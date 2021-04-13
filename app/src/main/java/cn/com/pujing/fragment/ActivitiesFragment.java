package cn.com.pujing.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.TabPageAdapter;
import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.ActivityTypeBean;
import cn.com.pujing.entity.OrderTpeBean;
import cn.com.pujing.presenter.ActivitiesPresenter;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.ActivitiesView;
import cn.com.pujing.widget.MyActivityPopup;
import cn.com.pujing.widget.MyOrderPopup;

public class ActivitiesFragment extends BaseFragment<ActivitiesView, ActivitiesPresenter> implements ActivitiesView,MyActivityPopup.MyOrderClickListener {

    @BindView(R.id.stl_list_activities)
    SlidingTabLayout stlListActivities;
    @BindView(R.id.vp_list_exerciset)
    ViewPager vpListExerciset;
    @BindView(R.id.iv_activity_filter)
    ImageView ivActivityFilter;

    MyActivitiesFragment myActivitiesFragment = new MyActivitiesFragment();
    CurrentHotFragment currentHotFragment = new CurrentHotFragment();

    private List<ActivityTypeBean> activityTypeBeans;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_activities;
    }

    @Override
    public void initEventAndData() {

        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(currentHotFragment);
        fragmentList.add(new HistoryActivitiesFragment());
        fragmentList.add(myActivitiesFragment);
        List<String> strings = new ArrayList<>();
        strings.add(getContext().getString(R.string.cur_hot_activities));
        strings.add(getContext().getString(R.string.cur_history_activities));
        strings.add(getContext().getString(R.string.cur_my_activities));

        mPresenter.getActivityType();

        TabPageAdapter tabPageAdapter = new TabPageAdapter(getChildFragmentManager(), fragmentList,strings);
        vpListExerciset.setAdapter(tabPageAdapter);
        stlListActivities.setViewPager(vpListExerciset);
        vpListExerciset.setOffscreenPageLimit(1);
        vpListExerciset.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2){
                    myActivitiesFragment.getData();
                }

                if (position == 0){
                    ivActivityFilter.setVisibility(View.VISIBLE);
                }else {
                    ivActivityFilter.setVisibility(View.GONE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public boolean immersionBarEnabled() {
        return true;
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).titleBar((R.id.v_title_bar)).init();
    }

    @Override
    protected ActivitiesPresenter createPresenter() {
        return new ActivitiesPresenter();
    }

    @OnClick({R.id.iv_activity_filter})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_activity_filter:

                MyActivityPopup myOrderPopup = new MyActivityPopup(getActivity(),this,activityTypeBeans);
                myOrderPopup.showAsDropDown(getActivity().findViewById(R.id.v_title_bar));

                break;

            default:
                break;
        }
    }

    @Override
    public void getActivityTypeSuccess(List<ActivityTypeBean> activityTypeBeans) {
        this.activityTypeBeans = activityTypeBeans;
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(getActivity(),msg);
    }

    @Override
    public void setItemValue(OrderTpeBean orderTpeBean, OrderTpeBean orderTpeBean1, String startDate, String endDate) {
        String orderType = "";
        String orderScale = "";
        if (orderTpeBean != null){
            orderType = orderTpeBean.typeId+"";
        }

        if (orderTpeBean1 != null){
            orderScale = orderTpeBean1.typeId + "";
        }

        currentHotFragment.setHotPresenter(endDate,startDate,orderType,orderScale);
    }
}
