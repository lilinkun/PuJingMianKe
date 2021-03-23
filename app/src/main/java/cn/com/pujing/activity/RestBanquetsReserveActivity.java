package cn.com.pujing.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.RestBanquetsReserveAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.ChangeDataBean;
import cn.com.pujing.entity.RestBanquetsBean;
import cn.com.pujing.entity.RestMealTypeBean;
import cn.com.pujing.presenter.RestBanquetsReservePresenter;
import cn.com.pujing.util.ActivityUtil;
import cn.com.pujing.util.PuJingUtils;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.RestBanquetsReserveView;
import cn.com.pujing.widget.OnAccountDialog;
import cn.com.pujing.widget.ReserveTimeDialog;
import cn.com.pujing.widget.ReserveTypeDialog;
import cn.com.pujing.widget.RestMealTypeDialog;
import cn.com.pujing.widget.RestNumDialog;

/**
 * author : liguo
 * date : 2021/3/9 20:53
 * description :
 */
public class RestBanquetsReserveActivity extends BaseActivity<RestBanquetsReserveView, RestBanquetsReservePresenter> implements RestBanquetsReserveView, RestMealTypeDialog.MealTypeClickListener, ReserveTimeDialog.OnTimeListenr, ReserveTypeDialog.OnItemChoose {

    @BindView(R.id.rv_order_detail)
    RecyclerView rvOrderDetail;
    @BindView(R.id.tv_reserve_date)
    TextView tvReserveDate;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_meal_type)
    TextView tvMealType;
    @BindView(R.id.tv_reserve_time)
    TextView tvReserveTime;
    @BindView(R.id.tv_reserve_num)
    TextView tvReserveNum;
    @BindView(R.id.tv_reserve)
    TextView tvReserve;
    @BindView(R.id.tv_reserve_type)
    TextView tvReserveType;
    @BindView(R.id.rl_reserve_type)
    RelativeLayout rlReserveType;
    @BindView(R.id.rl_reserve_person_num)
    RelativeLayout rlReservePersonNum;
    @BindView(R.id.ll_rest_banquets)
    LinearLayout llRestBanquets;

    private RestBanquetsReserveAdapter restBanquetsReserveAdapter;
    private SimpleDateFormat simpleDateFormat;
    private Calendar calendar;
    private int type;
    private int orderType = 0; // 1为实时  2为预约
    private ChangeDataBean changeDataBean;
    private List<RestMealTypeBean> restMealTypeBeans;
    private RestMealTypeBean restMealTypeBean;
    private String dateStr;
    private String minTimes;
    private String maxTimes;

    @Override
    public int getLayoutId() {
        return R.layout.activity_rest_banquets_reserve;
    }

    @Override
    public void init() {

        ActivityUtil.addHomeActivity(this);

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();
        type = getIntent().getIntExtra("type",0);

        simpleDateFormat = new SimpleDateFormat("MM月dd日");
        if (type == 2) {
            calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 2);
            tvReserveDate.setText(simpleDateFormat.format(calendar.getTime()));
        }else {
            calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 0);
            tvReserveDate.setText(simpleDateFormat.format(new Date().getTime()));
        }

        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        dateStr = simpleDateFormat1.format(calendar.getTime());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        restBanquetsReserveAdapter = new RestBanquetsReserveAdapter(R.layout.adapter_rest_banquets_reserve,null,this);
        rvOrderDetail.setLayoutManager(linearLayoutManager);
        rvOrderDetail.setAdapter(restBanquetsReserveAdapter);

        mPresenter.queryShoppingCart(type);

        if (type == 2){
            tvReserve.setText(R.string.banquets_reserve);
            rlReserveType.setVisibility(View.GONE);
            llRestBanquets.setVisibility(View.VISIBLE);
        }else if (type == 3){
            orderType = 1;
            tvReserve.setText(R.string.order_meal_reserve);
            rlReserveType.setVisibility(View.VISIBLE);
            llRestBanquets.setVisibility(View.GONE);
            tvReserveType.setText(R.string.rightnow_order);
            rlReservePersonNum.setVisibility(View.GONE);
        }

        mPresenter.getMealType();
    }

    @Override
    protected RestBanquetsReservePresenter createPresenter() {
        return new RestBanquetsReservePresenter();
    }

    @Override
    public void queryShoppingCartSuccess(ChangeDataBean changeDataBean) {

        this.changeDataBean = changeDataBean;

        restBanquetsReserveAdapter.setNewInstance(changeDataBean.detailList);

        tvTotalPrice.setText("￥" + PuJingUtils.removeAmtLastZero(changeDataBean.totalAmount));
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void clearMyShoppingCart(ChangeDataBean changeDataBean) {
//        this.changeDataBean = changeDataBean;
    }

    @Override
    public void getMealType(List<RestMealTypeBean> beanList) {
        restMealTypeBeans = beanList;
        restMealTypeBean = restMealTypeBeans.get(0);
        tvMealType.setText(restMealTypeBean.label);

        String[] times = restMealTypeBean.meal_times.split("-");

        tvReserveTime.setText(times[0]);
    }

    @Override
    public void getOrderNumber(String orderNumber) {
//        Intent intent = new Intent(this,);
//        startActivity(intent);

        OnAccountDialog onAccountDialog = new OnAccountDialog(this,changeDataBean.totalAmount,type,orderNumber);
        onAccountDialog.show();

    }

    @Override
    public void getOrderNumberFail(String msg) {
        UToast.show(this,"下单失败");
    }

    @OnClick({R.id.iv_banquets_back,R.id.rl_reserve_date,R.id.rl_reserve_time,R.id.tv_sure_order,R.id.rl_rest_meal_type,R.id.rl_reserve_person_num,R.id.rl_reserve_type})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_banquets_back:

                finish();

                break;

            case R.id.rl_reserve_date:

                DatePickerDialog dialog = new DatePickerDialog(
                        this,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
                                tvReserveDate.setText(String.format("%02d月%02d日",(month+1),dayOfMonth));
                                dateStr = year + "-" +(month +1)  + "-" + dayOfMonth;
                            }
                        },
                        calendar.get(Calendar.YEAR), // 传入年份
                        calendar.get(Calendar.MONTH), // 传入月份
                        calendar.get(Calendar.DAY_OF_MONTH) // 传入天数
                );
                long min = 0;
                if (type == 2) {
                    min = calendar.getTime().getTime();
                }else if(type == 1){
                    min = new Date().getTime();
                }

                dialog.getDatePicker().setMinDate(min);

                dialog.show();

                break;

            case R.id.rl_reserve_time:

                String[] times = restMealTypeBean.meal_times.split("-");
                minTimes = times[0];
                maxTimes = times[1];

                ReserveTimeDialog reserveTimeDialog = new ReserveTimeDialog(this,minTimes,maxTimes);
                reserveTimeDialog.setOnListener(this);
                reserveTimeDialog.show();

                break;

            case R.id.tv_sure_order:

                if (type != 3) {
                    if (tvReserveNum.getText().toString().trim().length() == 0) {
                        UToast.show(this, R.string.rest_person_num_tip);
                        return;
                    }
                }


                RestBanquetsBean restBanquetsBean = new RestBanquetsBean();

//                restBanquetsBean.setOrderType(type);
                restBanquetsBean.setOrderingDate(dateStr);
                restBanquetsBean.setOrderType(type);

                if (type == 2) {
                    restBanquetsBean.setReservationType(2);
                }else {
                    restBanquetsBean.setReservationType(orderType);
                }

                if (orderType != 1){
                    restBanquetsBean.setMealTime(Integer.valueOf(restMealTypeBean.value));
                    restBanquetsBean.setOrderingTime(tvReserveTime.getText().toString());
                    if (type != 3){
                        restBanquetsBean.setPeopleNumber(Integer.valueOf(tvReserveNum.getText().toString()));
                    }
                }


                List<RestBanquetsBean.OrderFoodList> orderFoodLists = new ArrayList<>();
                for (int i = 0; i< changeDataBean.detailList.size();i++){
                    RestBanquetsBean.OrderFoodList orderFoodList = restBanquetsBean.new OrderFoodList();
                    orderFoodList.setFoodName(changeDataBean.detailList.get(i).name);
                    orderFoodList.setMenuItemId(changeDataBean.detailList.get(i).menuItemId);
                    orderFoodList.setNumber(changeDataBean.detailList.get(i).quantity);
                    orderFoodLists.add(orderFoodList);
                }

                restBanquetsBean.setOrderFoodList(orderFoodLists);

                mPresenter.clearMyShoppingCart(type);

                mPresenter.restOrder(restBanquetsBean.toString());

                break;

            case R.id.rl_rest_meal_type:

                RestMealTypeDialog restMealTypeDialog = new RestMealTypeDialog(this,restMealTypeBeans,this);
                restMealTypeDialog.show();

                break;

            case R.id.rl_reserve_person_num:

                View numView = LayoutInflater.from(this).inflate(R.layout.dialog_restnum,null);

                EditText etNum = numView.findViewById(R.id.et_num);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this).setView(numView).setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (etNum.getText().toString().trim().length() > 0){
                            tvReserveNum.setText(etNum.getText().toString());
                            dialog.dismiss();
                        }
                    }
                });
                alertDialog.create().show();


                break;

            case R.id.rl_reserve_type:

                ReserveTypeDialog reserveTypeDialog = new ReserveTypeDialog(this,this,orderType);
                reserveTypeDialog.show();

                break;

            default:
                break;
        }
    }

    @Override
    public void setItemValue(RestMealTypeBean restMealTypeBean) {
        if (this.restMealTypeBean == null || this.restMealTypeBean.id != restMealTypeBean.id){

            this.restMealTypeBean = restMealTypeBean;
            tvMealType.setText(restMealTypeBean.label);

            String[] times = restMealTypeBean.meal_times.split("-");
            tvReserveTime.setText(times[0]);

        }
    }

    @Override
    public void getTimeData(String time) {
        tvReserveTime.setText(time);
    }

    @Override
    public void onType(int type) {
        orderType = type;
        if (type == 1){
            tvReserveType.setText(R.string.rightnow_order);
            llRestBanquets.setVisibility(View.GONE);
        }else {
            tvReserveType.setText(R.string.reserve_order);
            llRestBanquets.setVisibility(View.VISIBLE);
        }
    }
}
