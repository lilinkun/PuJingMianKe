package cn.com.pujing.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.internal.Utils;
import cn.com.pujing.R;
import cn.com.pujing.adapter.OrderListDetailAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.RestBanquetsBean;
import cn.com.pujing.entity.RestOrderBean;
import cn.com.pujing.presenter.RanquetsOrderPresenter;
import cn.com.pujing.util.ActivityUtil;
import cn.com.pujing.util.PuJingUtils;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.RanquetsOrderView;

/**
 * author : liguo
 * date : 2021/3/10 18:33
 * description : 宴会餐和零点餐订单
 */
public class RanquetsOrderActivity extends BaseActivity<RanquetsOrderView, RanquetsOrderPresenter> implements RanquetsOrderView{

    @BindView(R.id.iv_rest_right_date)
    ImageView ivRestRightDate;
    @BindView(R.id.iv_rest_right_time)
    ImageView ivRestRightTime;
    @BindView(R.id.iv_rest_right_type)
    ImageView ivRestRightType;
    @BindView(R.id.iv_rest_right_num)
    ImageView ivRestRightNum;
    @BindView(R.id.tv_reserve_date)
    TextView tvReserveDate;
    @BindView(R.id.tv_reserve_time)
    TextView tvReserveTime;
    @BindView(R.id.tv_meal_type)
    TextView tvMealType;
    @BindView(R.id.tv_reserve_num)
    TextView tvReserveNum;
    @BindView(R.id.tv_ordernumber)
    TextView tvOrderNumber;
    @BindView(R.id.tv_ordertime)
    TextView tvOrderTime;
    @BindView(R.id.rv_order_detail)
    RecyclerView rvOrderDetail;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_order_title)
    TextView tvOrderTitle;
    @BindView(R.id.tv_exit_order)
    TextView tvExitOrder;
    @BindView(R.id.ll_rest_date)
    LinearLayout llRestDate;
    @BindView(R.id.ll_order_rest_bottom)
    LinearLayout llOrderRestBottom;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;

    private OrderListDetailAdapter orderListDetailAdapter;
    private String orderNumber;
    private int type = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ranquets_order;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();
        ActivityUtil.addHomeActivity(this);
        ivRestRightDate.setVisibility(View.GONE);
        ivRestRightTime.setVisibility(View.GONE);
        ivRestRightType.setVisibility(View.GONE);
        ivRestRightNum.setVisibility(View.GONE);

        orderNumber = getIntent().getStringExtra("ordernumber");
        type = getIntent().getIntExtra("type",0);

        if (type == 3){
            llRestDate.setVisibility(View.GONE);
            tvOrderTitle.setText(R.string.order_rest_detail);
        }


        tvOrderNumber.setText(orderNumber);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        orderListDetailAdapter = new OrderListDetailAdapter(R.layout.adapter_rest_banquets_reserve,null,this);
        rvOrderDetail.setLayoutManager(linearLayoutManager);
        rvOrderDetail.setAdapter(orderListDetailAdapter);


        mPresenter.orderDetail(orderNumber);

    }

    @Override
    protected RanquetsOrderPresenter createPresenter() {
        return new RanquetsOrderPresenter();
    }

    @Override
    public void RanquetsOrderDataSuccess(RestBanquetsBean restOrderBean) {
        tvOrderTime.setText(restOrderBean.createTime);
        tvOrderStatus.setText(restOrderBean.orderStatus_label);

        List<RestBanquetsBean.OrderFoodList> orderFoodLists = restOrderBean.getOrderFoodList();

        orderListDetailAdapter.setNewInstance(orderFoodLists);

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm月dd日");

        tvReserveDate.setText(restOrderBean.getOrderingDate());
        tvReserveTime.setText(restOrderBean.getOrderingTime());
        tvReserveNum.setText(restOrderBean.getPeopleNumber()+"");
        tvMealType.setText(restOrderBean.mealTime_label);

        tvTotalPrice.setText("￥" + PuJingUtils.removeAmtLastZero(restOrderBean.orderMoney));

        if(restOrderBean.getReservationType() == 1){
            tvExitOrder.setVisibility(View.GONE);
        }
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void exitOrderSuccess(boolean isTrue) {
        UToast.show(this,"取消订单成功");
        ActivityUtil.finishHomeAll();
    }

    @OnClick({R.id.tv_copy,R.id.iv_setmeal_back,R.id.tv_exit_order,R.id.tv_add_rest})
    public void onClick(View view){

        switch (view.getId()){
            case R.id.tv_copy:

                PuJingUtils.copyContentToClipboard(tvOrderNumber.getText().toString(),this);
                UToast.show(this,"已复制成功");

                break;

            case R.id.iv_setmeal_back:

                ActivityUtil.finishHomeAll();

                break;

            case R.id.tv_exit_order:

                mPresenter.restOrderClean(orderNumber);

                break;

            case R.id.tv_add_rest:


                Intent intent = new Intent(this, RestBanquetsActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("add",true);
                intent.putExtra("orderNumber",orderNumber);
                startActivity(intent);

                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtil.finishHomeAll();
    }
}
