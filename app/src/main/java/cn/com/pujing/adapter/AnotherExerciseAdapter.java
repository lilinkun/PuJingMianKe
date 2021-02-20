package cn.com.pujing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.QuerySelectDay;

public class AnotherExerciseAdapter extends BaseQuickAdapter<QuerySelectDay.Data, BaseViewHolder> {

    public AnotherExerciseAdapter(int layoutResId, @Nullable List<QuerySelectDay.Data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, QuerySelectDay.Data data) {
        baseViewHolder.setText(R.id.tv_title, data.content);
        baseViewHolder.setText(R.id.tv_content, data.timeSlot);
    }
}
