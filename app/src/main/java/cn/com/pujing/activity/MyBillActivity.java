package cn.com.pujing.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.model.Response;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.BillsAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.BillsBean;
import cn.com.pujing.entity.BillsItemBean;
import cn.com.pujing.entity.MyBillBean;
import cn.com.pujing.entity.PagesBean;
import cn.com.pujing.presenter.MyBillPresenter;
import cn.com.pujing.util.PuJingUtils;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.MyBillView;

/**
 * 我的账单
 */
public class MyBillActivity extends BaseActivity<MyBillView, MyBillPresenter> implements MyBillView {

    @BindView(R.id.tv_last_month)
    TextView tvLastMonth;
    @BindView(R.id.tv_current_month)
    TextView tvCurrentMonth;
    @BindView(R.id.tv_arrearage)
    TextView tvArrearage;
    @BindView(R.id.tv_arrearage_tip)
    TextView tvArrearageTip;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_total_tip)
    TextView tvTotalTip;
    @BindView(R.id.rv_bills)
    RecyclerView rvBills;

    List<BillsBean> billsBeans;
    List<MyBillBean> myBillBeans;
    private BillsAdapter billsAdapter;
    private int page = 1;
    private int currendId = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_bill;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        mPresenter.getMyCurrentBills();

        billsAdapter = new BillsAdapter(R.layout.adapter_bills,null);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rvBills.setLayoutManager(linearLayoutManager);
        rvBills.setAdapter(billsAdapter);

        billsAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;

                mPresenter.getMyBills(billsBeans.get(currendId).id+"",page);
            }
        });

    }


    @Override
    protected MyBillPresenter createPresenter() {
        return new MyBillPresenter();
    }

    @Override
    public void getMyBillsSuccess(PagesBean<MyBillBean> beanPagesBean) {

        if (myBillBeans == null){
            myBillBeans = beanPagesBean.records;
        }else {
            if (page > 1){
                myBillBeans.addAll(beanPagesBean.records);
            }else {
                myBillBeans = beanPagesBean.records;
            }
        }

        billsAdapter.setNewInstance(myBillBeans);

        if (myBillBeans.size() == beanPagesBean.total) {
            billsAdapter.getLoadMoreModule().loadMoreEnd();
        }else {
            billsAdapter.getLoadMoreModule().loadMoreComplete();
        }

    }

    @Override
    public void getMyCurrentBillsSuccess(List<BillsBean> billsBeans) {
        this.billsBeans = billsBeans;

        tvLastMonth.setText("当期账单\n" + billsBeans.get(0).billMonth);

        tvCurrentMonth.setText("未出账单\n" + billsBeans.get(1).billMonth);

        changePage(0);
    }

    @Override
    public void getDataFail(String msg) {

        if (msg.contains("sorry")){
            msg = msg.substring(5,msg.length());
            UToast.show(this,msg);
            finish();
        }else {
            UToast.show(this, msg);
        }
    }


    public void changePage(int currendId){
        this.currendId = currendId;
        page = 1;
        if (currendId == 0) {
            if (billsBeans.get(0).payStatus == 2) {
                tvArrearage.setText("本月已结清");
                tvArrearageTip.setText("本月无需再还");
            } else {
                tvArrearage.setText("￥" + PuJingUtils.removeAmtLastZero(billsBeans.get(0).arrearage));
                tvArrearageTip.setText("本月还需再还");
            }
            tvTotalPrice.setText("￥" + PuJingUtils.removeAmtLastZero(billsBeans.get(0).totalAmount));
            tvTotalTip.setText("当期账单金额 ");

            mPresenter.getMyBills(billsBeans.get(currendId).id+"",page);
        }else {

            tvArrearage.setText("￥" + PuJingUtils.removeAmtLastZero(billsBeans.get(1).arrearage));

            tvArrearageTip.setText("待付账单金额");

            tvTotalPrice.setText("￥" + PuJingUtils.removeAmtLastZero(billsBeans.get(1).totalAmount));

            tvTotalTip.setText("未出账单金额 ");
            mPresenter.getMyBills(billsBeans.get(currendId).id+"",page);
        }

    }

    @OnClick({R.id.tv_current_month,R.id.tv_last_month,R.id.iv_back,R.id.rl_history})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_current_month:

                tvCurrentMonth.setTextColor(getResources().getColor(R.color.main_color));
                tvCurrentMonth.setBackground(getResources().getDrawable(R.drawable.bg_venue_line));

                tvLastMonth.setTextColor(getResources().getColor(R.color.gray_999));
                tvLastMonth.setBackground(getResources().getDrawable(R.drawable.shape_health_frame));

                changePage(1);

                break;

            case R.id.tv_last_month:

                tvLastMonth.setTextColor(getResources().getColor(R.color.main_color));
                tvLastMonth.setBackground(getResources().getDrawable(R.drawable.bg_venue_line));

                tvCurrentMonth.setTextColor(getResources().getColor(R.color.gray_999));
                tvCurrentMonth.setBackground(getResources().getDrawable(R.drawable.shape_health_frame));

                changePage(0);

                break;

            case R.id.iv_back:

                finish();

                break;

            case R.id.rl_history:

                Intent intent = new Intent(this,BillHistoryActivity.class);
                startActivity(intent);

                break;

            default:
                break;
        }
    }

}
