package cn.com.pujing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.haibin.calendarview.BaseView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.OrderTpeBean;

/**
 * author : liguo
 * date : 2021/4/9 11:17
 * description :
 */
public class GridOrderAdapter extends BaseQuickAdapter<OrderTpeBean, BaseViewHolder> {

    private int typePos = 0;

    public GridOrderAdapter(int layoutResId, @Nullable List<OrderTpeBean> data) {
        super(layoutResId, data);
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
