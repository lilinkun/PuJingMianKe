package cn.com.pujing.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.activity.LoginActivity;
import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.presenter.RegisterPresenter;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.Methods;
import cn.com.pujing.util.PuJingUtils;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.RegisterView;
import cn.com.pujing.widget.RegeisterPopup;

public class RegisterFragment extends BaseFragment<RegisterView, RegisterPresenter> implements View.OnClickListener, RegisterView, RegeisterPopup.OnListener {
    @BindView(R.id.et_cell_phone_number)
    EditText etCellPhoneNumber;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_sure_pwd)
    EditText etSurePwd;
    @BindView(R.id.et_captcha)
    EditText etCaptcha;
    @BindView(R.id.et_username)
    EditText etName;
    @BindView(R.id.tv_sex_value)
    TextView tvSexValue;

    private int sex = 0;

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
    @OnClick({R.id.tv_login,R.id.tv_get_captcha,R.id.btn_register,R.id.tv_sex_value})
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
            String etsurePwd = etSurePwd.getText().toString().trim();
            String name = etName.getText().toString().trim();

            if (userName.isEmpty() || pwd.isEmpty() || etsurePwd.isEmpty()){
                UToast.show(getActivity(),R.string.login_tip);
            }else if (name.isEmpty()){
                UToast.show(getActivity(),R.string.name_null_tip);
            }else if (phone.isEmpty()){
                UToast.show(getActivity(),R.string.phone_null_tip);
            }else if(!PuJingUtils.isPhoneNumber(phone)){
                UToast.show(getActivity(),R.string.phone_tip);
            } else if (captcha.isEmpty()){
                UToast.show(getActivity(),R.string.vcode_null_tip);
            }else if(!pwd.equals(etsurePwd)){
                UToast.show(getActivity(),R.string.psd_no_identical);
            }else if( pwd.length() < 6 || pwd.length() > 20){
                UToast.show(getActivity(),R.string.psw_null_tip);
            }else if (sex == 0) {
                UToast.show(getActivity(),"请选择性别");
            } else {

                mPresenter.register(userName,phone,pwd,captcha,name,sex+"");

            }
        }else if (id == R.id.tv_sex_value){

            RegeisterPopup regeisterPopup = new RegeisterPopup(getActivity(),this);
            regeisterPopup.showAsDropDown(etUserName);

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

    @Override
    public void getSex(int sex) {
        this.sex = sex;
        if (sex == 1){
            tvSexValue.setText("男");
        }else {
            tvSexValue.setText("女");
        }
    }
}
