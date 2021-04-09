package cn.com.pujing.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.OkGo;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.OrderAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.entity.ActivityCalendar;
import cn.com.pujing.entity.OrderItemBean;
import cn.com.pujing.entity.OrderTpeBean;
import cn.com.pujing.fragment.CurrentHotFragment;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.presenter.MyOrderPresenter;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.MyOrderView;
import cn.com.pujing.widget.MyOrderPopup;

/**
 * 我的订单
 */
public class MyOrderActivity extends BaseActivity<MyOrderView, MyOrderPresenter> implements MyOrderView, MyOrderPopup.MyOrderClickListener {

    @BindView(R.id.iv_order_filter)
    ImageView ivOrderFilter;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private OrderAdapter orderAdapter;
    private List<OrderItemBean.MyOrder> myOrders;
    private int page = 1;
    private int ordertypeId = 0;
    private String startDate = "";
    private String endDate = "";

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
        orderAdapter = new OrderAdapter(R.layout.adapter_my_order, null);
        recyclerView.setAdapter(orderAdapter);

        mPresenter.getMyOrder(page,ordertypeId,startDate,endDate);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;

                mPresenter.getMyOrder(page,ordertypeId,startDate,endDate);
            }
        });

        orderAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;

                mPresenter.getMyOrder(page,ordertypeId,startDate,endDate);
            }
        });

        orderAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (myOrders.get(position).orderCategory == 1){

                    Intent intent = new Intent(MyOrderActivity.this,RanquetsOrderActivity.class);
                    intent.putExtra("ordernumber",myOrders.get(position).orderNumber);
                    intent.putExtra("type",myOrders.get(position).orderType);
                    startActivity(intent);
                }else if (myOrders.get(position).orderCategory == 2){

                    Intent intent = new Intent(MyOrderActivity.this,OrderDetailActivity.class);
                    intent.putExtra("ordernumber",myOrders.get(position).id);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected MyOrderPresenter createPresenter() {
        return new MyOrderPresenter();
    }

    @OnClick({R.id.iv_order_filter,R.id.iv_myorder})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_order_filter:

                MyOrderPopup myOrderPopup = new MyOrderPopup(this,this);
                myOrderPopup.showAsDropDown(findViewById(R.id.v_title_bar));

                break;

            case R.id.iv_myorder:

                finish();

                break;

            default:

                break;
        }
    }

    @Override
    public void getMyOrderSuccess(OrderItemBean orderItemBean) {

        if (myOrders == null){
            myOrders = orderItemBean.records;
        }else {
            if (orderItemBean.pages > 1){
                myOrders.addAll(orderItemBean.records);
            }else {
                myOrders = orderItemBean.records;
            }
        }

        orderAdapter.setNewInstance(myOrders);

        if (orderItemBean.size == orderItemBean.total) {
            orderAdapter.getLoadMoreModule().loadMoreComplete();
        }else {
            orderAdapter.getLoadMoreModule().loadMoreEnd();
        }

        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void setItemValue(OrderTpeBean orderTpeBean, String startDate, String endDate) {
        page = 1;
        ordertypeId = orderTpeBean.typeId;
        this.startDate = startDate;
        this.endDate = endDate;
        mPresenter.getMyOrder(page,ordertypeId,startDate,endDate);
    }
}
