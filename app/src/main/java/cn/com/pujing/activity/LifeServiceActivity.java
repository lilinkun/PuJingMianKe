package cn.com.pujing.activity;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.HealthCenterAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.HealthCenterBean;
import cn.com.pujing.entity.ServiceBean;
import cn.com.pujing.presenter.LifeServicePresenter;
import cn.com.pujing.view.LifeServiceView;

/**
 * author : liguo
 * date : 2021/3/26 15:39
 * description :
 */
public class LifeServiceActivity extends BaseActivity<LifeServiceView, LifeServicePresenter> implements LifeServiceView{

    @BindView(R.id.rv_life_service)
    RecyclerView rvLifeService;

    @Override
    public int getLayoutId() {
        return R.layout.activity_lifeservice;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        ArrayList<HealthCenterBean> healthCenterBeans = new ArrayList<>();

        for (int i = 0; i < 20; i++){
            HealthCenterBean healthCenterBean = new HealthCenterBean();
            if (i%2 == 0) {
                healthCenterBean.setTitle(true);
            }else {
                healthCenterBean.setTitle(false);
            }

            healthCenterBean.setProjectTypeTitle("按摩理疗");
            healthCenterBean.setProjectTitleName("推拿");
            healthCenterBean.setProjectContent("颈肩/腰部推拿  168元/60分钟");
            healthCenterBeans.add(healthCenterBean);
        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rvLifeService.setLayoutManager(linearLayoutManager);
        HealthCenterAdapter healthCenterAdapter = new HealthCenterAdapter(healthCenterBeans);
        rvLifeService.setAdapter(healthCenterAdapter);

        mPresenter.getService();

    }

    @Override
    protected LifeServicePresenter createPresenter() {
        return new LifeServicePresenter();
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void getServiceDataSuccess(List<ServiceBean> serviceBeans) {

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
