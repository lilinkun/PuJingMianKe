package cn.com.pujing.activity;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cn.com.pujing.R;
import cn.com.pujing.adapter.OrderAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.OrderItem;
import cn.com.pujing.presenter.MyOrderPresenter;
import cn.com.pujing.view.MyOrderView;

public class MyOrderActivity extends BaseActivity<MyOrderView, MyOrderPresenter> implements MyOrderView {

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    public void initView() {

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
}
