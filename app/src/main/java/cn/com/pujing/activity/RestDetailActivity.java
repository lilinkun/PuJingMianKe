package cn.com.pujing.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gyf.immersionbar.ImmersionBar;

import java.lang.annotation.ElementType;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.ChangeDataBean;
import cn.com.pujing.entity.RestDetailBean;
import cn.com.pujing.entity.RestSortDetailBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.presenter.RestDetailPresenter;
import cn.com.pujing.util.Eyes;
import cn.com.pujing.util.PuJingUtils;
import cn.com.pujing.util.UToast;
import cn.com.pujing.util.Urls;
import cn.com.pujing.view.RestDetailView;

/**
 * author : liguo
 * date : 2021/3/9 17:09
 * description :
 */
public class RestDetailActivity extends BaseActivity<RestDetailView, RestDetailPresenter> implements RestDetailView {

    @BindView(R.id.iv_rest_detail)
    ImageView ivRestDetail;
    @BindView(R.id.iv_goods_add)
    ImageView ivGoodsAdd;
    @BindView(R.id.iv_goods_reduce)
    ImageView ivGoodsReduce;
    @BindView(R.id.tv_goods_num)
    TextView tvGoodsNum;
    @BindView(R.id.tv_rest_price)
    TextView tvRestprice;
    @BindView(R.id.tv_rest_main_food_value)
    TextView tvRestMainFoodValue;
    @BindView(R.id.tv_rest_nutrient_elements_value)
    TextView tvRestNutrientElementsValue;
    @BindView(R.id.tv_rest_taboo_value)
    TextView tvRestTabooValue;
    @BindView(R.id.tv_rest_name)
    TextView tvRestName;

    private int num = 0;
    private int id;
    private int type;
    private RestSortDetailBean restSortDetailBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_rest_detail;
    }

    @Override
    public void initView() {

        Eyes.translucentStatusBar(this, false);

        restSortDetailBean = (RestSortDetailBean)(getIntent().getSerializableExtra("restSortDetailBean"));
        num = restSortDetailBean.getGoodsNum();
        id = restSortDetailBean.getFoodId();
        type = getIntent().getIntExtra("type",0);

        if (num > 0){
            tvGoodsNum.setText(num+"");
            tvGoodsNum.setVisibility(View.VISIBLE);
            ivGoodsReduce.setVisibility(View.VISIBLE);
        }

        mPresenter.getRestDetail(id);
    }

    @Override
    protected RestDetailPresenter createPresenter() {
        return new RestDetailPresenter();
    }

    @Override
    public void getRestDetail(RestDetailBean restDetailBean) {

        Glide.with(this)
                .load(PujingService.PREFIX + Urls.IMG + restDetailBean.coverPic)
                .error(R.drawable.ic_no_pic)
                .into(ivRestDetail);

        if (type == 2) {
            tvRestprice.setText("￥ " + PuJingUtils.removeAmtLastZero(restDetailBean.banquetPrice));
        }else {
            tvRestprice.setText("￥ " + PuJingUtils.removeAmtLastZero(restDetailBean.sporadicPrice));
        }
        tvRestMainFoodValue.setText(restDetailBean.materiel);
        tvRestNutrientElementsValue.setText(restDetailBean.nutrientElement);
        tvRestTabooValue.setText(restDetailBean.disableLabel);
        tvRestName.setText(restDetailBean.foodCategoryName);
    }


    @OnClick({R.id.iv_goods_add,R.id.iv_goods_reduce,R.id.iv_rest_back})
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

            case R.id.iv_rest_back:

                setResult(RESULT_OK);
                finish();

                break;
        }
    }


    @Override
    public void onChangeSuccess(ChangeDataBean changeDataBean) {
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}
