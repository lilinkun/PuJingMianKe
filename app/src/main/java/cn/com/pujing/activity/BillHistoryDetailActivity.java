package cn.com.pujing.activity;

import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import cn.com.pujing.R;
import cn.com.pujing.adapter.BillsAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.HistoryBillsBean;
import cn.com.pujing.entity.MyBillBean;
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

    @Override
    public int getLayoutId() {
        return R.layout.activity_bill_history_detail;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        HistoryBillsBean.BillList billList = (HistoryBillsBean.BillList)getIntent().getSerializableExtra("bill");

        tvBillHistoryName.setText(billList.billMonth + "月账单");

        mPresenter.getMyBills(billList.id+"");


        billsAdapter = new BillsAdapter(R.layout.adapter_bills,null);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rvHistoryBills.setLayoutManager(linearLayoutManager);
        rvHistoryBills.setAdapter(billsAdapter);

        tv_price.setText("￥" + PuJingUtils.removeAmtLastZero(billList.totalAmount));

    }

    @Override
    protected BillHistoryDetailPresenter createPresenter() {
        return new BillHistoryDetailPresenter();
    }

    @Override
    public void getMyBillsSuccess(MyBillBean billsItemBean) {

        billsAdapter.setNewInstance(billsItemBean.records);


    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(this,msg);
    }
}
