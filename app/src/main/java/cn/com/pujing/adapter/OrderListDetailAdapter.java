package cn.com.pujing.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.RestBanquetsBean;
import cn.com.pujing.entity.RestOrderBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.util.PuJingUtils;

/**
 * author : liguo
 * date : 2021/3/12 18:20
 * description :
 */
public class OrderListDetailAdapter extends BaseQuickAdapter<RestBanquetsBean.OrderFoodList, BaseViewHolder> {

    private Context context;

    public OrderListDetailAdapter(int layoutResId, @Nullable List<RestBanquetsBean.OrderFoodList> data,Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RestBanquetsBean.OrderFoodList orderFoodList) {
        baseViewHolder.setText(R.id.tv_reserve_name,orderFoodList.getFoodName());
        baseViewHolder.setText(R.id.tv_reserve_price,"￥" + PuJingUtils.removeAmtLastZero(orderFoodList.getPrice()));
        baseViewHolder.setText(R.id.tv_reserve_num, "x"+orderFoodList.getNumber());

        ImageView imageView = baseViewHolder.getView(R.id.iv_reserve_head);

        Glide.with(context).load(PujingService.PREFIX + PujingService.IMG + orderFoodList.getCoverPic()).placeholder(R.drawable.ic_no_pic).into(imageView);


    }
}
