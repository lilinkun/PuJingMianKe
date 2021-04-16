package cn.com.pujing.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.OrderItemBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.util.PuJingUtils;

public class OrderAdapter extends BaseQuickAdapter<OrderItemBean.MyOrder, BaseViewHolder> implements LoadMoreModule {

    private List<OrderItemBean.MyOrder> orderItem;

    public OrderAdapter(int layoutResId) {
        super(layoutResId);
    }

    public OrderAdapter(int layoutResId, @Nullable List<OrderItemBean.MyOrder> data) {
        super(layoutResId, data);
    }

    public void setData(List<OrderItemBean.MyOrder> data){
        this.orderItem = data;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, OrderItemBean.MyOrder orderItem) {
        baseViewHolder.setText(R.id.tv_name, orderItem.orderName);
        baseViewHolder.setText(R.id.tv_type, orderItem.orderType_label);
        baseViewHolder.setText(R.id.tv_date, orderItem.createTime);
        baseViewHolder.setText(R.id.tv_status, orderItem.orderStatus_label);
        baseViewHolder.setText(R.id.tv_price, PuJingUtils.removeAmtLastZero(orderItem.orderMoney)+"");

        ImageView imageView = baseViewHolder.getView(R.id.iv);
        String url =  PujingService.PREFIX+PujingService.IMG+orderItem.orderPic;
        Glide.with(baseViewHolder.itemView.getContext()).load(url).error(R.drawable.ic_no_pic).into(imageView);

    }
}
