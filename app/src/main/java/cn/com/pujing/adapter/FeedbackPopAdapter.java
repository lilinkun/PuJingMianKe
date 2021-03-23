package cn.com.pujing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.FeedbackBean;

public class FeedbackPopAdapter extends BaseQuickAdapter<FeedbackBean, BaseViewHolder> {

    public FeedbackPopAdapter(int layoutResId, @Nullable List<FeedbackBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, FeedbackBean s) {
        baseViewHolder.setText(R.id.tv_pop_feedback_type,s.label);
    }
}
