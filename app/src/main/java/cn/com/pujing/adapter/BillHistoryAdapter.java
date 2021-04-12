package cn.com.pujing.adapter;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.activity.BillHistoryDetailActivity;
import cn.com.pujing.entity.HistoryBillsBean;

/**
 * author : liguo
 * date : 2021/4/12 16:07
 * description :
 */
public class BillHistoryAdapter extends BaseQuickAdapter<HistoryBillsBean, BaseViewHolder> {

    public BillHistoryAdapter(int layoutResId, @Nullable List<HistoryBillsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HistoryBillsBean historyBillsBean) {

        baseViewHolder.setText(R.id.tv_bills_year,historyBillsBean.year+"");

        RecyclerView rvAdapterBills = baseViewHolder.getView(R.id.rv_adapter_bills);

        BillChildHistoryAdapter billChildHistoryAdapter = new BillChildHistoryAdapter(R.layout.adapter_bill_child_history,historyBillsBean.billList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(baseViewHolder.itemView.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rvAdapterBills.addItemDecoration(new DividerItemDecoration(baseViewHolder.itemView.getContext(),DividerItemDecoration.VERTICAL));

        rvAdapterBills.setLayoutManager(linearLayoutManager);
        rvAdapterBills.setAdapter(billChildHistoryAdapter);

        billChildHistoryAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent();
                intent.setClass(baseViewHolder.itemView.getContext(), BillHistoryDetailActivity.class);
                intent.putExtra("bill",historyBillsBean.billList.get(position));
                baseViewHolder.itemView.getContext().startActivity(intent);
            }
        });

    }
}
