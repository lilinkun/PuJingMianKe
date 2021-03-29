package cn.com.pujing.activity;

import android.view.View;

import com.gyf.immersionbar.ImmersionBar;

import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.presenter.ServiceReservePresenter;
import cn.com.pujing.view.ServiceReserveView;
import cn.com.pujing.widget.RightAndInterestsDialog;

/**
 * author : liguo
 * date : 2021/3/26 17:51
 * description :
 */
public class ServiceReserveActivity extends BaseActivity<ServiceReserveView, ServiceReservePresenter> implements ServiceReserveView {

    @Override
    public int getLayoutId() {
        return R.layout.activity_service_reserve;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this)
                .statusBarColor(R.color.main_color)
                .fitsSystemWindows(true)
                .init();
    }

    @Override
    protected ServiceReservePresenter createPresenter() {
        return new ServiceReservePresenter();
    }

    @OnClick({R.id.iv_lifeservice_back,R.id.rl_rights_and_interests})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_lifeservice_back:

                finish();

                break;

            case R.id.rl_rights_and_interests:

                RightAndInterestsDialog rightAndInterestsDialog = new RightAndInterestsDialog(this);
                rightAndInterestsDialog.show();

                break;

            default:

                break;
        }
    }
}
