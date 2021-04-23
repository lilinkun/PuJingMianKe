package cn.com.pujing.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.ActivityBean;
import cn.com.pujing.entity.OrderDetailBean;
import cn.com.pujing.entity.VenueDetailBean;
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
    @BindView(R.id.ll_order_detail_date)
    LinearLayout llOrderDetailDate;
    @BindView(R.id.tv_exit_order)
    TextView tvExitOrder;
    @BindView(R.id.tv_pay_status)
    TextView tvPayStatus;

    private int type = 0;

    private VenueDetailBean venueDetailBean;
    private String ordernumber;

    @Override
    public int getLayoutId() {
        return R.layout.activity_orderdetail;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        ordernumber = getIntent().getStringExtra("ordernumber");
        type = getIntent().getIntExtra("type",0);

        initPresenter();

        if (type == 3){
            llOrderDetailDate.setVisibility(View.GONE);
            rlCoupon.setVisibility(View.GONE);
        }

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
        tvPayStatus.setText(orderDetailBean.payStatus_label);

        if (orderDetailBean.orderingDate == null || orderDetailBean.orderingDate.toString().length() == 0){
            llOrderDetailDate.setVisibility(View.GONE);
        }

        if (orderDetailBean.customerVoucherId != null && orderDetailBean.customerVoucherId.toString().length() > 0){
            rlCoupon.setVisibility(View.VISIBLE);
            tvTotalCoupon.setText(orderDetailBean.customerVoucherName + "  x1");
        }
        Glide.with(this).load(PujingService.PREFIX + PujingService.IMG + orderDetailBean.themePic)
                .apply(PuJingUtils.setGlideCircle(10)).error(R.drawable.ic_no_pic).into(ivReserveHead);

        if (orderDetailBean.orderStatus == 1){
            tvExitOrder.setVisibility(View.VISIBLE);
        }else {
            tvExitOrder.setVisibility(View.GONE);
        }

    }

    @Override
    public void queryActivitySuccess(ActivityBean activityBean) {
        tvOrderTime.setText(activityBean.createTime);
        tvOrderStatus.setText(activityBean.orderStatus_label);
        tvOrderNumber.setText(activityBean.orderNumber);
        tvTotalPrice.setText("￥" + PuJingUtils.removeAmtLastZero(activityBean.orderMoney));
        tvReservePrice.setText("￥" + PuJingUtils.removeAmtLastZero(activityBean.orderMoney));
        tvReserveName.setText(activityBean.activityName);
        tvPayStatus.setText(activityBean.payStatus_label);

        ivReserveHead.setImageResource(R.drawable.ic_no_pic);

    }

    @Override
    public void queryVenueSuccess(VenueDetailBean venueDetailBean) {
        this.venueDetailBean = venueDetailBean;
        tvOrderTime.setText(venueDetailBean.orderTime);
        if (venueDetailBean.status == 1){
            tvOrderStatus.setText("待确认");
        }else if (venueDetailBean.status == 2){
            tvOrderStatus.setText("已确认");
        }else if (venueDetailBean.status == 3){
            tvOrderStatus.setText("已取消");
        }
        tvOrderNumber.setText(venueDetailBean.orderNum);
        tvTotalPrice.setText("￥" + 0);
        tvReservePrice.setText("￥" + 0);
        tvReserveName.setText(venueDetailBean.deviceName);
        tvReserveDate.setText(venueDetailBean.reserveDate);
        tvReserveTime.setText(venueDetailBean.reserveTime);
//        tvPayStatus.setText(venueDetailBean.payStatus_label);

        llOrderDetailDate.setVisibility(View.GONE);

        Glide.with(this).load(PujingService.PREFIX + PujingService.IMG + venueDetailBean.topic)
                .apply(PuJingUtils.setGlideCircle(10)).error(R.drawable.ic_no_pic).into(ivReserveHead);
    }

    @Override
    public void queryFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void exitVenueOrder() {
        finish();
    }

    @Override
    public void exitVenueOrderFail(String msg) {

    }

    @Override
    public void exitServiceOrder() {

        finish();
    }

    @Override
    public void exitServiceOrderFail(String msg) {

    }

    @OnClick({R.id.iv_order_detail_back,R.id.tv_exit_order})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_order_detail_back:
                finish();
                break;

            case R.id.tv_exit_order:

                new AlertDialog.Builder(this).setTitle("是否取消订单").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (type == 4) {
                            mPresenter.exitVenueOrder(ordernumber, 3 + "");
                        }else if (type == 2){
                            mPresenter.exitServiceOrder(ordernumber);
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();


                break;

            default:

                break;
        }
    }

    private void initPresenter(){
        if (type == 4){
            mPresenter.searchVenueDetail(ordernumber);
        }else if (type == 3){
            mPresenter.queryActivityOrder(ordernumber);
            tvExitOrder.setVisibility(View.GONE);
        }else {
            mPresenter.queryReserveServiceOrder(ordernumber);
        }
    }
}
