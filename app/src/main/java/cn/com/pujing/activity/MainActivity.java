package cn.com.pujing.activity;

import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gyf.immersionbar.ImmersionBar;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;

import butterknife.BindView;
import cn.com.pujing.R;
import cn.com.pujing.adapter.VpAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.TabEntity;
import cn.com.pujing.fragment.ActivitiesFragment;
import cn.com.pujing.fragment.ExerciseFragment;
import cn.com.pujing.fragment.HomeFragment;
import cn.com.pujing.fragment.MineFragment;
import cn.com.pujing.fragment.RestaurantFragment;
import cn.com.pujing.util.ActivityUtil;

public class MainActivity extends BaseActivity {

    @BindView(R.id.vp)
    ViewPager viewPager;
    @BindView(R.id.bnv)
    BottomNavigationViewEx bottomNavigationViewEx;
    @BindView(R.id.ctl_main)
    CommonTabLayout commonTablayout;

    private ArrayList<BaseFragment> fragmentList;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private int[] mIconUnselectIds = {
            R.mipmap.ic_unselect_home, R.mipmap.ic_unselect_restaurant,
            R.mipmap.ic_unselect_exercise, R.mipmap.ic_unselect_mine};
    private int[] mIconSelectIds = {
            R.mipmap.ic_select_home, R.mipmap.ic_select_restaurant,
            R.mipmap.ic_select_exercise, R.mipmap.ic_select_mine};

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        ActivityUtil.addActivity(this);

        HomeFragment homeFragment = new HomeFragment();
        RestaurantFragment restaurantFragment = new RestaurantFragment();
        ActivitiesFragment exerciseFragment = new ActivitiesFragment();
        MineFragment mineFragment = new MineFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(homeFragment);
        fragmentList.add(restaurantFragment);
        fragmentList.add(exerciseFragment);
        fragmentList.add(mineFragment);
        VpAdapter vpAdapter = new VpAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(vpAdapter);

        for (int i = 0; i < fragmentList.size(); i++) {
            mTabEntities.add(new TabEntity(getResources().getStringArray(R.array.main_activity)[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        bindTab();
    }

    private void bindTab() {
        commonTablayout.setTabData(mTabEntities);
        commonTablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                commonTablayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setCurrentItem(0);
    }




    @Override
    public void onSuccess(Response response) {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    public void setCurPos(int pos) {
        viewPager.setCurrentItem(pos);
    }

    private long exitTime;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - exitTime < 2000) {
            super.onBackPressed();
        } else {
            exitTime = System.currentTimeMillis();
            Toast.makeText(this, getString(R.string.exit_prompt), Toast.LENGTH_SHORT).show();
        }
    }
}