package cn.com.pujing.activity;

import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.BillHistoryAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.HistoryBillsBean;
import cn.com.pujing.presenter.BillHistoryPresenter;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.BillHistoryView;

/**
 * author : liguo
 * date : 2021/4/12 16:07
 * description :
 */
public class BillHistoryActivity extends BaseActivity<BillHistoryView, BillHistoryPresenter> implements BillHistoryView {

    @BindView(R.id.rv_bills_history)
    RecyclerView rvBillsHistory;

    private BillHistoryAdapter billHistoryAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bill_history;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();
        mPresenter.getBillData();

        billHistoryAdapter = new BillHistoryAdapter(R.layout.adapter_bill_history,null);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rvBillsHistory.setLayoutManager(linearLayoutManager);
        rvBillsHistory.setAdapter(billHistoryAdapter);

    }

    @Override
    protected BillHistoryPresenter createPresenter() {
        return new BillHistoryPresenter();
    }

    @Override
    public void getDataSuccess(List<HistoryBillsBean> historyBillsBeans) {
        billHistoryAdapter.setNewInstance(historyBillsBeans);
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(this,msg);
    }

    @OnClick({R.id.iv_history_bill_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_history_bill_back:

                finish();

                break;

            default:

                break;
        }
    }
}
