package cn.com.pujing.activity;

import com.lzy.okgo.model.Response;

import cn.com.pujing.R;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.base.BasePresenter;

public class MyEnrollActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_my_enroll;
    }

    public void init() {

    }

    @Override
    public void onSuccess(Response response) {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
