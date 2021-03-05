package cn.com.pujing.fragment;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.TabPageAdapter;
import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.entity.ActivityCalendar;
import cn.com.pujing.entity.Base;
import cn.com.pujing.entity.GetAllCategory;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.Urls;

public class ActivitiesFragment extends BaseFragment{

    @BindView(R.id.tv_my_enroll)
    TextView tvMyEnroll;
    @BindView(R.id.stl_list_activities)
    SlidingTabLayout stlListActivities;
    @BindView(R.id.vp_list_exerciset)
    ViewPager vpListExerciset;
    MyActivitiesFragment myActivitiesFragment = new MyActivitiesFragment();

    @Override
    public int getlayoutId() {
        return R.layout.fragment_activities;
    }

    @Override
    public void initEventAndData() {

        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new CurrentHotFragment());
        fragmentList.add(new HistoryActivitiesFragment());
        fragmentList.add(myActivitiesFragment);
        List<String> strings = new ArrayList<>();
        strings.add(getContext().getString(R.string.cur_hot_activities));
        strings.add(getContext().getString(R.string.cur_history_activities));
        strings.add(getContext().getString(R.string.cur_my_activities));

        TabPageAdapter tabPageAdapter = new TabPageAdapter(getChildFragmentManager(), fragmentList,strings);
        vpListExerciset.setAdapter(tabPageAdapter);
        stlListActivities.setViewPager(vpListExerciset);
        vpListExerciset.setOffscreenPageLimit(1);
        vpListExerciset.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2){
                    myActivitiesFragment.getData();
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
    protected BasePresenter createPresenter() {
        return null;
    }

}
