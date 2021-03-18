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
import cn.com.pujing.activity.MealOrderActivity;
import cn.com.pujing.activity.RestBanquetsActivity;
import cn.com.pujing.activity.RestRoutineActivity;
import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.AddRestBean;
import cn.com.pujing.entity.RoutineRecordBean;
import cn.com.pujing.presenter.RestaurantPresenter;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.RestBanquetsView;
import cn.com.pujing.view.RestaurantView;

public class RestaurantFragment extends BaseFragment<RestaurantView, RestaurantPresenter> implements RestaurantView{


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
    protected RestaurantPresenter createPresenter() {
        return new RestaurantPresenter();
    }

    @OnClick({R.id.rl_rest_routine,R.id.rl_rest_banquets,R.id.rl_rest_order})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_rest_routine:


                mPresenter.getRoutineData();

                break;

            case R.id.rl_rest_banquets:

                Intent intent1 = new Intent(getActivity(), RestBanquetsActivity.class);
                intent1.putExtra("type",2);
                intent1.putExtra("add",false);
                startActivity(intent1);

                break;

            case R.id.rl_rest_order:


                Intent intent2 = new Intent(getActivity(), RestBanquetsActivity.class);
                intent2.putExtra("type",1);
                intent2.putExtra("add",false);
                startActivity(intent2);

                break;
        }
    }

    @Override
    public void getRestData(RoutineRecordBean addRestBean) {

        if (addRestBean.restaurantCycleRecord == null){

            Intent intent = new Intent(getActivity(), RestRoutineActivity.class);
            startActivity(intent);
        }else {

            if (addRestBean.restaurantCycleRecord.status == 1) {

                Intent intent = new Intent(getActivity(), RestRoutineActivity.class);
                startActivity(intent);
            } else {

                Intent intent = new Intent(getActivity(), MealOrderActivity.class);
                intent.putExtra("status",addRestBean.restaurantCycleRecord.status);
                startActivity(intent);
            }
        }
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(getActivity(),msg);
    }
}
