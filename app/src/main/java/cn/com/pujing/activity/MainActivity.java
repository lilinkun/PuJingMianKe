package cn.com.pujing.activity;

import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.pujing.R;
import cn.com.pujing.adapter.VpAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.fragment.ExerciseFragment;
import cn.com.pujing.fragment.HomeFragment;
import cn.com.pujing.fragment.MineFragment;
import cn.com.pujing.fragment.RestaurantFragment;

public class MainActivity extends BaseActivity {

    @BindView(R.id.vp)
    ViewPager viewPager;
    @BindView(R.id.bnv)
    BottomNavigationViewEx bottomNavigationViewEx;

    private List<Fragment> fragmentList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {

        HomeFragment homeFragment = new HomeFragment();
        RestaurantFragment restaurantFragment = new RestaurantFragment();
        ExerciseFragment exerciseFragment = new ExerciseFragment();
        MineFragment mineFragment = new MineFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(homeFragment);
        fragmentList.add(restaurantFragment);
        fragmentList.add(exerciseFragment);
        fragmentList.add(mineFragment);
        VpAdapter vpAdapter = new VpAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(vpAdapter);

        bottomNavigationViewEx.enableShiftingMode(false);

        bottomNavigationViewEx.setupWithViewPager(viewPager, false);
    }

    @Override
    public void onSuccess(Response response) {

    }

    public void setCurPos(int pos) {
        bottomNavigationViewEx.setCurrentItem(pos);
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