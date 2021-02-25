package cn.com.pujing.fragment;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import cn.com.pujing.R;
import cn.com.pujing.adapter.PayBillAdapter;
import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.PayBillItem;

public class PayBillFragment extends BaseFragment {
    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_pay_bill;
    }

    @Override
    public void initEventAndData() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        PayBillAdapter payBillAdapter = new PayBillAdapter(R.layout.item_pay_bill, PayBillItem.getTestData());
        recyclerView.setAdapter(payBillAdapter);
    }

}
