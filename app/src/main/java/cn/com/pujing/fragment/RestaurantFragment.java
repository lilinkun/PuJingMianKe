package cn.com.pujing.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.SimpleImmersionFragment;

import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.activity.RestBanquetsActivity;
import cn.com.pujing.activity.RestRoutineActivity;
import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.view.RestBanquetsView;

public class RestaurantFragment extends BaseFragment {


    @Override
    public int getlayoutId() {
        return R.layout.fragment_restaurant;
    }

    @Override
    public void initEventAndData() {

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

    @OnClick({R.id.rl_rest_routine,R.id.rl_rest_banquets})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_rest_routine:

                Intent intent = new Intent(getActivity(), RestRoutineActivity.class);
                startActivity(intent);

                break;

            case R.id.rl_rest_banquets:

                Intent intent1 = new Intent(getActivity(), RestBanquetsActivity.class);
                startActivity(intent1);

                break;
        }
    }

}
