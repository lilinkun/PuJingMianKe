package cn.com.pujing.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.Methods;
import cn.com.pujing.R;
import cn.com.pujing.util.Urls;
import cn.com.pujing.activity.LoginActivity;
import cn.com.pujing.activity.MainActivity;
import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.entity.GetPublicKey;
import cn.com.pujing.entity.Token;
import cn.com.pujing.util.Base64Utils;

public class LoginFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.tv_register)
    TextView registerTV;
    @BindView(R.id.tv_login)
    TextView loginTV;
    @BindView(R.id.et_account)
    EditText etUsername;
    @BindView(R.id.et_pwd)
    EditText etPwd;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initEventAndData() {

        registerTV.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        loginTV.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

    }

    @Override
    @OnClick({R.id.tv_register,R.id.tv_forget_pwd,R.id.btn_login})
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

            if (etUsername.getText().toString().trim().isEmpty() || etPwd.getText().toString().trim().isEmpty()){
                Toast.makeText(getActivity(),R.string.login_tip,Toast.LENGTH_LONG).show();
            }else {

                OkGo.get(Urls.GETPUBLICKEY)
                        .tag(this)
                        .execute(new JsonCallback<>(GetPublicKey.class, this));
            }
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
                String userName = etUsername.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();

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
        etUsername.setText(userName);
    }

}
