package cn.com.pujing.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.ActivityCalendar;
import cn.com.pujing.entity.ExerciseBean;
import cn.com.pujing.util.Urls;

/**
 * author : liguo
 * date : 2021/2/24 11:52
 * description :
 */
public class HistoryActivitiesAdapter extends BaseQuickAdapter<ExerciseBean.Data, BaseViewHolder> {


    public HistoryActivitiesAdapter(int layoutResId, @Nullable List<ExerciseBean.Data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ExerciseBean.Data record) {

        ImageView ivHeader = baseViewHolder.getView(R.id.iv_item_exercise);
        Glide.with(getContext())
                .load(Urls.PREFIX + Urls.IMG + record.photo)
//                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(ivHeader);
        baseViewHolder.setText(R.id.tv_title, record.activityName);
        baseViewHolder.setText(R.id.tv_status, record.calendarStatus);
        baseViewHolder.setText(R.id.tv_content, record.content);
        baseViewHolder.setText(R.id.tv_cost, String.format(getContext().getString(R.string.format_fee), record.price));

    }
}
