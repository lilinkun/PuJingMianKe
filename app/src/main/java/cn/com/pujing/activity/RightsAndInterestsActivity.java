package cn.com.pujing.activity;

import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.RightsAndInterestsAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.presenter.RightsAndInterestsPresenter;
import cn.com.pujing.view.RightsAndInterestsView;

/**
 * author : liguo
 * date : 2021/3/29 16:51
 * description :
 */
public class RightsAndInterestsActivity extends BaseActivity<RightsAndInterestsView, RightsAndInterestsPresenter> implements RightsAndInterestsView {

    @BindView(R.id.rv_rights_and_interests)
    RecyclerView rvRightsAndInterests;


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

        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("1");
        strings.add("1");
        strings.add("1");

        RightsAndInterestsAdapter rightsAndInterestsAdapter = new RightsAndInterestsAdapter(R.layout.adapter_rights_and_interests,strings);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rvRightsAndInterests.setLayoutManager(linearLayoutManager);

        rvRightsAndInterests.setAdapter(rightsAndInterestsAdapter);
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

}
