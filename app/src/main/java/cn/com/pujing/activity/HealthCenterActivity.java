package cn.com.pujing.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.HealthCenterAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.HealthCenterBean;
import cn.com.pujing.presenter.HealthCenterPresenter;
import cn.com.pujing.view.HealthCenterView;
import cn.com.pujing.widget.RightAndInterestsDialog;

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

    @Override
    public int getLayoutId() {
        return R.layout.activity_health_center;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this)
                .statusBarColor(R.color.main_color)
                .fitsSystemWindows(true)
                .init();


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

        rvHealthCenter.setLayoutManager(linearLayoutManager);
        HealthCenterAdapter healthCenterAdapter = new HealthCenterAdapter(healthCenterBeans);
        rvHealthCenter.setAdapter(healthCenterAdapter);

        healthCenterAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(HealthCenterActivity.this,LifeTypeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected HealthCenterPresenter createPresenter() {
        return new HealthCenterPresenter();
    }

    @OnClick({R.id.tv_rights_and_interests,R.id.iv_health_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_rights_and_interests:

                RightAndInterestsDialog rightAndInterestsDialog = new RightAndInterestsDialog(this);
                rightAndInterestsDialog.show();

                break;

            case R.id.iv_health_back:

                finish();

                break;


            default:
                break;
        }
    }
}
