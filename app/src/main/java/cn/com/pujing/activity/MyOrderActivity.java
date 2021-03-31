package cn.com.pujing.activity;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.OrderAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.OrderItem;
import cn.com.pujing.presenter.MyOrderPresenter;
import cn.com.pujing.view.MyOrderView;
import cn.com.pujing.widget.MyOrderPopup;

public class MyOrderActivity extends BaseActivity<MyOrderView, MyOrderPresenter> implements MyOrderView {

    @BindView(R.id.iv_order_filter)
    ImageView ivOrderFilter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL));
        OrderAdapter orderAdapter = new OrderAdapter(R.layout.adapter_my_order, OrderItem.getTestData());
        recyclerView.setAdapter(orderAdapter);
    }

    @Override
    protected MyOrderPresenter createPresenter() {
        return new MyOrderPresenter();
    }

    @OnClick({R.id.iv_order_filter})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_order_filter:

                MyOrderPopup myOrderPopup = new MyOrderPopup(this);
                myOrderPopup.showAsDropDown(findViewById(R.id.v_title_bar));

                break;

            default:

                break;
        }
    }

}
