package cn.com.pujing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.MessageBean;

/**
 * author : liguo
 * date : 2021/4/18 12:30
 * description :
 */
public class MessageAdapter extends BaseQuickAdapter<MessageBean, BaseViewHolder> implements LoadMoreModule {

    public MessageAdapter(int layoutResId, @Nullable List<MessageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MessageBean messageBean) {
        baseViewHolder.setText(R.id.tv_message_conntent,messageBean.messageContent);
        baseViewHolder.setText(R.id.tv_message_time,messageBean.createTime);

        if (messageBean.status == 0){
            baseViewHolder.setBackgroundResource(R.id.view_circle,R.drawable.bg_circle_rest);
        }else {
            baseViewHolder.setBackgroundResource(R.id.view_circle,R.drawable.bg_unread);
        }

    }
}
