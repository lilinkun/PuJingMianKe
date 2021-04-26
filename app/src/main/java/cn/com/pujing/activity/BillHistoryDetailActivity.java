package cn.com.pujing.activity;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.BillsAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.HistoryBillsBean;
import cn.com.pujing.entity.MyBillBean;
import cn.com.pujing.entity.PagesBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.presenter.BillHistoryDetailPresenter;
import cn.com.pujing.util.PuJingUtils;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.BillHistoryDetailView;

/**
 * author : liguo
 * date : 2021/4/12 18:40
 * description :
 */
public class BillHistoryDetailActivity extends BaseActivity<BillHistoryDetailView, BillHistoryDetailPresenter> implements BillHistoryDetailView{

    @BindView(R.id.tv_bill_history_name)
    TextView tvBillHistoryName;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.rv_history_bills)
    RecyclerView rvHistoryBills;

    private BillsAdapter billsAdapter;
    private int page = 1;
    private List<MyBillBean> myBillBeans;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bill_history_detail;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        HistoryBillsBean.BillList billList = (HistoryBillsBean.BillList)getIntent().getSerializableExtra("bill");

        tvBillHistoryName.setText(billList.billMonth + "月账单");

        mPresenter.getMyBills(billList.id+"",page);


        billsAdapter = new BillsAdapter(R.layout.adapter_bills,null);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rvHistoryBills.setLayoutManager(linearLayoutManager);
        rvHistoryBills.setAdapter(billsAdapter);

        tv_price.setText("￥" + PuJingUtils.removeAmtLastZero(billList.totalAmount));


        billsAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;

                mPresenter.getMyBills(billList.id+"",page);
            }
        });

    }

    @Override
    protected BillHistoryDetailPresenter createPresenter() {
        return new BillHistoryDetailPresenter();
    }

    @Override
    public void getMyBillsSuccess(PagesBean<MyBillBean> billsItemBean) {

        if (myBillBeans == null){
            myBillBeans = billsItemBean.records;
        }else {
            if (page > 1){
                myBillBeans.addAll(billsItemBean.records);
            }else {
                myBillBeans = billsItemBean.records;
            }
        }

        billsAdapter.setNewInstance(myBillBeans);

        if (myBillBeans.size() == billsItemBean.total) {
            billsAdapter.getLoadMoreModule().loadMoreEnd();
        }else {
            billsAdapter.getLoadMoreModule().loadMoreComplete();
        }
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(this,msg);
    }

    @OnClick({R.id.iv_history_bill_back})
    public void onClick(View view){
        if (view.getId() == R.id.iv_history_bill_back){
            finish();
        }
    }
}
