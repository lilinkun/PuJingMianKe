package cn.com.pujing.activity;

import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.fragment.ForgetPwdFragment;
import cn.com.pujing.fragment.LoginFragment;
import cn.com.pujing.fragment.RegisterFragment;

public class LoginActivity extends BaseActivity {
    public static final int LOGIN = 0;
    public static final int REGISTER = 1;
    public static final int FORGET_PWD = 2;
    public LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    private ForgetPwdFragment forgetPwdFragment;
    private int current;

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    public void init() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .init();
        showFragment(LOGIN);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    public void showFragment(int type) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (loginFragment != null) {
            fragmentTransaction.hide(loginFragment);
        }

        if (registerFragment != null) {
            fragmentTransaction.hide(registerFragment);
        }

        if (forgetPwdFragment != null) {
            fragmentTransaction.hide(forgetPwdFragment);
        }

        switch (type) {
            case LOGIN:
                ivBack.setVisibility(View.GONE);
                if (loginFragment == null) {
                    loginFragment = new LoginFragment();
                    fragmentTransaction.add(R.id.fl, loginFragment);
                } else {
                    fragmentTransaction.show(loginFragment);
                }
                break;
            case REGISTER:
                ivBack.setVisibility(View.GONE);
                if (registerFragment == null) {
                    registerFragment = new RegisterFragment();
                    fragmentTransaction.add(R.id.fl, registerFragment);
                } else {
                    fragmentTransaction.show(registerFragment);
                }
                break;
            case FORGET_PWD:
                ivBack.setVisibility(View.VISIBLE);
                if (forgetPwdFragment == null) {
                    forgetPwdFragment = new ForgetPwdFragment();
                    fragmentTransaction.add(R.id.fl, forgetPwdFragment);
                } else {
                    fragmentTransaction.show(forgetPwdFragment);
                }
                break;
        }
        fragmentTransaction.commit();
        current = type;
    }

    @Override
    public void onBackPressed() {

        if (current == FORGET_PWD) {
            showFragment(LOGIN);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:

                onBackPressed();

                break;
        }
    }

}
