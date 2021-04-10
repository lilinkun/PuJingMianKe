package cn.com.pujing.activity;

import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.model.Response;

import cn.com.pujing.R;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.presenter.MyBillPresenter;
import cn.com.pujing.view.MyBillView;

/**
 * 我的账单
 */
public class MyBillActivity extends BaseActivity<MyBillView, MyBillPresenter> implements MyBillView {

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_bill;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        mPresenter.getMyCurrentBills();

    }


    @Override
    protected MyBillPresenter createPresenter() {
        return new MyBillPresenter();
    }

    @Override
    public void getMyCurrentBillsSuccess(Object o) {

    }

    @Override
    public void getDataFail(String msg) {

    }
}
