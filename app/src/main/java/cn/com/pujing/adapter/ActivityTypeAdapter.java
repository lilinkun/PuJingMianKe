package cn.com.pujing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.entity.ActivityTypeBean;

/**
 * author : liguo
 * date : 2021/4/13 16:46
 * description :
 */
public class ActivityTypeAdapter extends BaseQuickAdapter<ActivityTypeBean, BaseViewHolder> {

    public ActivityTypeAdapter(int layoutResId, @Nullable List<ActivityTypeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ActivityTypeBean activityTypeBean) {


    }
}
