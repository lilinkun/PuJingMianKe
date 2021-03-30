package cn.com.pujing.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import cn.com.pujing.R;
import cn.com.pujing.adapter.RightsAndInterestsDetailAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.RightsAndInterestsBean;
import cn.com.pujing.entity.RightsVoucherVoBean;
import cn.com.pujing.presenter.RightsAndInterestsDetailPresenter;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.RightsAndInterestsDetailView;

/**
 * author : liguo
 * date : 2021/3/30 13:50
 * description :
 */
public class RightsAndInterestsDetailActivity extends BaseActivity<RightsAndInterestsDetailView, RightsAndInterestsDetailPresenter> implements RightsAndInterestsDetailView{

    @BindView(R.id.rv_rights_and_interests_details)
    RecyclerView rvRightsAndInterestsDetails;

    private RightsAndInterestsDetailAdapter rightsAndInterestsDetailAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_rights_and_interests_detail;
    }

    @Override
    public void initView() {

        rightsAndInterestsDetailAdapter = new RightsAndInterestsDetailAdapter(R.layout.adapter_rights_and_interests_detail,null);

        String id = getIntent().getStringExtra("id");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rvRightsAndInterestsDetails.setLayoutManager(linearLayoutManager);

        rvRightsAndInterestsDetails.setAdapter(rightsAndInterestsDetailAdapter);

        mPresenter.getRightsAndInterestsData(id);
    }

    @Override
    protected RightsAndInterestsDetailPresenter createPresenter() {
        return new RightsAndInterestsDetailPresenter();
    }

    @Override
    public void getRightsAndInterestsListSuccess(RightsAndInterestsBean rightsAndInterestsBean) {
        rightsAndInterestsDetailAdapter.setNewInstance((List<RightsVoucherVoBean>) rightsAndInterestsBean.rightsVoucherVoList);
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(this,msg);
    }
}
