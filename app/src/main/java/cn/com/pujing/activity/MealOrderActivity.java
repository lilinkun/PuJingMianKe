package cn.com.pujing.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.MealOrderAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.MealOrderBean;
import cn.com.pujing.entity.RestDayBean;
import cn.com.pujing.entity.RoutineRecordBean;
import cn.com.pujing.presenter.MealOrderPresenter;
import cn.com.pujing.util.ActivityUtil;
import cn.com.pujing.util.PuJingUtils;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.MealOrderView;

/**
 * author : liguo
 * date : 2021/3/7 12:29
 * description :
 */
public class MealOrderActivity extends BaseActivity<MealOrderView, MealOrderPresenter> implements MealOrderView {

    private MealOrderAdapter mealOrderAdapter;

    @BindView(R.id.rv_preview)
    RecyclerView rvPreview;
    @BindView(R.id.tv_order_title)
    TextView tvOrderTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    private int status;

    @Override
    public int getLayoutId() {
        return R.layout.activity_seeorder;
    }

    @Override
    public void init() {
        ActivityUtil.addHomeActivity(this);

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        status = getIntent().getIntExtra("status",0);
        if (status == 1){
            tvOrderTitle.setText(R.string.order_preview);
        }else {
            tvOrderTitle.setText(R.string.order_see);
            tvSubmit.setText(R.string.edit);
            if (status == 3) {
                tvSubmit.setVisibility(View.GONE);
            }
        }

        mealOrderAdapter = new MealOrderAdapter(R.layout.adapter_meal_order_date,null,this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rvPreview.setLayoutManager(linearLayoutManager);
        rvPreview.setAdapter(mealOrderAdapter);

        mPresenter.getRoutineData();
    }

    @Override
    protected MealOrderPresenter createPresenter() {
        return new MealOrderPresenter();
    }

    @OnClick({R.id.tv_submit,R.id.iv_order_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_submit:

                if (status== 1) {
                    mPresenter.submitSetMeal();
                }else {

                    Intent intent = new Intent(this, RestRoutineActivity.class);
                    startActivity(intent);

                }

                break;

            case R.id.iv_order_back:

                finish();

                break;
        }
    }


    @Override
    public void getRestData(RoutineRecordBean routineRecordBean) {

        mealOrderAdapter.setNewInstance(routineRecordBean.cycleMeals);
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void submitSuccess(boolean b) {
        if (b){
            UToast.show(this,"提交成功");

            ActivityUtil.finishHomeAll();
        }
    }
}
