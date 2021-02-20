package cn.com.pujing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.PaidBillItem;

public class PaidBillAdapter extends BaseQuickAdapter<PaidBillItem, BaseViewHolder> {

    public PaidBillAdapter(int layoutResId) {
        super(layoutResId);
    }

    public PaidBillAdapter(int layoutResId, @Nullable List<PaidBillItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, PaidBillItem paidBillItem) {
        baseViewHolder.setImageResource(R.id.iv, paidBillItem.imgRes);
        baseViewHolder.setText(R.id.tv_name, paidBillItem.name);
        baseViewHolder.setText(R.id.tv_time, paidBillItem.time);
        baseViewHolder.setText(R.id.tv_content, paidBillItem.content);
        baseViewHolder.setText(R.id.tv_price, paidBillItem.price);
    }
}
