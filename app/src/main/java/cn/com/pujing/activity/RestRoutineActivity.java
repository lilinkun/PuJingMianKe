package cn.com.pujing.activity;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gyf.immersionbar.ImmersionBar;

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
import cn.com.pujing.entity.RoutineRecordBean;
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
    private int type = 0;
    private List<RestDayBean> nextWeek;
    private RestRoutineAdapter restRoutineAdapter;
    private RestTypeAdapter restTypeAdapter;
    private RestDayAdapter restDayAdapter;
    private RoutineRecordBean routineRecordBean;
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
                mPresenter.getRoutineData(currentDay);
//                mPresenter.getSetMealData(nextWeek.get(currentDateItem).dateDay,Integer.valueOf(restTypeBeans.get(type).value));
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
                mPresenter.getSetMealData(nextWeek.get(currentDateItem).dateDay,restTypeBeans.get(type).value);
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
                /*for (int i = 0; i < setMealBeans.size();i++){
                    if (i == position){
                        setMealBeans.get(i).setShow(true);
*/

                if (!restTypeBeans.get(type).value.contains(",")) {
                    int id = 0;

                    for (int j = 0; j < routineRecordBean.cycleMealVoList.size(); j++) {
                        if (routineRecordBean.cycleMealVoList.get(j).type == Integer.valueOf(restTypeBeans.get(type).value)) {
                            id = routineRecordBean.cycleMealVoList.get(j).id;
                        }
                    }

                    mPresenter.saveSetMeal(setMealBeans.get(position), currentDay, restTypeBeans.get(type).value, id);
                }
                    /*}else {
                        setMealBeans.get(i).setShow(false);
                    }
                }
                restRoutineAdapter.notifyDataSetChanged();*/

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

                if (currentDateItem < 6){
                    currentDateItem++;
                    restDayAdapter.setPositionView(currentDateItem);
                    currentDay = nextWeek.get(currentDateItem).dateDay;

                    mPresenter.getRoutineData(currentDay);

                    if(currentDateItem == 6){
                        tvNextDay.setText("提交");
                    }

//                    mPresenter.getSetMealData(nextWeek.get(currentDateItem).dateDay,Integer.valueOf(restTypeBeans.get(type).value));
                }else {
                    mPresenter.submitSetMeal(2);
                }

                break;
        }
    }

    @Override
    public void getSetMealSuccess(List<SetMealBean> setMealBeans) {
        int id = 0;

        if (routineRecordBean.cycleMealVoList != null) {

            for (int i = 0; i < routineRecordBean.cycleMealVoList.size(); i++) {
                if (!restTypeBeans.get(type).value.contains(",")) {
                    if (routineRecordBean.cycleMealVoList.get(i).type == Integer.valueOf(restTypeBeans.get(type).value)) {
                        id = routineRecordBean.cycleMealVoList.get(i).mealIds;
                    }
                }
            }
        }

        this.setMealBeans = setMealBeans;

        for (int j = 0;j<setMealBeans.size();j++){

            if (restTypeBeans.get(type).value.contains(",")){
                setMealBeans.get(j).setVisibel(false);
            }else {
                setMealBeans.get(j).setVisibel(true);
                if (setMealBeans.get(j).getId() == id) {
                    setMealBeans.get(j).setShow(true);
                }
            }
        }

        restRoutineAdapter.setNewInstance(setMealBeans);
    }

    @Override
    public void getSetMealTypeSuccess(List<RestTypeBean> restTypeBeans) {
        if (restTypeAdapter != null){
            this.restTypeBeans = restTypeBeans;
            restTypeAdapter.setNewInstance(restTypeBeans);


            mPresenter.getRoutineData(currentDay);

//            mPresenter.getSetMealData(nextWeek.get(currentDateItem).dateDay,Integer.valueOf(restTypeBeans.get(0).value));

            //获取第一个位置的view，并点击
//            rvRestType.getLayoutManager().findViewByPosition(0).performClick();
        }
    }

    private void selectSetMeal(SetMealBean setMealBean){

        for (int i = 0;i<setMealBeans.size();i++){
            if (setMealBeans.get(i).getId() == setMealBean.getId()){
                setMealBeans.get(i).setShow(true);
            }else {
                setMealBeans.get(i).setShow(false);
            }
        }
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void getRestClickDataFail(String msg) {
        routineRecordBean.cycleMealVoList.clear();
    }

    @Override
    public void saveDataSuccess(boolean b) {
        mPresenter.getRoutineData(currentDay);
    }

    @Override
    public void submitSuccess(boolean b) {

    }

    @Override
    public void getRestClickData(RoutineRecordBean routineRecordBean) {

        this.routineRecordBean = routineRecordBean;

        mPresenter.getSetMealData(nextWeek.get(currentDateItem).dateDay,restTypeBeans.get(type).value);
    }
}
