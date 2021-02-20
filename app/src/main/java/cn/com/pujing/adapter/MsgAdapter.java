package cn.com.pujing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.MsgItem;

public class MsgAdapter extends BaseQuickAdapter<MsgItem, BaseViewHolder> {

    public MsgAdapter(int layoutResId) {
        super(layoutResId);
    }

    public MsgAdapter(int layoutResId, @Nullable List<MsgItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MsgItem msgItem) {
        baseViewHolder.setImageResource(R.id.iv, msgItem.imgRes);
        baseViewHolder.setText(R.id.tv_type, msgItem.type);
        baseViewHolder.setText(R.id.tv_content, msgItem.content);
        baseViewHolder.setText(R.id.tv_time, msgItem.time);
    }
}
