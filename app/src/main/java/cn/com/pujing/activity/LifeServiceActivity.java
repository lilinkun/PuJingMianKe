package cn.com.pujing.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

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
import cn.com.pujing.adapter.HealthCenterAdapter;
import cn.com.pujing.adapter.ImageNetAdapter;
import cn.com.pujing.adapter.ServiceImageNetAdapter;
import cn.com.pujing.adapter.ServiceTitleAdapter;
import cn.com.pujing.adapter.VenueImageNetAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.BannerBean;
import cn.com.pujing.entity.BasicServiceVoListBean;
import cn.com.pujing.entity.HealthCenterBean;
import cn.com.pujing.entity.ServiceBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.presenter.LifeServicePresenter;
import cn.com.pujing.util.ActivityUtil;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.PuJingUtils;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.LifeServiceView;

/**
 * author : liguo
 * date : 2021/3/26 15:39
 * description :
 */
public class LifeServiceActivity extends BaseActivity<LifeServiceView, LifeServicePresenter> implements LifeServiceView{

    @BindView(R.id.rv_life_service)
    RecyclerView rvLifeService;
    @BindView(R.id.banner_life_service)
    Banner bannerLifeService;

    private ServiceTitleAdapter healthCenterAdapter;
    private ImageNetAdapter imageNetAdapter;
    private List<BannerBean> bannerBeans;

    @Override
    public int getLayoutId() {
        return R.layout.activity_lifeservice;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        ActivityUtil.addHomeActivity(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rvLifeService.setLayoutManager(linearLayoutManager);
        healthCenterAdapter = new ServiceTitleAdapter(R.layout.adapter_health_center_title,null,1);
        rvLifeService.setAdapter(healthCenterAdapter);

        mPresenter.getService();

        mPresenter.getBannerData();

        imageNetAdapter = new ImageNetAdapter(null,1);
        bannerLifeService.setAdapter(imageNetAdapter);
        bannerLifeService.setIndicator(new CircleIndicator(this));
        bannerLifeService.setIndicatorSelectedColor(getResources().getColor(R.color.white));
        bannerLifeService.setIndicatorSelectedColor(getResources().getColor(R.color.banner_normal));

        bannerLifeService.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {


                BannerBean bannerBean = bannerBeans.get(position);

                PuJingUtils.bannerClick(LifeServiceActivity.this,bannerBean);

            }
        });
    }

    @Override
    protected LifeServicePresenter createPresenter() {
        return new LifeServicePresenter();
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void getServiceDataSuccess(List<ServiceBean> serviceBeans) {
        healthCenterAdapter.setNewInstance(serviceBeans);

        List<BasicServiceVoListBean> strings = new ArrayList<>();
        for (int i = 0;i<serviceBeans.size();i++){
            if (serviceBeans.get(i).getBasicServiceVoList() != null && serviceBeans.get(i).getBasicServiceVoList().size() > 0) {
                for (int j = 0; j < serviceBeans.get(i).getBasicServiceVoList().size(); j++) {
                    strings.add(serviceBeans.get(i).getBasicServiceVoList().get(j));
                }
            }
        }


    }

    @Override
    public void getBannerDataSuccess(List<BannerBean> data) {
        this.bannerBeans = data;
        imageNetAdapter.setDatas(bannerBeans);
        imageNetAdapter.notifyDataSetChanged();
    }

    @Override
    public void getBannerDataFail(String msg) {

    }

    @OnClick({R.id.iv_lifeservice_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_lifeservice_back:

                finish();

                break;

            default:

                break;
        }
    }
}
