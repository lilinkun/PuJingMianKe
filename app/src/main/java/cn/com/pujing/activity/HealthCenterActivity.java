package cn.com.pujing.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.HealthCenterAdapter;
import cn.com.pujing.adapter.ServiceTitleAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.HealthCenterBean;
import cn.com.pujing.entity.ServiceBean;
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
    @BindView(R.id.tv_vip_tip)
    TextView tvVipTip;

    ServiceTitleAdapter healthCenterAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_health_center;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rvHealthCenter.setLayoutManager(linearLayoutManager);
        healthCenterAdapter = new ServiceTitleAdapter(R.layout.adapter_health_center_title,null,2);
        rvHealthCenter.setAdapter(healthCenterAdapter);

        mPresenter.getService();

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
    }

    @Override
    public void getServiceDataSuccess(List<ServiceBean> serviceBeans) {
        healthCenterAdapter.setNewInstance(serviceBeans);
    }
}
