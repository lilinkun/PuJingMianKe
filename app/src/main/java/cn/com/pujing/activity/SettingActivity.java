package cn.com.pujing.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.util.ActivityUtil;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.Methods;
import cn.com.pujing.util.PuJingUtils;

/**
 * author : liguo
 * date : 2021/4/21 14:26
 * description :
 */
public class SettingActivity extends BaseActivity {

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

    @OnClick({R.id.tv_modify_psd,R.id.iv_setting_back,R.id.tv_exit})
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

            default:
                break;
        }
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
