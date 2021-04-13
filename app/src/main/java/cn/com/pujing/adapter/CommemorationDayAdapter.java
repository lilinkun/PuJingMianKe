package cn.com.pujing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.CommemorationDayBean;

/**
 * author : liguo
 * date : 2021/4/13 9:31
 * description :
 */
public class CommemorationDayAdapter extends BaseQuickAdapter<CommemorationDayBean.CommemorationDay, BaseViewHolder> {

    public CommemorationDayAdapter(int layoutResId, @Nullable List<CommemorationDayBean.CommemorationDay> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CommemorationDayBean.CommemorationDay commemorationDay) {
        baseViewHolder.setText(R.id.tv_commemoration_day_name,commemorationDay.commemorationName);
        baseViewHolder.setText(R.id.tv_commemoration_day_date,commemorationDay.commemorationDay);
    }
}
