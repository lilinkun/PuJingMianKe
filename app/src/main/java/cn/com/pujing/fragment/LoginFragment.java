package cn.com.pujing.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.Base;
import cn.com.pujing.entity.LoginToken;
import cn.com.pujing.entity.PublicKey;
import cn.com.pujing.presenter.LoginPresenter;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.Methods;
import cn.com.pujing.R;
import cn.com.pujing.util.UToast;
import cn.com.pujing.util.Urls;
import cn.com.pujing.activity.LoginActivity;
import cn.com.pujing.activity.MainActivity;
import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.entity.GetPublicKey;
import cn.com.pujing.entity.Token;
import cn.com.pujing.util.Base64Utils;
import cn.com.pujing.view.LoginView;

public class LoginFragment extends BaseFragment<LoginView, LoginPresenter> implements View.OnClickListener ,LoginView{

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
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
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

                /*OkGo.get(Urls.GETPUBLICKEY)
                        .tag(this)
                        .execute(new JsonCallback<>(GetPublicKey.class, this));*/
                mPresenter.getPublicKey();
            }
        }
    }

    public void setUserName(String userName) {
        etUsername.setText(userName);
    }


    @Override
    public void getPublicKeySucccess(PublicKey mPublicKey) {

        String publicKey = mPublicKey.getPublicKey();
        publicKey = publicKey.replaceAll("-----BEGIN PUBLIC KEY-----", "").trim();
        publicKey = publicKey.replaceAll("-----END PUBLIC KEY-----", "").trim();

        String rsaKey = mPublicKey.getRsaKey();
        String userName = etUsername.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();

        try {
            String password = Base64Utils.encode(Methods.encryptByPublicKey(pwd.getBytes(), Methods.getPublicKey(publicKey)));

            mPresenter.getLoginSuccess(userName,password,rsaKey);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(getActivity(),msg);
    }

    @Override
    public void getLoginSuccess(LoginToken loginToken) {

        Methods.saveKeyValue(Constants.AUTHORIZATION, Constants.BEARER + loginToken.getAccess_token(), getActivity());
        OkGo.getInstance().getCommonHeaders().put(Constants.AUTHORIZATION, Constants.BEARER + loginToken.getAccess_token());


        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        getActivity().finish();
    }
}
