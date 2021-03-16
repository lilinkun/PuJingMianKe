package cn.com.pujing.activity;

import cn.com.pujing.R;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.presenter.SeeOrderPresenter;
import cn.com.pujing.view.SeeOrderView;

/**
 * author : liguo
 * date : 2021/3/7 12:29
 * description :
 */
public class SeeOrderActivity extends BaseActivity<SeeOrderView, SeeOrderPresenter> implements SeeOrderView {

    @Override
    public int getLayoutId() {
        return R.layout.activity_seeorder;
    }

    @Override
    public void init() {

    }

    @Override
    protected SeeOrderPresenter createPresenter() {
        return new SeeOrderPresenter();
    }

    @Override
    public void getOrderList() {

    }

    @Override
    public void getDataFail(String msg) {

    }
}
