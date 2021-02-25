package cn.com.pujing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.OpinionTypeBean;

public class FeedbackPopAdapter extends BaseQuickAdapter<OpinionTypeBean.Data, BaseViewHolder> {

    public FeedbackPopAdapter(int layoutResId, @Nullable List<OpinionTypeBean.Data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, OpinionTypeBean.Data s) {
        baseViewHolder.setText(R.id.tv_pop_feedback_type,s.label);
    }
}
