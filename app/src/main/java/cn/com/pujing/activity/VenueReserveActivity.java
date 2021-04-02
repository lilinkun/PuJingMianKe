package cn.com.pujing.activity;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
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
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.VenueReserveView;

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

    VenueBean venueBean;
    DeviceAdapter deviceAdapter;
    DeviceBean deviceBean;
    List<DeviceBean> deviceBeans;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public int getLayoutId() {
        return R.layout.activity_venue_reserve;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        venueBean = (VenueBean) getIntent().getSerializableExtra("venue");

        tvVenue.setText(venueBean.venueName);

        mPresenter.getDevice(venueBean.venueId);

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

            }
        });

    }

    @Override
    protected VenueReservePresenter createPresenter() {
        return new VenueReservePresenter();
    }

    @OnClick({R.id.iv_back,R.id.ll_reserve_order_time})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:

                finish();

                break;

            case R.id.ll_reserve_order_time:
                Calendar
                c = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(
                        this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
                                tvReserveDate.setText(year + "-" + String.format("%02d-%02d",(month+1),dayOfMonth));
                                mPresenter.reserveDevice(venueBean.venueId,deviceBean.deviceId,tvReserveDate.getText().toString());
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
                dialog.show();

                break;

            default:
                break;
        }
    }

    @Override
    public void getDeviceListSuccess(List<DeviceBean> deviceBeans) {
        deviceAdapter.setNewInstance(deviceBeans);
        this.deviceBeans = deviceBeans;
        deviceBean = deviceBeans.get(0);
        mPresenter.reserveDevice(venueBean.venueId,deviceBeans.get(0).deviceId,simpleDateFormat.format(System.currentTimeMillis()));
    }

    @Override
    public void getDeviceFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void getReserveDevice(ReserveDeviceBean reserveDeviceBean) {

        Glide.with(this).load(PujingService.PREFIX + PujingService.IMG+reserveDeviceBean.venueManage.topic).into(ivVenuePic);


    }
}
