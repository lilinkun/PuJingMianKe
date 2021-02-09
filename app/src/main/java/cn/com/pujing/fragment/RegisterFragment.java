package cn.com.pujing.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

import java.util.HashMap;

import cn.com.pujing.Constants;
import cn.com.pujing.Methods;
import cn.com.pujing.R;
import cn.com.pujing.Urls;
import cn.com.pujing.activity.LoginActivity;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.datastructure.Register;
import cn.com.pujing.datastructure.SendSms;

public class RegisterFragment extends BaseFragment implements View.OnClickListener {
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_register, null);
        }
        init(view);
        return view;
    }

    private void init(View view) {
        view.findViewById(R.id.tv_login).setOnClickListener(this);
        view.findViewById(R.id.tv_get_captcha).setOnClickListener(this);
        view.findViewById(R.id.btn_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.tv_login) {
            LoginActivity loginActivity = (LoginActivity) getActivity();

            if (loginActivity != null) {
                loginActivity.showFragment(LoginActivity.LOGIN);
            }
        } else if (id == R.id.tv_get_captcha) {
            String phone = ((EditText) view.findViewById(R.id.et_cell_phone_number)).getText().toString().trim();

            OkGo.get(Urls.SENDSMS)
                    .tag(this)
                    .params(Constants.PHONE, phone)
                    .execute(new JsonCallback<>(SendSms.class, this));

        } else if (id == R.id.btn_register) {
            String userName = ((EditText) view.findViewById(R.id.et_user_name)).getText().toString().trim();
            String phone = ((EditText) view.findViewById(R.id.et_cell_phone_number)).getText().toString().trim();
            String pwd = ((EditText) view.findViewById(R.id.et_pwd)).getText().toString().trim();
            String captcha = ((EditText) view.findViewById(R.id.et_captcha)).getText().toString().trim();

            HashMap<String, String> params = new HashMap<>();
            params.put(Constants.USERNAME, userName);
            params.put(Constants.PHONE, phone);
            params.put(Constants.NEWPASSWORD, pwd);
            params.put(Constants.SMSVALIDCODE, captcha);
            JSONObject jsonObject = new JSONObject(params);

            OkGo.post(Urls.REGISTER)
                    .tag(this)
                    .upJson(jsonObject)
                    .execute(new JsonCallback<>(Register.class, this));
        }
    }

    @Override
    public void onSuccess(Response response) {
        if (response != null) {

            if (response.body() instanceof SendSms) {
                SendSms sendSms = (SendSms) response.body();

                if (sendSms != null) {
                    EditText editText = view.findViewById(R.id.et_captcha);
                    editText.setText(sendSms.data);
                }
            } else if (response.body() instanceof Register) {
                Register register = (Register) response.body();

                if (register != null && register.data) {
                    String userName = ((EditText) view.findViewById(R.id.et_user_name)).getText().toString().trim();
                    Methods.saveKeyValue(Constants.PHONE, userName, getActivity());
                    Toast.makeText(getActivity(), getString(R.string.register_success), Toast.LENGTH_SHORT).show();
                    LoginActivity loginActivity = (LoginActivity) getActivity();
                    loginActivity.loginFragment.setUserName(userName);

                    if (loginActivity != null) {
                        loginActivity.showFragment(LoginActivity.LOGIN);
                    }
                }
            }
        }
    }
}
