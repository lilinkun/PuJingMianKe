package cn.com.pujing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.OrderTpeBean;

/**
 * author : liguo
 * date : 2021/4/13 16:27
 * description :
 */
public class GridActivityAdapter extends BaseQuickAdapter<OrderTpeBean, BaseViewHolder> {

    private int typePos = 0;

    public GridActivityAdapter(int layoutResId, @Nullable List<OrderTpeBean> data,int pos) {
        super(layoutResId, data);
        this.typePos = pos;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, OrderTpeBean orderTpeBean) {

        baseViewHolder.setText(R.id.tv_order_type, orderTpeBean.orderTypeName);

        if (typePos == baseViewHolder.getAdapterPosition()) {
            baseViewHolder.setBackgroundResource(R.id.tv_order_type, R.drawable.bg_btn_myorder_click);
            baseViewHolder.setTextColorRes(R.id.tv_order_type, R.color.white);
        }else {
            baseViewHolder.setBackgroundResource(R.id.tv_order_type, R.drawable.bg_btn_myorder_unclick);
            baseViewHolder.setTextColorRes(R.id.tv_order_type, R.color.grey_common);
        }
    }

    public void setTypePos(int typePos){
        this.typePos = typePos;
        notifyDataSetChanged();
    }
}
