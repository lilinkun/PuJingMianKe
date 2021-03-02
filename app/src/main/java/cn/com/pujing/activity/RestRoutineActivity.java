package cn.com.pujing.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.RestRoutineAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.SetMealBean;
import cn.com.pujing.presenter.RestRoutinePresenter;
import cn.com.pujing.util.PuJingUtils;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.RestRoutineView;

/**
 * author : liguo
 * date : 2021/3/1 18:02
 * description :
 */
public class RestRoutineActivity extends BaseActivity<RestRoutineView, RestRoutinePresenter> implements RestRoutineView {

    @BindView(R.id.rl_rest_breakfast)
    RelativeLayout rlRestBreakfast;
    @BindView(R.id.rl_rest_lunch)
    RelativeLayout rlRestLunch;
    @BindView(R.id.rl_rest_dinner)
    RelativeLayout rlRestDinner;
    @BindView(R.id.rl_rest_soup)
    RelativeLayout rlRestSoup;
    @BindView(R.id.view_rest_breakfast)
    View vRestBreakfast;
    @BindView(R.id.view_rest_lunch)
    View vRestLunch;
    @BindView(R.id.view_rest_dinner)
    View vRestDinner;
    @BindView(R.id.view_rest_soup)
    View vRestSoup;
    @BindView(R.id.tv_restroutine_date)
    TextView tvRestroutineDate;
    @BindView(R.id.iv_restroutine_left)
    ImageView ivRestroutineLeft;
    @BindView(R.id.iv_restroutine_right)
    ImageView ivRestroutineRight;
    @BindView(R.id.rv_restroutine)
    RecyclerView rvRestroutine;

    private SetMealBean setMealBean;
    private int currentDateItem = 0;
    private int type = 1; //1早餐2早午茶3中餐4下午茶5晚餐
    private List<String> nextWeek;
    private RestRoutineAdapter restRoutineAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_restroutine;
    }

    @Override
    public void init() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        clickItem(0);
        nextWeek = PuJingUtils.getNextWeekDateList();

        tvRestroutineDate.setText(nextWeek.get(currentDateItem));

        mPresenter.getSetMealData(nextWeek.get(currentDateItem),type);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        restRoutineAdapter = new RestRoutineAdapter(R.layout.adapter_restroutine,null,this);

        rvRestroutine.setLayoutManager(linearLayoutManager);
        rvRestroutine.setAdapter(restRoutineAdapter);
    }

    @Override
    protected RestRoutinePresenter createPresenter() {
        return new RestRoutinePresenter();
    }


    @OnClick({R.id.rl_rest_breakfast,R.id.rl_rest_lunch,R.id.rl_rest_dinner,R.id.rl_rest_soup,R.id.iv_restroutine_left,R.id.iv_restroutine_right
    ,R.id.iv_setmeal_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_rest_breakfast:

                clickItem(0);

                break;

            case R.id.rl_rest_lunch:

                clickItem(1);

                break;

            case R.id.rl_rest_dinner:

                clickItem(2);

                break;

            case R.id.rl_rest_soup:

                clickItem(3);

                break;

            case R.id.iv_restroutine_left:

                if (currentDateItem > 0){
                    currentDateItem--;
                    tvRestroutineDate.setText(nextWeek.get(currentDateItem));
                }

                break;

            case R.id.iv_restroutine_right:

                if (currentDateItem < 6){
                    currentDateItem++;
                    tvRestroutineDate.setText(nextWeek.get(currentDateItem));
                }

                break;

            case R.id.iv_setmeal_back:

                finish();

                break;
        }
    }

    private void clickItem(int clickItem){


        RelativeLayout[] relativeLayouts = {rlRestBreakfast,rlRestLunch,rlRestDinner,rlRestSoup};
        View[] views = {vRestBreakfast,vRestLunch,vRestDinner,vRestSoup};

        for (int i = 0; i <relativeLayouts.length;i++){
            if (i == clickItem) {
                relativeLayouts[i].setSelected(true);
                views[i].setVisibility(View.VISIBLE);
            }else {
                relativeLayouts[i].setSelected(false);
                views[i].setVisibility(View.GONE);
            }
        }


    }

    @Override
    public void getSetMealSuccess(List<SetMealBean> setMealBean) {
//        this.setMealBean = setMealBean;
        restRoutineAdapter.setNewInstance(setMealBean);
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(this,msg);
    }
}
