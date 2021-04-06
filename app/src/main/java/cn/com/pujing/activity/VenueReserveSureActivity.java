package cn.com.pujing.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.DeviceBean;
import cn.com.pujing.entity.ReserveDeviceBean;
import cn.com.pujing.entity.VenueBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.presenter.VenueReserveSurePresenter;
import cn.com.pujing.util.ActivityUtil;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.VenueReserveSureView;

/**
 * author : liguo
 * date : 2021/4/6 17:03
 * description :
 */
public class VenueReserveSureActivity extends BaseActivity<VenueReserveSureView, VenueReserveSurePresenter> implements VenueReserveSureView{

    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_device_name)
    TextView tvDeviceName;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.iv_venue_pic)
    ImageView ivVenuePic;

    private DeviceBean device;
    private VenueBean.Records venueBean;
    private String time;
    private String date;
    private ReserveDeviceBean reserveDeviceBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_venue_sure;
    }

    @Override
    public void initView() {

        ActivityUtil.addHomeActivity(this);
        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("time");
        device = (DeviceBean)getIntent().getSerializableExtra("device");
        venueBean = (VenueBean.Records)getIntent().getSerializableExtra("venueBean");
        reserveDeviceBean = (ReserveDeviceBean)getIntent().getSerializableExtra("reserveDeviceBean");
        tvDate.setText(date + " " + time);
        tvDeviceName.setText(device.deviceName);
        tvDescription.setText(reserveDeviceBean.venueManage.description);

        Glide.with(this).load(PujingService.PREFIX + PujingService.IMG + reserveDeviceBean.venueManage.topic).into(ivVenuePic);

    }

    @Override
    protected VenueReserveSurePresenter createPresenter() {
        return new VenueReserveSurePresenter();
    }

    @Override
    public void venueReserveSuccess(Boolean aBoolean) {
        if (aBoolean) {

            ActivityUtil.finishHomeAll();
        }
    }

    @Override
    public void venueReserveFail(String msg) {
        UToast.show(this,msg + "");
    }

    @OnClick({R.id.iv_back,R.id.tv_sumbit_venue_reserve})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:

                finish();

                break;

            case R.id.tv_sumbit_venue_reserve:

                mPresenter.reserveSure(venueBean.id,device.deviceId,date,time);

                break;

            default:

                break;
        }
    }
}
