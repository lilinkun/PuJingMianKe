package cn.com.pujing.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
import cn.com.pujing.entity.BannerBean;
import cn.com.pujing.entity.TabEntity;
import cn.com.pujing.entity.UpdateBean;
import cn.com.pujing.fragment.HomeFragment;
import cn.com.pujing.fragment.MessageFragment;
import cn.com.pujing.fragment.MineFragment;
import cn.com.pujing.fragment.RestaurantFragment;
import cn.com.pujing.presenter.MainPresenter;
import cn.com.pujing.service.UpdateService;
import cn.com.pujing.util.ActivityUtil;
import cn.com.pujing.util.PuJingUtils;
import cn.com.pujing.view.MainView;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView {

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
    public void initView() {

        registerReceiver(reciver, new IntentFilter("com.jiguang.demo.message"));

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        ActivityUtil.addActivity(this);

        mPresenter.sendPushDevice(JPushInterface.getRegistrationID(this));

        HomeFragment homeFragment = new HomeFragment();
        RestaurantFragment restaurantFragment = new RestaurantFragment();
        MessageFragment messageFragment = new MessageFragment();
        MineFragment mineFragment = new MineFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(homeFragment);
        fragmentList.add(restaurantFragment);
        fragmentList.add(messageFragment);
        fragmentList.add(mineFragment);
        VpAdapter vpAdapter = new VpAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(vpAdapter);

        for (int i = 0; i < fragmentList.size(); i++) {
            mTabEntities.add(new TabEntity(getResources().getStringArray(R.array.main_activity)[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        bindTab();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            if (bundle.getSerializable("push") != null){

                BannerBean bannerBean = (BannerBean) bundle.getSerializable("push");

                PuJingUtils.bannerClick(this,bannerBean);
            }
        }

        mPresenter.checkUpdate();

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
    protected MainPresenter createPresenter() {
        return new MainPresenter();
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

    private final BroadcastReceiver reciver =  new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View dv = View.inflate(context, R.layout.d_dialog_msg, null);
            TextView tvOk = (TextView) dv.findViewById(R.id.btn_ok);
            TextView msg = (TextView) dv.findViewById(R.id.msg);
            msg.setText(intent.getStringExtra("msg"));
            final Dialog dialog = builder.create();
            dialog.show();
            dialog.getWindow().setContentView(dv);
            tvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });*/
        }
    };

    @Override
    public void sendPushDeviceSuccess(Object o) {

    }

    @Override
    public void sendPushDeviceFail(String msg) {

    }

    @Override
    public void getUpdateDataSuccess(UpdateBean updateBean) {
        if (updateBean.buildVersion > Double.valueOf(PuJingUtils.getVersion(this))){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this).setMessage("请升级更新app").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    /*mApkUrl = bean1.getInstall_url();
                    deleteApkFile();
                    downloadApkFile(dialog);*/

                    UpdateService.startAction(MainActivity.this,updateBean.downloadURL,updateBean.buildName);

                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
//            builder.create().setCanceledOnTouchOutside(false);
            //  builder.setCancelable(false);
            /*builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });*/
            builder.show();
        }
    }

    @Override
    public void getDataFail(String msg) {

    }
}