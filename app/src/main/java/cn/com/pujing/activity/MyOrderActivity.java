package cn.com.pujing.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lzy.okgo.model.Response;

import cn.com.pujing.R;
import cn.com.pujing.adapter.OrderAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.datastructure.OrderItem;

public class MyOrderActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_order;
    }

    public void init() {

        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL));
        OrderAdapter orderAdapter = new OrderAdapter(R.layout.item_order, OrderItem.getTestData());
        recyclerView.setAdapter(orderAdapter);
    }

    @Override
    public void onSuccess(Response response) {

    }
}
