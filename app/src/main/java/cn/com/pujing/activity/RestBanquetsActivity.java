package cn.com.pujing.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gyf.immersionbar.ImmersionBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.RestSortAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.callback.CheckListener;
import cn.com.pujing.entity.BanquetBean;
import cn.com.pujing.entity.ChangeDataBean;
import cn.com.pujing.fragment.RestSortDetailFragment;
import cn.com.pujing.presenter.RestBanquetsPresenter;
import cn.com.pujing.util.ActivityUtil;
import cn.com.pujing.util.PuJingUtils;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.RestBanquetsView;
import cn.com.pujing.widget.ItemHeaderDecoration;

/**
 * author : liguo
 * date : 2021/3/1 18:46
 * description :
 */
public class RestBanquetsActivity extends BaseActivity<RestBanquetsView, RestBanquetsPresenter> implements RestBanquetsView, CheckListener {

    @BindView(R.id.tv_banquets_tip)
    TextView tvBanquetsTip;
    @BindView(R.id.iv_banquets_back)
    ImageView ivBanquetsBack;
    @BindView(R.id.rv_sort_title)
    RecyclerView rvSortTitle;
    @BindView(R.id.tv_total_choose)
    TextView tvTotalChoose;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_rest_reserve)
    TextView tvRestReserve;
    @BindView(R.id.tv_rest_title)
    TextView tvRestTitle;

    private RestSortAdapter restSortAdapter;
    private RestSortDetailFragment mSortDetailFragment;
    private BanquetBean banquetBean;
    private LinearLayoutManager mLinearLayoutManager;
    private int targetPosition;//点击左边某一个具体的item的位置
    private boolean isMoved;
    private int type;
    private ChangeDataBean changeDataBean;
    private boolean isAdd = false;
    private String orderNumber;

    @Override
    public int getLayoutId() {
        return R.layout.activity_rest_banquets;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        ActivityUtil.addHomeActivity(this);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,2);

        String limitDate = simpleDateFormat.format(calendar.getTime());

        if (limitDate.startsWith("0")){
            limitDate = limitDate.substring(1,limitDate.length());
        }

        tvBanquetsTip.setText(getString(R.string.current_order_tip)+ limitDate +getString(R.string.after_order));

        restSortAdapter = new RestSortAdapter(R.layout.rest_item_sort_list,this,null);
        mLinearLayoutManager = new LinearLayoutManager(this);
        rvSortTitle.setLayoutManager(mLinearLayoutManager);
        /*DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvSortTitle.addItemDecoration(decoration);*/

        restSortAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (mSortDetailFragment != null) {
                    isMoved = true;
                    targetPosition = position;
                    setChecked(position, true);
                }
            }
        });

        rvSortTitle.setAdapter(restSortAdapter);

        isAdd = getIntent().getBooleanExtra("add",false);
        type = getIntent().getIntExtra("type",0);
        mPresenter.getBanquetsData(type);
        if (type == 3){
            tvBanquetsTip.setVisibility(View.GONE);
            tvRestTitle.setText(R.string.rest_order_meal);
        }else {
            tvRestTitle.setText(R.string.rest_banquets);
        }

        if (isAdd){
            orderNumber = getIntent().getStringExtra("orderNumber");

        }

    }

    @Override
    protected RestBanquetsPresenter createPresenter() {
        return new RestBanquetsPresenter();
    }

    public void createFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mSortDetailFragment = new RestSortDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("right", banquetBean);
        bundle.putInt("rest_type",type);
        mSortDetailFragment.setArguments(bundle);
        mSortDetailFragment.setListener(this);
        fragmentTransaction.add(R.id.lin_fragment, mSortDetailFragment);
        fragmentTransaction.commit();

        mPresenter.queryShoppingCart(1,type);
    }

    @OnClick({R.id.iv_banquets_back,R.id.tv_rest_reserve})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_banquets_back:

                finish();

                break;

            case R.id.tv_rest_reserve:

                if (isAdd){
                    mPresenter.clearMyShoppingCart(type);
                    mPresenter.addRest(changeDataBean,orderNumber);

                }else {

                    if (changeDataBean != null && changeDataBean.totalQuantity > 0) {

                        Intent intent = new Intent(this, RestBanquetsReserveActivity.class);
                        intent.putExtra("type", type);
                        startActivity(intent);
                    } else {
                        UToast.show(this, R.string.rest_choose);
                    }
                }

                break;
            default:
                break;
        }
    }

    @Override
    public void getBanquetSuccess(BanquetBean banquetBeanList) {
        this.banquetBean = banquetBeanList;
        createFragment();
    }

    @Override
    public void getDataFail(String msg) {

        if (msg.contains("sorry")){
            msg = msg.substring(5,msg.length());
            finish();
        }

        UToast.show(this,msg);

    }

    @Override
    public void onAddSuccess(ChangeDataBean changeDataBean) {
        tvTotalChoose.setText(getString(R.string.choosed) + changeDataBean.totalQuantity + getString(R.string.item));
        tvTotalPrice.setText("￥" + PuJingUtils.removeAmtLastZero(changeDataBean.totalAmount));
        mSortDetailFragment.setNum(changeDataBean,2);
        this.changeDataBean = changeDataBean;

        if (changeDataBean.categoryCountList != null && changeDataBean.categoryCountList.size() > 0){
            for (int i = 0; i < banquetBean.getCategoryList().size(); i++) {
                banquetBean.getCategoryList().get(i).setQuantity(0);
                for (int j = 0; j < changeDataBean.categoryCountList.size(); j++) {
                    if (banquetBean.getCategoryList().get(i).getGroupName().equals(changeDataBean.categoryCountList.get(j).name)){
                        banquetBean.getCategoryList().get(i).setQuantity(changeDataBean.categoryCountList.get(j).quantity);
                    }
                }
            }
        }else {

            for (int i = 0; i < banquetBean.getCategoryList().size(); i++) {
                banquetBean.getCategoryList().get(i).setQuantity(0);
            }
        }
        restSortAdapter.notifyDataSetChanged();
    }

    @Override
    public void clearMyShoppingCart(ChangeDataBean changeDataBean) {

    }

    @Override
    public void queryShoppingCartSuccess(ChangeDataBean changeDataBean,int type) {
        this.changeDataBean = changeDataBean;
        tvTotalChoose.setText(getString(R.string.choosed) + changeDataBean.totalQuantity + getString(R.string.item));
        tvTotalPrice.setText("￥" + PuJingUtils.removeAmtLastZero(changeDataBean.totalAmount));
        mSortDetailFragment.setNum(changeDataBean,1);

        for (int i = 0; i < banquetBean.getCategoryList().size(); i++) {
            banquetBean.getCategoryList().get(i).setQuantity(0);
        }

        if (changeDataBean.categoryCountList != null && changeDataBean.categoryCountList.size() > 0){
            for (int i = 0; i < banquetBean.getCategoryList().size(); i++) {
                for (int j = 0; j < changeDataBean.categoryCountList.size(); j++) {
                    if (banquetBean.getCategoryList().get(i).getGroupName().equals(changeDataBean.categoryCountList.get(j).name)){
                        banquetBean.getCategoryList().get(i).setQuantity(changeDataBean.categoryCountList.get(j).quantity);
                    }
                }
            }
        }

        if (type == 1) {
            restSortAdapter.setNewInstance(banquetBean.getCategoryList());
        }else {
            restSortAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void addRestSuccess(Boolean b) {
        if (b) {
            Intent intent = new Intent(this, RanquetsOrderActivity.class);
            intent.putExtra("ordernumber", orderNumber);
            intent.putExtra("type", type);
            startActivity(intent);
        }
    }

    @Override
    public void check(int position, boolean isScroll) {
        setChecked(position, isScroll);
    }

    @Override
    public void onAdd(int menuItemId, int quantity) {
        mPresenter.restDataChange(menuItemId,quantity,type);
    }

    @Override
    public void query() {
        mPresenter.queryShoppingCart(2,type);
    }


    private void setChecked(int position, boolean isLeft) {
        if (isLeft) {
            restSortAdapter.setCheckedPosition(position);
            //此处的位置需要根据每个分类的集合来进行计算
            int count = 0;
            for (int i = 0; i < position; i++) {
                count += banquetBean.getCategoryList().get(i).categorys.size();
            }
            count += position;
            mSortDetailFragment.setData(count);
            ItemHeaderDecoration.setCurrentTag(String.valueOf(targetPosition));//凡是点击左边，将左边点击的位置作为当前的tag
        } else {
            if (isMoved) {
                isMoved = false;
            } else {
                restSortAdapter.setCheckedPosition(position);
            }
            ItemHeaderDecoration.setCurrentTag(String.valueOf(position));//如果是滑动右边联动左边，则按照右边传过来的位置作为tag

        }
        moveToCenter(position);

    }

    //将当前选中的item居中
    private void moveToCenter(int position) {
        //将点击的position转换为当前屏幕上可见的item的位置以便于计算距离顶部的高度，从而进行移动居中
        View childAt = rvSortTitle.getChildAt(position - mLinearLayoutManager.findFirstVisibleItemPosition());
        if (childAt != null) {
            int y = (childAt.getTop() - rvSortTitle.getHeight() / 2);
            rvSortTitle.smoothScrollBy(0, y);
        }

    }

}
