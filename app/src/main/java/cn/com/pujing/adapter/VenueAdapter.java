package cn.com.pujing.adapter;

import android.widget.BaseAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.VenueBean;

/**
 * author : liguo
 * date : 2021/4/2 14:11
 * description :
 */
public class VenueAdapter extends BaseQuickAdapter<VenueBean, BaseViewHolder> {

    public VenueAdapter(int layoutResId, @Nullable List<VenueBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, VenueBean venueBean) {
        baseViewHolder.setText(R.id.tv_venue_name,venueBean.venueName);
    }
}
