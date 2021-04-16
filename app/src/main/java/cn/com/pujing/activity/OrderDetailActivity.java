package cn.com.pujing.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.OrderDetailBean;
import cn.com.pujing.http.PujingService;
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
    @BindView(R.id.tv_total_coupon)
    TextView tvTotalCoupon;
    @BindView(R.id.iv_reserve_head)
    ImageView ivReserveHead;
    @BindView(R.id.rl_coupon)
    RelativeLayout rlCoupon;

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

        if (orderDetailBean.customerVoucherId != null && orderDetailBean.customerVoucherId.toString().length() > 0){
            rlCoupon.setVisibility(View.VISIBLE);
            tvTotalCoupon.setText(orderDetailBean.customerVoucherName + "  x1");
        }
        Glide.with(this).load(PujingService.PREFIX + PujingService.IMG + orderDetailBean.themePic)
                .apply(PuJingUtils.setGlideCircle(10)).error(R.drawable.ic_no_pic).into(ivReserveHead);


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
