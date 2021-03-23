package cn.com.pujing.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.HistoryActivitiesBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.util.Urls;

/**
 * author : liguo
 * date : 2021/2/23 18:52
 * description :
 */
public class MyActivitiesAdapter extends BaseQuickAdapter<HistoryActivitiesBean.Records, BaseViewHolder> {


    public MyActivitiesAdapter(int layoutResId, @Nullable List<HistoryActivitiesBean.Records> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HistoryActivitiesBean.Records record) {

        ImageView ivHeader = baseViewHolder.getView(R.id.iv_myactivities);
        Glide.with(getContext())
                .load(PujingService.PREFIX + Urls.IMG + record.photo)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(ivHeader);
        baseViewHolder.setText(R.id.tv_myactivies_title, record.activityName);
        baseViewHolder.setText(R.id.tv_activities_status, record.calendarStatus_label);
        baseViewHolder.setText(R.id.tv_myactivies_content, record.summary);
        baseViewHolder.setText(R.id.tv_activities_price, String.format(getContext().getString(R.string.format_fee), record.price));

    }
}
