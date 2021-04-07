package cn.com.pujing.activity;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.MyCardAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.MyCardBean;
import cn.com.pujing.presenter.MyCardPresenter;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.MyCardView;

/**
 * author : liguo
 * date : 2021/3/29 11:04
 * description :我的卡包
 */
public class MyCardActivity extends BaseActivity<MyCardView, MyCardPresenter> implements MyCardView{

    @BindView(R.id.rv_mycard)
    RecyclerView rvMycard;

    private MyCardAdapter myCardAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mycard;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        myCardAdapter = new MyCardAdapter(R.layout.adapter_mycard,null);

        rvMycard.setLayoutManager(linearLayoutManager);
        rvMycard.setAdapter(myCardAdapter);

        mPresenter.getMycard(0);
    }

    @Override
    protected MyCardPresenter createPresenter() {
        return new MyCardPresenter();
    }

    @OnClick({R.id.iv_card_back,R.id.tv_invalid_coupon})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_card_back:
                    finish();
                break;

            case R.id.tv_invalid_coupon:

                Intent intent = new Intent();
                intent.setClass(this,InvalidCouponActivity.class);
                startActivity(intent);

                break;

            default:

                break;
        }
    }

    @Override
    public void getCardDataSuccess(List<MyCardBean> myCardBeans) {
        myCardAdapter.setNewInstance(myCardBeans);
    }

    @Override
    public void getCardDataFail(String msg) {
        UToast.show(this,msg);
    }
}
