package cn.com.pujing.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.activity.LoginActivity;
import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.presenter.RegisterPresenter;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.Methods;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.RegisterView;

public class RegisterFragment extends BaseFragment<RegisterView, RegisterPresenter> implements View.OnClickListener,RegisterView {
    @BindView(R.id.et_cell_phone_number)
    EditText etCellPhoneNumber;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_captcha)
    EditText etCaptcha;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void initEventAndData() {
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @Override
    @OnClick({R.id.tv_login,R.id.tv_get_captcha,R.id.btn_register})
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.tv_login) {
            LoginActivity loginActivity = (LoginActivity) getActivity();

            if (loginActivity != null) {
                loginActivity.showFragment(LoginActivity.LOGIN);
            }
        } else if (id == R.id.tv_get_captcha) {
            String phone = etCellPhoneNumber.getText().toString().trim();

            mPresenter.sendVCode(phone,false);


        } else if (id == R.id.btn_register) {
            String userName = etUserName.getText().toString().trim();
            String phone = etCellPhoneNumber.getText().toString().trim();
            String pwd = etPwd.getText().toString().trim();
            String captcha = etCaptcha.getText().toString().trim();

            if (userName.isEmpty() || pwd.isEmpty()){
                Toast.makeText(getActivity(),R.string.login_tip,Toast.LENGTH_LONG).show();
            }else if (phone.isEmpty()){
                Toast.makeText(getActivity(),"手机号不能为空",Toast.LENGTH_LONG).show();
            }else if (captcha.isEmpty()){
                Toast.makeText(getActivity(),"验证码不能为空",Toast.LENGTH_LONG).show();
            }else {

                mPresenter.register(userName,phone,pwd,captcha);

            }
        }
    }


    @Override
    public void getVCodeSuccess(String sendSms) {
        if (sendSms != null) {
            etCaptcha.setText(sendSms);
        }
    }

    @Override
    public void registerSuccess() {
        String userName = etUserName.getText().toString().trim();
        Methods.saveKeyValue(Constants.PHONE, userName, getActivity());
        Toast.makeText(getActivity(), getString(R.string.register_success), Toast.LENGTH_SHORT).show();
        LoginActivity loginActivity = (LoginActivity) getActivity();
        loginActivity.loginFragment.setUserName(userName);

        if (loginActivity != null) {
            loginActivity.showFragment(LoginActivity.LOGIN);
        }
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(getActivity(),msg);
    }
}
