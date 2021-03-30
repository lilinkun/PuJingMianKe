package cn.com.pujing.activity;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.MealPicAdapter;
import cn.com.pujing.adapter.SetMealAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.ChangeDataBean;
import cn.com.pujing.entity.RestMealBean;
import cn.com.pujing.entity.RestSortDetailBean;
import cn.com.pujing.presenter.SetMealDetailPresenter;
import cn.com.pujing.util.PuJingUtils;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.SetMealDetailView;

/**
 * author : liguo
 * date : 2021/3/7 15:27
 * description :
 */
public class SetMealDetailActivity extends BaseActivity<SetMealDetailView, SetMealDetailPresenter> implements SetMealDetailView {

    @BindView(R.id.rv_meal_pic)
    RecyclerView rvMealPic;
    @BindView(R.id.rv_meal_detail)
    RecyclerView rvMealDetail;
    @BindView(R.id.iv_setmeal_back)
    ImageView ivSetmealBack;
    @BindView(R.id.tv_meal_name)
    TextView tvMealName;
    @BindView(R.id.tv_rest_price)
    TextView tvRestPrice;
    @BindView(R.id.iv_goods_add)
    ImageView ivGoodsAdd;
    @BindView(R.id.iv_goods_reduce)
    ImageView ivGoodsReduce;
    @BindView(R.id.tv_goods_num)
    TextView tvGoodsNum;
    @BindView(R.id.tv_notice)
    TextView tvNotice;


    private RestSortDetailBean restSortDetailBean;
    private MealPicAdapter mealPicAdapter;
    private SetMealAdapter setMealAdapter;
    private RestMealBean restMealBean;
    private List<RestSortDetailBean> restSortDetailBeans = new ArrayList<>();

    private int num;
    private int type;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setmeal;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

//        int id = getIntent().getIntExtra("id",0);
        restSortDetailBean = (RestSortDetailBean)(getIntent().getSerializableExtra("restSortDetailBean"));
        int id = restSortDetailBean.getFoodId();
        type = getIntent().getIntExtra("type",0);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        mealPicAdapter = new MealPicAdapter(R.layout.adapter_meal_pic,null,this);

        rvMealPic.setLayoutManager(linearLayoutManager);
        rvMealPic.setAdapter(mealPicAdapter);

        tvRestPrice.setText("￥" + PuJingUtils.removeAmtLastZero(restSortDetailBean.getPrice()));
        num = restSortDetailBean.getGoodsNum();
        if (num > 0){
            tvGoodsNum.setText(num+"");
            tvGoodsNum.setVisibility(View.VISIBLE);
            ivGoodsReduce.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(RecyclerView.VERTICAL);

        setMealAdapter = new SetMealAdapter(this,restSortDetailBeans);

        rvMealDetail.setLayoutManager(linearLayoutManager1);
        rvMealDetail.setAdapter(setMealAdapter);


        mPresenter.queryRestMealDetail(id);

    }

    @Override
    protected SetMealDetailPresenter createPresenter() {
        return new SetMealDetailPresenter();
    }


    @Override
    public void getSetMealDetail(RestMealBean restMealBean) {
        this.restMealBean = restMealBean;
        tvMealName.setText(restMealBean.mealName);
        tvNotice.setText(restMealBean.notice);

        ArrayList<String> strings = new ArrayList<>();
        if(restMealBean.coverPic!=null ) {
            if (restMealBean.coverPic.contains(",")) {
                String[] restMealBeans = restMealBean.coverPic.split(",");
                for (int i = 0; i < restMealBeans.length; i++) {
                    strings.add(restMealBeans[i]);
                }
            } else {
                strings.add(restMealBean.coverPic);
            }
        }

        mealPicAdapter.setNewInstance(strings);

        for (int i = 0;i<restMealBean.foodGruops.size();i++){
            RestSortDetailBean sortDetailBean = new RestSortDetailBean();
            sortDetailBean.setTitle(true);
            sortDetailBean.setTitleName(restMealBean.foodGruops.get(i).groupName);
            sortDetailBean.setName(restMealBean.foodGruops.get(i).groupName);
            restSortDetailBeans.add(sortDetailBean);
            List<RestMealBean.FoodGruops.FoodCategorys> foodCategorys = restMealBean.foodGruops.get(i).foodCategorys;
            for (int j = 0;j<foodCategorys.size();j++){

                RestSortDetailBean sortDetailBean1 = new RestSortDetailBean();
                sortDetailBean1.setTitle(false);
                sortDetailBean1.setName(foodCategorys.get(j).foodCategoryName);
                sortDetailBean1.setGoodsNum(Integer.valueOf(foodCategorys.get(j).number));
                sortDetailBean1.setCalculateUnit(foodCategorys.get(j).calculateUnit);
                restSortDetailBeans.add(sortDetailBean1);
            }
        }

        setMealAdapter.setNewInstance(restSortDetailBeans);

    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void onChangeSuccess(ChangeDataBean changeDataBean) {

    }

    @OnClick({R.id.iv_goods_add,R.id.iv_goods_reduce,R.id.iv_setmeal_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_goods_add:
                if (num == 99){
                    UToast.show(this,"已到最大值");
                }else {
                    num++;
                    tvGoodsNum.setText(num + "");
                    if (num > 0) {
                        tvGoodsNum.setVisibility(View.VISIBLE);
                        ivGoodsReduce.setVisibility(View.VISIBLE);
                    }
                    mPresenter.restDataChange(restSortDetailBean.getmId(), num, type);
                }

                break;

            case R.id.iv_goods_reduce:

                num--;
                if (num == 0){
                    tvGoodsNum.setVisibility(View.GONE);
                    ivGoodsReduce.setVisibility(View.GONE);
                }else {
                    tvGoodsNum.setText(num+"");
                }
                mPresenter.restDataChange(restSortDetailBean.getmId(),num,type);

                break;

            case R.id.iv_setmeal_back:

                setResult(RESULT_OK);
                finish();

                break;
        }
    }


    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

}
