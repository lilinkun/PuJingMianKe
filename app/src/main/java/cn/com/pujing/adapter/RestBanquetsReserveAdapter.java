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
import cn.com.pujing.entity.ChangeDataBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.util.Urls;

/**
 * author : liguo
 * date : 2021/3/10 16:50
 * description :
 */
public class RestBanquetsReserveAdapter extends BaseQuickAdapter<ChangeDataBean.DetailList, BaseViewHolder> {

    private Context context;

    public RestBanquetsReserveAdapter(int layoutResId, @Nullable List<ChangeDataBean.DetailList> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ChangeDataBean.DetailList detailList) {

        baseViewHolder.setText(R.id.tv_reserve_name,detailList.name);
        baseViewHolder.setText(R.id.tv_reserve_price,"￥ " + detailList.amount);
        baseViewHolder.setText(R.id.tv_reserve_num,"x" + detailList.quantity);

        ImageView imageView = baseViewHolder.getView(R.id.iv_reserve_head);

        Glide.with(context).load(PujingService.PREFIX + Urls.IMG + detailList.coverPic).error(R.color.gray_line).into(imageView);

    }
}
