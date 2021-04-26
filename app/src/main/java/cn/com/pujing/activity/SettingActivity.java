package cn.com.pujing.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.UpdateBean;
import cn.com.pujing.presenter.SettingPresenter;
import cn.com.pujing.service.UpdateService;
import cn.com.pujing.util.ActivityUtil;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.Methods;
import cn.com.pujing.util.PuJingUtils;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.SettingView;

/**
 * author : liguo
 * date : 2021/4/21 14:26
 * description :
 */
public class SettingActivity extends BaseActivity<SettingView, SettingPresenter> implements SettingView{

    @BindView(R.id.tv_current_edition)
    TextView tvCurrentEdition;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        ActivityUtil.addActivity(this);
        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        tvCurrentEdition.setText("v" + PuJingUtils.getVersion(this));


    }

    @OnClick({R.id.tv_modify_psd,R.id.iv_setting_back,R.id.tv_exit,R.id.rl_current_edition})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_modify_psd:

                startActivity(new Intent(this, ModifyPsdActivity.class));
                break;

            case R.id.iv_setting_back:

                finish();

                break;

            case R.id.tv_exit:

                Methods.saveKeyValue(Constants.AVATAR, "", this);
                Methods.saveKeyValue(Constants.AUTHORIZATION, "", this);
                Intent intent1 = new Intent(this, LoginActivity.class);
                startActivity(intent1);
                ActivityUtil.finishAll();

                break;

            case R.id.rl_current_edition:


                mPresenter.checkUpdate();


                break;

            default:
                break;
        }
    }


    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter();
    }

    @Override
    public void getUpdateDataSuccess(UpdateBean updateBean) {
        if (updateBean.buildVersion > Double.valueOf(PuJingUtils.getVersion(this))){
            AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this).setMessage("请升级更新app").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    /*mApkUrl = bean1.getInstall_url();
                    deleteApkFile();
                    downloadApkFile(dialog);*/

                    UpdateService.startAction(SettingActivity.this,updateBean.downloadURL,updateBean.buildName);

                }
            });
            builder.create().setCanceledOnTouchOutside(false);
            //  builder.setCancelable(false);
            /*builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });*/
            builder.show();
        }else {
            UToast.show(this,"此版本是最新版本");
        }
    }

    @Override
    public void getDataFail(String msg) {

    }


}
