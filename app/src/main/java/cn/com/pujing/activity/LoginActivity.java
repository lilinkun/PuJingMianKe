package cn.com.pujing.activity;

import android.content.pm.PackageManager;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
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
import cn.com.pujing.util.PermissUtil;

/**
 * 登录界面
 */
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

    @Override
    public void initView() {
        ImmersionBar.with(this).statusBarDarkFont(true).init();
        showFragment(LOGIN);

        if (getIntent()!= null){
            if (getIntent().getStringExtra("name") != null){
                loginFragment.setName(getIntent().getStringExtra("name"));
            }
        }

        PermissUtil.checkPermissions(this,PermissUtil.appNeedPermissions);

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    /**
     * 显示
     * @param type
     */
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

            default:
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

    /**
     * 点击事件
     * @param view
     */
    @OnClick({R.id.iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:

                onBackPressed();

                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissUtil.PERMISSON_REQUESTCODE){
            if (grantResults[0] == PackageManager.PERMISSION_DENIED){
                finish();
            }
        }
    }
}
