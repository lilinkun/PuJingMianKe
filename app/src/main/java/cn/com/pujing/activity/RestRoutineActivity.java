package cn.com.pujing.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.RestDayAdapter;
import cn.com.pujing.adapter.RestRoutineAdapter;
import cn.com.pujing.adapter.RestTypeAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.RestDayBean;
import cn.com.pujing.entity.RestTypeBean;
import cn.com.pujing.entity.SetMealBean;
import cn.com.pujing.presenter.RestRoutinePresenter;
import cn.com.pujing.util.ActivityUtil;
import cn.com.pujing.util.PuJingUtils;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.RestRoutineView;

/**
 * author : liguo
 * date : 2021/3/1 18:02
 * description :
 */
public class RestRoutineActivity extends BaseActivity<RestRoutineView, RestRoutinePresenter> implements RestRoutineView {

    @BindView(R.id.rv_restroutine)
    RecyclerView rvRestroutine;
    @BindView(R.id.tv_next_day)
    TextView tvNextDay;
    @BindView(R.id.rv_rest_type)
    RecyclerView rvRestType;
    @BindView(R.id.rv_rest_day)
    RecyclerView rvRestDay;

    private List<SetMealBean> setMealBeans;
    List<RestTypeBean> restTypeBeans;
    private int currentDateItem = 0;
    private int type = 1; //1早餐2早午茶3中餐4下午茶5晚餐
    private List<RestDayBean> nextWeek;
    private RestRoutineAdapter restRoutineAdapter;
    private RestTypeAdapter restTypeAdapter;
    private RestDayAdapter restDayAdapter;
    private SetMealBean setMealBean_breakfast; //早餐
    private SetMealBean setMealBean_lunch;      //中餐
    private SetMealBean setMealBean_dinner;     //晚餐
    private SetMealBean setMealBean_brunch;     //早午茶
    private SetMealBean setMealBean_afternoon_tea;     //下午茶
    private String currentDay;

    View[] ovalViews ;

    @Override
    public int getLayoutId() {
        return R.layout.activity_restroutine;
    }

    @Override
    public void init() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        ActivityUtil.addHomeActivity(this);

        nextWeek = PuJingUtils.getNextWeekDateList();

        currentDay = nextWeek.get(0).dateDay;


        LinearLayoutManager dayLinearLayoutManager = new LinearLayoutManager(this);
        dayLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        restDayAdapter = new RestDayAdapter(R.layout.adapter_rest_day,nextWeek,this);
        rvRestDay.setLayoutManager(dayLinearLayoutManager);
        rvRestDay.setAdapter(restDayAdapter);
        restDayAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                restDayAdapter.setPositionView(position);
                currentDateItem = position;
                currentDay = nextWeek.get(currentDateItem).dateDay;
                mPresenter.getSetMealData(nextWeek.get(currentDateItem).dateDay,Integer.valueOf(restTypeBeans.get(type).value));
            }
        });


        LinearLayoutManager typeLinearLayoutManager = new LinearLayoutManager(this);
        typeLinearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        restTypeAdapter = new RestTypeAdapter(R.layout.adapter_resttype,this,null);

        rvRestType.setLayoutManager(typeLinearLayoutManager);
        rvRestType.setAdapter(restTypeAdapter);

        restTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                restTypeAdapter.setPositionView(position);
                type = position;
                mPresenter.getSetMealData(nextWeek.get(currentDateItem).dateDay,Integer.valueOf(restTypeBeans.get(type).value));
            }
        });

        mPresenter.getSetMealTypeData();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        restRoutineAdapter = new RestRoutineAdapter(R.layout.adapter_restroutine,null,this);

        rvRestroutine.setLayoutManager(linearLayoutManager);
        rvRestroutine.setAdapter(restRoutineAdapter);

        restRoutineAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                for (int i = 0; i < setMealBeans.size();i++){
                    if (i == position){
                        setMealBeans.get(i).setShow(true);

                        mPresenter.saveSetMeal(setMealBeans.get(position),currentDay,Integer.valueOf(restTypeBeans.get(type).value));

                    }else {
                        setMealBeans.get(i).setShow(false);
                    }
                }
                restRoutineAdapter.notifyDataSetChanged();

                if (type == 1){
                    setMealBean_breakfast = setMealBeans.get(position);
                }else if (type == 2){
                    setMealBean_brunch = setMealBeans.get(position);
                }else if (type == 3){
                    setMealBean_lunch = setMealBeans.get(position);
                }else if (type == 4){
                    setMealBean_afternoon_tea = setMealBeans.get(position);
                }else if (type == 5){
                    setMealBean_dinner = setMealBeans.get(position);
                }
            }
        });
    }

    @Override
    protected RestRoutinePresenter createPresenter() {
        return new RestRoutinePresenter();
    }


    @OnClick({R.id.iv_setmeal_back,R.id.tv_next_day})
    public void onClick(View view){
        switch (view.getId()){


            case R.id.iv_setmeal_back:

                finish();

                break;

            case R.id.tv_next_day:

                if (tvNextDay.equals(getString(R.string.next_day))){



                }

                break;
        }
    }

    @Override
    public void getSetMealSuccess(List<SetMealBean> setMealBeans) {
        this.setMealBeans = setMealBeans;

        if (type == 1){
            if (setMealBean_breakfast != null) {
                selectSetMeal(setMealBean_breakfast);
            }
        }else if (type == 2){
            if (setMealBean_lunch != null) {
                selectSetMeal(setMealBean_lunch);
            }
        }else if (type == 3){
            if (setMealBean_dinner != null) {
                selectSetMeal(setMealBean_dinner);
            }
        }
        restRoutineAdapter.setNewInstance(setMealBeans);
    }

    @Override
    public void getSetMealTypeSuccess(List<RestTypeBean> restTypeBeans) {
        if (restTypeAdapter != null){
            this.restTypeBeans = restTypeBeans;
            restTypeAdapter.setNewInstance(restTypeBeans);

            mPresenter.getSetMealData(nextWeek.get(currentDateItem).dateDay,Integer.valueOf(restTypeBeans.get(0).value));

            //获取第一个位置的view，并点击
//            rvRestType.getLayoutManager().findViewByPosition(0).performClick();
        }
    }

    private void selectSetMeal(SetMealBean setMealBean){

        for (int i = 0;i<setMealBeans.size();i++){
            if (setMealBeans.get(i).getId() == setMealBean.getId()){
                setMealBeans.get(i).setShow(true);
//                ovalViews[type-1].setVisibility(View.VISIBLE);
            }else {
                setMealBeans.get(i).setShow(false);
            }
        }
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(this,msg);
    }
}
