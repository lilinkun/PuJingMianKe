package cn.com.pujing.activity;

import com.gyf.immersionbar.ImmersionBar;

import cn.com.pujing.R;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.presenter.ServiceReservePresenter;
import cn.com.pujing.view.ServiceReserveView;

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
}
