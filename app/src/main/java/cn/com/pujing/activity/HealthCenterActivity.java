package cn.com.pujing.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.ImageNetAdapter;
import cn.com.pujing.adapter.ServiceTitleAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.BannerBean;
import cn.com.pujing.entity.BasicServiceVoListBean;
import cn.com.pujing.entity.ServiceBean;
import cn.com.pujing.entity.VipBean;
import cn.com.pujing.presenter.HealthCenterPresenter;
import cn.com.pujing.util.ActivityUtil;
import cn.com.pujing.util.PuJingUtils;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.HealthCenterView;

/**
 * author : liguo
 * date : 2021/3/26 11:09
 * description :
 */
public class HealthCenterActivity extends BaseActivity<HealthCenterView, HealthCenterPresenter> implements HealthCenterView{

    @BindView(R.id.tv_rights_and_interests)
    TextView tvRightsAndInterests;
    @BindView(R.id.rv_health_center)
    RecyclerView rvHealthCenter;
    @BindView(R.id.tv_vip_tip)
    TextView tvVipTip;
    @BindView(R.id.tv_vip)
    TextView tvVip;
    @BindView(R.id.tv_content_tip)
    TextView tvContentTip;
    @BindView(R.id.tv_content_base_tip)
    TextView tvContentBaseTip;
    @BindView(R.id.banner_life_service)
    Banner bannerLifeService;

    ServiceTitleAdapter healthCenterAdapter;
    private ImageNetAdapter imageNetAdapter;
    private List<BannerBean> bannerBeans;

    @Override
    public int getLayoutId() {
        return R.layout.activity_health_center;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        ActivityUtil.addHomeActivity(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rvHealthCenter.setLayoutManager(linearLayoutManager);
        healthCenterAdapter = new ServiceTitleAdapter(R.layout.adapter_health_center_title,null,2);
        rvHealthCenter.setAdapter(healthCenterAdapter);

        mPresenter.getBannerData();

        mPresenter.getService();

        mPresenter.getVip();

        imageNetAdapter = new ImageNetAdapter(null,1);
        bannerLifeService.setAdapter(imageNetAdapter);
        bannerLifeService.setIndicator(new CircleIndicator(this));
        bannerLifeService.setIndicatorSelectedColor(getResources().getColor(R.color.white));
        bannerLifeService.setIndicatorSelectedColor(getResources().getColor(R.color.banner_normal));

        bannerLifeService.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {

                BannerBean bannerBean = bannerBeans.get(position);

                PuJingUtils.bannerClick(HealthCenterActivity.this,bannerBean);

            }
        });

    }

    @Override
    protected HealthCenterPresenter createPresenter() {
        return new HealthCenterPresenter();
    }

    @OnClick({R.id.tv_rights_and_interests,R.id.iv_health_back,R.id.tv_mycard,R.id.tv_vip_tip})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_rights_and_interests:

                Intent intent1 = new Intent();
                intent1.setClass(this,RightsAndInterestsActivity.class);
                startActivity(intent1);

                break;

            case R.id.iv_health_back:

                finish();

                break;

            case R.id.tv_mycard:

                Intent intent = new Intent();
                intent.setClass(this,MyCardActivity.class);
                startActivity(intent);

                break;

            case R.id.tv_vip_tip:

                View vipView = LayoutInflater.from(this).inflate(R.layout.dialog_vip_tip,null);

                TextView chooserView = vipView.findViewById(R.id.tv_choose);

                AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(vipView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                chooserView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                break;

            default:
                break;
        }
    }

    @Override
    public void getDataFail(String msg) {

        if (msg.contains("sorry")){
            msg = msg.substring(5,msg.length());
            UToast.show(this,msg);
            finish();
        }

    }

    @Override
    public void getServiceDataSuccess(List<ServiceBean> serviceBeans) {
        healthCenterAdapter.setNewInstance(serviceBeans);


        /*List<BasicServiceVoListBean> strings = new ArrayList<>();
        for (int i = 0;i<serviceBeans.size();i++){
            if (serviceBeans.get(i).getBasicServiceVoList() != null && serviceBeans.get(i).getBasicServiceVoList().size() > 0) {
                for (int j = 0; j < serviceBeans.get(i).getBasicServiceVoList().size(); j++) {
                    strings.add(serviceBeans.get(i).getBasicServiceVoList().get(j));
                }
            }
        }

        imageNetAdapter.setDatas(strings);*/
    }

    @Override
    public void getVip(VipBean vipBean) {
        if (vipBean.healthVip){
            tvVip.setText("VIP会员-有效期至：" + vipBean.expires);
            tvContentTip.setVisibility(View.GONE);
            tvVipTip.setText("续费");
        }else {
            tvContentTip.setVisibility(View.VISIBLE);
            tvVip.setText("暂未开通会员");
            tvVipTip.setText(vipBean.vipCost + "开通会员");
            if (vipBean.incrementServiceDiscount > 0 && vipBean.incrementServiceDiscount < 10) {
                tvContentTip.setText("开通会员尊享增值服务"+
                        PuJingUtils.removeAmtLastZero(vipBean.incrementServiceDiscount)+"折");

            }else if (vipBean.incrementServiceDiscount == 0){
                tvContentTip.setText("开通会员尊享增值服务免费");
            }else {
                tvContentTip.setVisibility(View.GONE);
            }

            if (vipBean.basicServiceDiscount > 0 && vipBean.basicServiceDiscount < 10) {
                tvContentBaseTip.setText("开通会员尊享基础服务"+
                        PuJingUtils.removeAmtLastZero(vipBean.basicServiceDiscount)+"折");

            }else if (vipBean.basicServiceDiscount == 0){
                tvContentBaseTip.setText("开通会员尊享基础服务免费");
            }else {
                tvContentBaseTip.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void getBannerDataSuccess(List<BannerBean> data) {
        bannerBeans = data;
        imageNetAdapter.setDatas(data);
        imageNetAdapter.notifyDataSetChanged();
    }

    @Override
    public void getBannerDataFail(String msg) {
        UToast.show(this,msg);
    }
}
