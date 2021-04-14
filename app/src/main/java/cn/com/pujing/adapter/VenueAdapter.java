package cn.com.pujing.adapter;

import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.VenueBean;
import cn.com.pujing.http.PujingService;

/**
 * author : liguo
 * date : 2021/4/2 14:11
 * description :
 */
public class VenueAdapter extends BaseQuickAdapter<VenueBean.Records, BaseViewHolder> {

    public VenueAdapter(int layoutResId, @Nullable List<VenueBean.Records> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, VenueBean.Records venueBean) {
        baseViewHolder.setText(R.id.tv_venue_name,venueBean.name);

        ImageView ivVenuePic = baseViewHolder.getView(R.id.iv_venue_pic);

        Glide.with(baseViewHolder.itemView.getContext()).load(PujingService.PREFIX + PujingService.IMG + venueBean.thumbnail).error(R.drawable.ic_no_pic).into(ivVenuePic);

    }
}
