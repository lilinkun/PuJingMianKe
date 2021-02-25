package cn.com.pujing.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.util.Urls;
import cn.com.pujing.entity.ActivityCalendar;

public class ExerciseAdapter extends BaseMultiItemQuickAdapter<ActivityCalendar.Data.Record, BaseViewHolder> implements LoadMoreModule {

    public ExerciseAdapter(@Nullable List<ActivityCalendar.Data.Record> data) {
        super(data);
        addItemType(-1, R.layout.header_exercise);
        addItemType(0, R.layout.item_exercise);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ActivityCalendar.Data.Record record) {
        switch (baseViewHolder.getItemViewType()) {
            case -1:
                ImageView ivHeader = baseViewHolder.getView(R.id.iv_header_exercise);
                Glide.with(getContext())
                        .load(Urls.PREFIX + Urls.IMG + record.photo)
//                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(ivHeader);
                baseViewHolder.setText(R.id.tv_title, record.activityName);
                baseViewHolder.setText(R.id.tv_status, record.calendarStatus);
                baseViewHolder.setText(R.id.tv_content, record.content);
                baseViewHolder.setText(R.id.tv_cost, String.format(getContext().getString(R.string.format_fee), record.price));
                baseViewHolder.setText(R.id.tv_remaining_enrollment, String.format(getContext().getString(R.string.format_remaining), record.stayUserNum));
                baseViewHolder.setText(R.id.tv_time, record.activityStartDate);
                break;
            case 0:
                ImageView iv = baseViewHolder.getView(R.id.iv_item_exercise);
                Glide.with(getContext())
                        .load(Urls.PREFIX + Urls.IMG + record.photo)
//                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(iv);
                baseViewHolder.setText(R.id.tv_title, record.activityName);
                baseViewHolder.setText(R.id.tv_status, record.calendarStatus);
                baseViewHolder.setText(R.id.tv_content, record.content);
                baseViewHolder.setText(R.id.tv_cost, String.format(getContext().getString(R.string.format_fee), record.price));
                baseViewHolder.setText(R.id.tv_remaining_enrollment, String.format(getContext().getString(R.string.format_remaining), record.stayUserNum));
                baseViewHolder.setText(R.id.tv_time, record.activityStartDate);
                break;
        }
    }
}
