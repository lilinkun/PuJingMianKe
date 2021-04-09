package cn.com.pujing.activity;

import android.view.View;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.OrderDetailBean;
import cn.com.pujing.presenter.OrderDetailPresenter;
import cn.com.pujing.util.PuJingUtils;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.OrderDetailView;

/**
 * author : liguo
 * date : 2021/4/9 15:54
 * description :
 */
public class OrderDetailActivity extends BaseActivity<OrderDetailView, OrderDetailPresenter> implements OrderDetailView{

    @BindView(R.id.tv_ordernumber)
    TextView tvOrderNumber;
    @BindView(R.id.tv_ordertime)
    TextView tvOrderTime;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_reserve_name)
    TextView tvReserveName;
    @BindView(R.id.tv_reserve_price)
    TextView tvReservePrice;
    @BindView(R.id.tv_reserve_date)
    TextView tvReserveDate;
    @BindView(R.id.tv_reserve_time)
    TextView tvReserveTime;

    @Override
    public int getLayoutId() {
        return R.layout.activity_orderdetail;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        String ordernumber = getIntent().getStringExtra("ordernumber");

        mPresenter.queryReserveServiceOrder(ordernumber);

    }

    @Override
    protected OrderDetailPresenter createPresenter() {
        return new OrderDetailPresenter();
    }


    @Override
    public void querySuccess(OrderDetailBean orderDetailBean) {
        tvOrderTime.setText(orderDetailBean.createTime);
        tvOrderStatus.setText(orderDetailBean.orderStatus_label);
        tvOrderNumber.setText(orderDetailBean.orderNunber);
        tvTotalPrice.setText("￥" + PuJingUtils.removeAmtLastZero(orderDetailBean.money));
        tvReservePrice.setText("￥" + PuJingUtils.removeAmtLastZero(orderDetailBean.money));
        tvReserveName.setText(orderDetailBean.basicServiceItemsName);
        tvReserveDate.setText(orderDetailBean.orderingDate);
        tvReserveTime.setText(orderDetailBean.orderingTime);
    }

    @Override
    public void queryFail(String msg) {
        UToast.show(this,msg);
    }

    @OnClick({R.id.iv_order_detail_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_order_detail_back:
                finish();
                break;
        }
    }
}
