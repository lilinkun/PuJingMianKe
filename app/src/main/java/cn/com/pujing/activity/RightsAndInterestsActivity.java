package cn.com.pujing.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
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
import cn.com.pujing.adapter.RightsAndInterestsAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.RightsAndInterestsBean;
import cn.com.pujing.presenter.RightsAndInterestsPresenter;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.RightsAndInterestsView;

/**
 * author : liguo
 * date : 2021/3/29 16:51
 * description :
 */
public class RightsAndInterestsActivity extends BaseActivity<RightsAndInterestsView, RightsAndInterestsPresenter> implements RightsAndInterestsView {

    @BindView(R.id.rv_rights_and_interests)
    RecyclerView rvRightsAndInterests;

    private List<RightsAndInterestsBean> rightsAndInterestsBeans;
    private RightsAndInterestsAdapter rightsAndInterestsAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_rights_and_interests;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this)
                .statusBarColor(R.color.main_color)
                .fitsSystemWindows(true)
                .init();


        mPresenter.getRightsAndInterestsData();

        rightsAndInterestsAdapter = new RightsAndInterestsAdapter(R.layout.adapter_rights_and_interests,null);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rvRightsAndInterests.setLayoutManager(linearLayoutManager);

        rvRightsAndInterests.setAdapter(rightsAndInterestsAdapter);

        rightsAndInterestsAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(RightsAndInterestsActivity.this,RightsAndInterestsDetailActivity.class);
                intent.putExtra("id",rightsAndInterestsBeans.get(position).id);
                startActivity(intent);
            }
        });
    }

    @Override
    protected RightsAndInterestsPresenter createPresenter() {
        return new RightsAndInterestsPresenter();
    }


    @OnClick({R.id.iv_back})
    public void onClick(View view){
        if (view.getId() == R.id.iv_back){
            finish();
        }
    }

    @Override
    public void getRightsAndInterestsListSuccess(List<RightsAndInterestsBean> rightsAndInterestsBeans) {
        this.rightsAndInterestsBeans = rightsAndInterestsBeans;
        rightsAndInterestsAdapter.setNewInstance(rightsAndInterestsBeans);
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(this,msg);
    }
}
