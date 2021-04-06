package cn.com.pujing.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gyf.immersionbar.ImmersionBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.DeviceAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.DeviceBean;
import cn.com.pujing.entity.ReserveDeviceBean;
import cn.com.pujing.entity.VenueBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.presenter.VenueReservePresenter;
import cn.com.pujing.util.ActivityUtil;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.VenueReserveView;
import cn.com.pujing.widget.ShowTimePopup;

/**
 * author : liguo
 * date : 2021/4/2 18:10
 * description :
 */
public class VenueReserveActivity extends BaseActivity<VenueReserveView, VenueReservePresenter> implements VenueReserveView{

    @BindView(R.id.tv_venue)
    TextView tvVenue;
    @BindView(R.id.rv_device)
    RecyclerView rvDevice;
    @BindView(R.id.iv_venue_pic)
    ImageView ivVenuePic;
    @BindView(R.id.tv_reserve_date)
    TextView tvReserveDate;
    @BindView(R.id.tv_reserve_time)
    TextView tvReserveTime;
    @BindView(R.id.tv_size)
    TextView tvSize;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_contain_num)
    TextView tvContainNum;

    VenueBean.Records venueBean;
    DeviceAdapter deviceAdapter;
    ReserveDeviceBean reserveDeviceBean;
    DeviceBean deviceBean;
    List<DeviceBean> deviceBeans;

    public static int ClickPos = -1;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private int mYear = 0;
    private int mMonth = 0;
    private int mDay = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_venue_reserve;
    }

    @Override
    public void initView() {

        ActivityUtil.addHomeActivity(this);
        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        venueBean = (VenueBean.Records) getIntent().getSerializableExtra("venue");

        tvVenue.setText(venueBean.name);

        mPresenter.getDevice(venueBean.id);

        deviceAdapter = new DeviceAdapter(R.layout.adapter_device,null);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvDevice.setLayoutManager(linearLayoutManager);
        rvDevice.setAdapter(deviceAdapter);

        tvReserveDate.setText(simpleDateFormat.format(System.currentTimeMillis()));

        deviceAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                deviceBean = deviceBeans.get(position);
                deviceAdapter.setClickPos(position);
                ClickPos = -1;
                mPresenter.reserveDevice(venueBean.id,deviceBean.deviceId,simpleDateFormat.format(System.currentTimeMillis()));
            }
        });

    }

    @Override
    protected VenueReservePresenter createPresenter() {
        return new VenueReservePresenter();
    }

    @OnClick({R.id.iv_back,R.id.rl_reserve_date,R.id.rl_reserve_time,R.id.tv_reserve})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:

                finish();

                break;

            case R.id.rl_reserve_date:
                Calendar c = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(
                        this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
                                mYear = year;
                                mMonth = month;
                                mDay = dayOfMonth;
                                tvReserveDate.setText(year + "-" + String.format("%02d-%02d",(month+1),dayOfMonth));
                                mPresenter.reserveDevice(venueBean.id,deviceBean.deviceId,tvReserveDate.getText().toString());
                            }
                        },
                        // 传入年份
                        c.get(Calendar.YEAR),
                        // 传入月份
                        c.get(Calendar.MONTH),
                        // 传入天数
                        c.get(Calendar.DAY_OF_MONTH)
                );

                if (mYear > 0 && mMonth > 0 && mDay > 0) {
                    dialog.updateDate(mYear, mMonth, mDay);
                }

                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dialog.show();

                break;

            case R.id.rl_reserve_time:

                onClickTime();

                break;

            case R.id.tv_reserve:
                if (tvReserveTime.getText().toString().trim().length() > 0) {
                    Intent intent = new Intent(this, VenueReserveSureActivity.class);
                    intent.putExtra("date", tvReserveDate.getText().toString());
                    intent.putExtra("time", tvReserveTime.getText().toString());
                    intent.putExtra("device", deviceBean);
                    intent.putExtra("reserveDeviceBean", reserveDeviceBean);
                    intent.putExtra("venueBean", venueBean);
                    startActivity(intent);
                }else {
                    onClickTime();
                }
                break;

            default:
                break;
        }
    }

    private void onClickTime(){
        if (reserveDeviceBean != null && reserveDeviceBean.timesReserveNumList != null) {
            ShowTimePopup showTimePopup = new ShowTimePopup(this, reserveDeviceBean);

            showTimePopup.showAsDropDown(tvVenue);
            showTimePopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    tvReserveTime.setText(reserveDeviceBean.timesReserveNumList.get(ClickPos).startEndTime);
                }
            });
        }else {
            UToast.show(this,"不好意思，今天不能预约");
        }
    }

    @Override
    public void getDeviceListSuccess(List<DeviceBean> deviceBeans) {
        deviceAdapter.setNewInstance(deviceBeans);
        this.deviceBeans = deviceBeans;
        deviceBean = deviceBeans.get(0);
        mPresenter.reserveDevice(venueBean.id,deviceBeans.get(0).deviceId,simpleDateFormat.format(System.currentTimeMillis()));
    }

    @Override
    public void getDeviceFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void getReserveDevice(ReserveDeviceBean reserveDeviceBean) {

        this.reserveDeviceBean = reserveDeviceBean;

        tvReserveTime.setText("");
        ClickPos = -1;

        Glide.with(this).load(PujingService.PREFIX + PujingService.IMG+reserveDeviceBean.venueManage.topic).into(ivVenuePic);

        tvSize.setText(reserveDeviceBean.venueManage.area + "平方米");
        tvContainNum.setText(reserveDeviceBean.venueManage.peopleNum + "人");
        tvAddress.setText(reserveDeviceBean.venueManage.address + "楼");

    }

    @Override
    public void getReserveDeviceFail(String msg) {
        this.reserveDeviceBean = null;
        ClickPos = -1;
        tvReserveTime.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ClickPos = -1;
    }
}
