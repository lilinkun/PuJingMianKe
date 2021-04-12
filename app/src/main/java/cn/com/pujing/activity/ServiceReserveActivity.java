package cn.com.pujing.activity;

import android.content.DialogInterface;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.BasicServiceVoListBean;
import cn.com.pujing.entity.DeviceBean;
import cn.com.pujing.entity.MyCardBean;
import cn.com.pujing.entity.ReserveDeviceBean;
import cn.com.pujing.entity.ServiceItemsBean;
import cn.com.pujing.entity.VenueBean;
import cn.com.pujing.presenter.ServiceReservePresenter;
import cn.com.pujing.util.ActivityUtil;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.ServiceReserveView;
import cn.com.pujing.widget.RightAndInterestsDialog;

/**
 * author : liguo
 * date : 2021/3/26 17:51
 * description :
 */
public class ServiceReserveActivity extends BaseActivity<ServiceReserveView, ServiceReservePresenter> implements ServiceReserveView, RightAndInterestsDialog.OnChooseItemsListener {

    @BindView(R.id.tv_service_name)
    TextView tvServiceName;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_service_description)
    TextView tvDescription;
    @BindView(R.id.tv_service_content)
    TextView tvServiceContent;
    @BindView(R.id.tv_usecoupon)
    TextView tvUseCoupon;
    @BindView(R.id.rl_rights_and_interests)
    RelativeLayout rlRightsAndInterests;

    private String time;
    private String date;
    private BasicServiceVoListBean basicServiceVoListBean;
    private ServiceItemsBean serviceitemsbean;
    private int category;
    private String customerVoucherId = "0";

    @Override
    public int getLayoutId() {
        return R.layout.activity_service_reserve;
    }

    @Override
    public void initView() {

        ActivityUtil.addHomeActivity(this);

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("time");
        String content = getIntent().getStringExtra("content");
        basicServiceVoListBean = (BasicServiceVoListBean)getIntent().getSerializableExtra("basicservicevolistbean");
        category = getIntent().getIntExtra("category",0);
        serviceitemsbean = (ServiceItemsBean)getIntent().getSerializableExtra("serviceitemsbean");
        tvDate.setText(date + " " + time);
        tvServiceName.setText(serviceitemsbean.name);
        tvServiceContent.setText(content);
//        tvDescription.setText(serviceitemsbean.name);

        if(category == 1){
            rlRightsAndInterests.setVisibility(View.GONE);
        }
    }

    @Override
    protected ServiceReservePresenter createPresenter() {
        return new ServiceReservePresenter();
    }

    @OnClick({R.id.iv_lifeservice_back,R.id.rl_rights_and_interests,R.id.tv_reserve_order})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_lifeservice_back:

                finish();

                break;

            case R.id.rl_rights_and_interests:

                mPresenter.useCoupon(date,serviceitemsbean.id+"");


                break;

            case R.id.tv_reserve_order:

                mPresenter.reserveService(date,time,serviceitemsbean.id+"",serviceitemsbean.name,basicServiceVoListBean.id+"",category+"",customerVoucherId);

                break;

            default:

                break;
        }
    }

    @Override
    public void serviceReserveSuccess() {
        ActivityUtil.finishHomeAll();
    }

    @Override
    public void serviceReserveFail(String msg) {

    }

    @Override
    public void getCardDataSuccess(List<MyCardBean> myCardBeans) {

        if (myCardBeans != null  && myCardBeans.size() > 0) {
            RightAndInterestsDialog rightAndInterestsDialog = new RightAndInterestsDialog(this, myCardBeans,this);
            rightAndInterestsDialog.show();

        }else {
            UToast.show(this,"您没有权益券");
        }
    }

    @Override
    public void getCardDataFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void onChooseItems(int pos) {
        if (pos == -1){
            customerVoucherId = "0";
            tvUseCoupon.setText("");
        }else {
            customerVoucherId = pos + "";
            tvUseCoupon.setText("已选择1张权益券");
        }
    }
}
