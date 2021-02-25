package cn.com.pujing.fragment;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import butterknife.BindView;
import cn.com.pujing.R;
import cn.com.pujing.activity.BillInfoActivity;
import cn.com.pujing.adapter.PaidBillAdapter;
import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.PaidBillItem;

public class PaidBillFragment extends BaseFragment {
    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_paied_bill;
    }

    @Override
    public void initEventAndData() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        PaidBillAdapter paidBillAdapter = new PaidBillAdapter(R.layout.item_paid_bill, PaidBillItem.getTestData());
        recyclerView.setAdapter(paidBillAdapter);
        paidBillAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                startActivity(new Intent(getContext(), BillInfoActivity.class));
            }
        });
    }

}
