package cn.com.pujing.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.datastructure.OrderItem;

public class OrderAdapter extends BaseQuickAdapter<OrderItem, BaseViewHolder> {

    public OrderAdapter(int layoutResId) {
        super(layoutResId);
    }

    public OrderAdapter(int layoutResId, @Nullable List<OrderItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, OrderItem orderItem) {
        baseViewHolder.setImageResource(R.id.iv, orderItem.imgRes);
        baseViewHolder.setText(R.id.tv_name, orderItem.name);
        baseViewHolder.setText(R.id.tv_type, orderItem.type);
        baseViewHolder.setText(R.id.tv_date, orderItem.date);
        baseViewHolder.setText(R.id.tv_price, orderItem.price);

        TextView statusTextView = baseViewHolder.getView(R.id.tv_status);
        TextView cancelTextView = baseViewHolder.getView(R.id.tv_cancel);
        if (getContext().getString(R.string.cancel).equals(orderItem.status)) {
            statusTextView.setVisibility(View.GONE);
            cancelTextView.setVisibility(View.VISIBLE);
        } else {
            cancelTextView.setVisibility(View.GONE);
            statusTextView.setVisibility(View.VISIBLE);
            statusTextView.setText(orderItem.status);
        }
    }
}
