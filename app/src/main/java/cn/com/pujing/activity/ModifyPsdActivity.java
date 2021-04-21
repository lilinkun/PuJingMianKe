package cn.com.pujing.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.presenter.ModifyPsdPresenter;
import cn.com.pujing.util.ActivityUtil;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.Methods;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.ModifyPsdView;

/**
 * author : liguo
 * date : 2021/4/21 11:34
 * description :
 */
public class ModifyPsdActivity extends BaseActivity<ModifyPsdView, ModifyPsdPresenter> implements ModifyPsdView{

    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.et_old_pwd)
    EditText etOldPwd;
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R.id.et_sure_new_pwd)
    EditText etSureNewPwd;

    @Override
    public int getLayoutId() {
        return R.layout.activity_modifypsd;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();
        ActivityUtil.addActivity(this);

        String username = Methods.getValueByKey(Constants.USERNAME, this);
        tvAccount.setText(username);

    }

    @Override
    protected ModifyPsdPresenter createPresenter() {
        return new ModifyPsdPresenter();
    }


    @OnClick({R.id.iv_modify_psd_back,R.id.tv_submit})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_modify_psd_back:
                    finish();
                break;

            case R.id.tv_submit:

                if (etOldPwd.getText().toString().trim().length() == 0){
                    UToast.show(this,"请填写旧密码");
                }else if (etNewPwd.getText().toString().trim().length() == 0){
                    UToast.show(this,"请填写新密码");
                }else if(etSureNewPwd.getText().toString().trim().length() == 0){
                    UToast.show(this,"请确认新密码");
                }else if (!etNewPwd.getText().toString().equals(etSureNewPwd.getText().toString())){
                    UToast.show(this,"两次输入新密码不同");
                }else {

                }


                break;

            default:

                break;

        }
    }

    @Override
    public void modifyPsdSuccess(Object o) {

    }

    @Override
    public void getDataFail(String msg) {

    }
}
