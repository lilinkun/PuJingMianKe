package cn.com.pujing.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gyf.immersionbar.ImmersionBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.DeviceAdapter;
import cn.com.pujing.adapter.ServiceTipAdapter;
import cn.com.pujing.adapter.ServiceTypeAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.BasicServiceVoListBean;
import cn.com.pujing.entity.LifeTypeBean;
import cn.com.pujing.entity.ServiceItemsBean;
import cn.com.pujing.entity.ServicePutawayManageTimeBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.presenter.LifeTypePresenter;
import cn.com.pujing.util.ActivityUtil;
import cn.com.pujing.util.PuJingUtils;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.LifeTypeView;
import cn.com.pujing.widget.ShowServiceTimePopup;
import cn.com.pujing.widget.ShowTimePopup;

/**
 * author : liguo
 * date : 2021/3/26 16:20
 * description :
 */
public class LifeTypeActivity extends BaseActivity<LifeTypeView, LifeTypePresenter> implements LifeTypeView {

    @BindView(R.id.tv_reserve_date)
    TextView tvReserveDate;
    @BindView(R.id.rv_service_type)
    RecyclerView rvServiceType;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.tv_reserve_time)
    TextView tvReserveTime;
    @BindView(R.id.rv_service_tip)
    RecyclerView rvServiceTip;
    @BindView(R.id.tv_service_tip)
    TextView tvServiceTip;
    @BindView(R.id.iv_top_pic)
    ImageView ivTopPic;

    private ServiceTypeAdapter serviceTypeAdapter;
    private ServiceTipAdapter serviceTipAdapter;
    private BasicServiceVoListBean basicServiceVoListBean;
    private LifeTypeBean lifeTypeBeans;
    private ServiceItemsBean serviceItemsBean;
    private List<ServicePutawayManageTimeBean> servicePutawayManageTimeBeans;
    public static int ClickServicePos = -1;
    private int pos = 0;
    private int category = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_life_type;
    }

    @Override
    public void initView() {

        ActivityUtil.addHomeActivity(this);

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        basicServiceVoListBean = (BasicServiceVoListBean)getIntent().getSerializableExtra("basicservicevolistbean");

        category = getIntent().getIntExtra("category",0);

        mPresenter.getLifeType(basicServiceVoListBean.id);

        tvTitleName.setText(basicServiceVoListBean.name);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String cDay = simpleDateFormat.format(System.currentTimeMillis());

        tvReserveDate.setText(cDay);

        serviceTypeAdapter = new ServiceTypeAdapter(R.layout.adapter_service_type,null);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvServiceType.setLayoutManager(linearLayoutManager);
        rvServiceType.setAdapter(serviceTypeAdapter);

        serviceTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                serviceTypeAdapter.setClickPos(position);
                serviceItemsBean = lifeTypeBeans.serviceItemsList.get(position);
                serviceTypeAdapter.setClickPos(position);
                ClickServicePos = -1;
                pos = position;
                mPresenter.getLifeTime(basicServiceVoListBean.id+"",lifeTypeBeans.serviceItemsList.get(position).id+"",tvReserveDate.getText().toString());

            }
        });

        String[] strings =  getResources().getStringArray(R.array.service_type);

        serviceTipAdapter = new ServiceTipAdapter(R.layout.adapter_service_tip, Arrays.asList(strings));

        GridLayoutManager linearLayoutManager1 = new GridLayoutManager(this,4);
//        linearLayoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        rvServiceTip.setLayoutManager(linearLayoutManager1);
        rvServiceTip.setAdapter(serviceTipAdapter);

        serviceTipAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                serviceTipAdapter.onClickItem(position);

                if (position == 0) {
                    tvServiceTip.setText(lifeTypeBeans.content);
                }else if (position == 1){
                    tvServiceTip.setText(lifeTypeBeans.costExplain);
                }else if (position == 2){
                    tvServiceTip.setText(lifeTypeBeans.providePeopleId);
                }else if (position == 3){
                    tvServiceTip.setText(lifeTypeBeans.address);
                }
            }
        });

    }

    @Override
    protected LifeTypePresenter createPresenter() {
        return new LifeTypePresenter();
    }


    @OnClick({R.id.tv_reserve_order,R.id.ll_reserve_order_time,R.id.iv_lifetype_back,R.id.rl_reserve_time})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_reserve_order:

                if (tvReserveTime.getText().toString().trim().length() > 0) {
                    Intent intent = new Intent(this, ServiceReserveActivity.class);
                    intent.putExtra("date", tvReserveDate.getText().toString());
                    intent.putExtra("time", tvReserveTime.getText().toString());
                    intent.putExtra("basicservicevolistbean", basicServiceVoListBean);
                    intent.putExtra("category", category);
                    intent.putExtra("serviceitemsbean", serviceItemsBean);
                    intent.putExtra("content", lifeTypeBeans.content);
                    startActivity(intent);
                }else {
                    onClickTime();
                }
                break;

            case R.id.ll_reserve_order_time:

                Calendar c = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(
                        this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
                                tvReserveDate.setText(year + "-" + String.format("%02d-%02d",(month+1),dayOfMonth));
                                if (lifeTypeBeans != null ) {
                                    if (lifeTypeBeans.serviceItemsList != null && lifeTypeBeans.serviceItemsList.size() >= pos) {
                                        mPresenter.getLifeTime(basicServiceVoListBean.id + "", lifeTypeBeans.serviceItemsList.get(pos).id + "", tvReserveDate.getText().toString());
                                    }
                                }
                            }
                        },
                        // 传入年份
                        c.get(Calendar.YEAR),
                        // 传入月份
                        c.get(Calendar.MONTH),
                        // 传入天数
                        c.get(Calendar.DAY_OF_MONTH)
                );

                dialog.getDatePicker().setMinDate(System.currentTimeMillis());

                c.set(Calendar.DATE,1);
                c.roll(Calendar.DATE,-1);

                dialog.getDatePicker().setMaxDate(c.getTime().getTime());
                dialog.show();

                break;

            case R.id.iv_lifetype_back:

                finish();

                break;

            case R.id.rl_reserve_time:

                onClickTime();

                break;

            default:
                break;
        }
    }

    private void onClickTime(){
        if (lifeTypeBeans != null && lifeTypeBeans.servicePutawayManageTimeList != null) {
            ShowServiceTimePopup showTimePopup = new ShowServiceTimePopup(this, servicePutawayManageTimeBeans);

            showTimePopup.showAsDropDown(tvTitleName);
            showTimePopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    if (ClickServicePos != -1) {
                        tvReserveTime.setText(lifeTypeBeans.servicePutawayManageTimeList.get(ClickServicePos).timeQuantum);
                    }
                }
            });
        }else {
            UToast.show(this,"不好意思，今天不能预约");
        }
    }


    @Override
    public void getLifeType(LifeTypeBean lifeTypeBeans) {
        this.lifeTypeBeans = lifeTypeBeans;
        serviceTypeAdapter.setNewInstance(lifeTypeBeans.serviceItemsList);
        tvServiceTip.setText(lifeTypeBeans.content);
        serviceItemsBean = lifeTypeBeans.serviceItemsList.get(0);

        Glide.with(this).load(PujingService.PREFIX + PujingService.IMG + lifeTypeBeans.themePic)
                .apply(PuJingUtils.setGlideCircle(10)).into(ivTopPic);

        mPresenter.getLifeTime(basicServiceVoListBean.id+"",lifeTypeBeans.serviceItemsList.get(0).id+"",tvReserveDate.getText().toString());

    }

    @Override
    public void getLifeTypeDataFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void getLifeTimeSuccess(List<ServicePutawayManageTimeBean> servicePutawayManageTimeBeans) {
        this.servicePutawayManageTimeBeans = servicePutawayManageTimeBeans;
    }

    @Override
    public void getLifeTimeFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ClickServicePos = -1;
    }
}
