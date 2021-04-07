package cn.com.pujing.activity;

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
 * date : 2021/4/7 19:20
 * description :
 */
public class InvalidCouponActivity extends BaseActivity<MyCardView, MyCardPresenter> implements MyCardView{

    @BindView(R.id.rv_invalid_coupon)
    RecyclerView rvInvalidCoupon;

    private MyCardAdapter myCardAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_invalid_coupon;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        myCardAdapter = new MyCardAdapter(R.layout.adapter_invalid_coupon,null);

        rvInvalidCoupon.setLayoutManager(linearLayoutManager);
        rvInvalidCoupon.setAdapter(myCardAdapter);

        mPresenter.getMycard(1);
    }

    @Override
    protected MyCardPresenter createPresenter() {
        return new MyCardPresenter();
    }

    @OnClick({R.id.iv_invalid_coupon_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_invalid_coupon_back:
                finish();
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
