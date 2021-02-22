package cn.com.pujing.fragment;

import android.view.View;
import android.widget.TextView;

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
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.entity.ActivityCalendar;
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

    @Override
    public int getlayoutId() {
        return R.layout.fragment_activities;
    }

    @Override
    public void initEventAndData() {

        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new CurrentHotFragment());
        fragmentList.add(new CurrentHotFragment());
        fragmentList.add(new MyActivitiesFragment());
        List<String> strings = new ArrayList<>();
        strings.add("当前热门");
        strings.add("历史活动");
        strings.add("我的活动");

        TabPageAdapter tabPageAdapter = new TabPageAdapter(getActivity().getSupportFragmentManager(), fragmentList,strings);
        vpListExerciset.setAdapter(tabPageAdapter);
        stlListActivities.setViewPager(vpListExerciset);

        OkGo.get(Urls.GETALLCATEGORY)
                .tag(this)
                .execute(new JsonCallback<>(GetAllCategory.class, this));
    }


    @Override
    public boolean immersionBarEnabled() {
        return true;
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).titleBar((R.id.v_title_bar)).init();
    }
}
