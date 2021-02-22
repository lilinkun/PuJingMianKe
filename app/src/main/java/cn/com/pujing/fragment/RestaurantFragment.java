package cn.com.pujing.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.SimpleImmersionFragment;

import cn.com.pujing.R;
import cn.com.pujing.base.BaseFragment;

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
}
