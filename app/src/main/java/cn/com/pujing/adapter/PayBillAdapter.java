package cn.com.pujing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.PayBillItem;

public class PayBillAdapter extends BaseQuickAdapter<PayBillItem, BaseViewHolder> {

    public PayBillAdapter(int layoutResId) {
        super(layoutResId);
    }

    public PayBillAdapter(int layoutResId, @Nullable List<PayBillItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, PayBillItem payBillItem) {
        baseViewHolder.setImageResource(R.id.iv, payBillItem.imgRes);
        baseViewHolder.setText(R.id.tv_name, payBillItem.name);
        baseViewHolder.setText(R.id.tv_time, payBillItem.time);
        baseViewHolder.setText(R.id.tv_content, payBillItem.content);
        baseViewHolder.setText(R.id.tv_price, payBillItem.price);
    }
}
