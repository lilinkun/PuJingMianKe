package cn.com.pujing.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.net.URLEncoder;

import cn.com.pujing.Constants;
import cn.com.pujing.Methods;
import cn.com.pujing.R;
import cn.com.pujing.Urls;
import cn.com.pujing.activity.LoginActivity;
import cn.com.pujing.activity.MainActivity;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.datastructure.GetPublicKey;
import cn.com.pujing.datastructure.Token;
import cn.com.pujing.util.Base64Utils;

public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_login, null);
            init(view);
        }
        return view;
    }

    private void init(View view) {
        TextView registerTV = view.findViewById(R.id.tv_register);
        registerTV.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        registerTV.setOnClickListener(this);

        TextView loginTV = view.findViewById(R.id.tv_login);
        loginTV.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        view.findViewById(R.id.tv_forget_pwd).setOnClickListener(this);
        view.findViewById(R.id.btn_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.tv_register) {
            LoginActivity loginActivity = (LoginActivity) getActivity();

            if (loginActivity != null) {
                loginActivity.showFragment(LoginActivity.REGISTER);
            }
        } else if (id == R.id.tv_forget_pwd) {
            LoginActivity loginActivity = (LoginActivity) getActivity();
            if (loginActivity != null) {
                loginActivity.showFragment(LoginActivity.FORGET_PWD);
            }
        } else if (id == R.id.btn_login) {
            OkGo.get(Urls.GETPUBLICKEY)
                    .tag(this)
                    .execute(new JsonCallback<>(GetPublicKey.class, this));
        }
    }

    @Override
    public void onSuccess(Response response) {
        super.onSuccess(response);

        if (response != null) {

            if (response.body() instanceof GetPublicKey) {
                GetPublicKey getPublicKey = (GetPublicKey) response.body();
                GetPublicKey.Data data = getPublicKey.data;

                String publicKey = data.publicKey;
                publicKey = publicKey.replaceAll("-----BEGIN PUBLIC KEY-----", "").trim();
                publicKey = publicKey.replaceAll("-----END PUBLIC KEY-----", "").trim();

                String rsaKey = data.rsaKey;
                String userName = ((EditText) view.findViewById(R.id.et_account)).getText().toString().trim();
                String pwd = ((EditText) view.findViewById(R.id.et_pwd)).getText().toString().trim();

                try {
                    String password = Base64Utils.encode(Methods.encryptByPublicKey(pwd.getBytes(), Methods.getPublicKey(publicKey)));

                    OkGo.post(Urls.TOKEN)
                            .tag(this)
                            .params(Constants.USERNAME, userName)
                            .params(Constants.PASSWORD, URLEncoder.encode(password, "UTF-8"))
                            .params(Constants.RSAKEY, rsaKey)
                            .params(Constants.APPLICATIONCODE, Constants.ANDROID)
                            .execute(new JsonCallback<>(Token.class, this));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (response.body() instanceof Token) {
                Token token = (Token) response.body();
                Token.Data data = token.data;
                Methods.saveKeyValue(Constants.AUTHORIZATION, Constants.BEARER + data.access_token, getActivity());
                OkGo.getInstance().getCommonHeaders().put(Constants.AUTHORIZATION, Constants.BEARER + data.access_token);

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                getActivity().finish();
            }
        }
    }

    public void setUserName(String userName) {
        EditText editText = view.findViewById(R.id.et_account);
        editText.setText(userName);
    }

}
