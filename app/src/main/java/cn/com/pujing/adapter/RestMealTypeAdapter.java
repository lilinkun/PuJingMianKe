package cn.com.pujing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.FeedbackBean;
import cn.com.pujing.entity.RestMealTypeBean;

/**
 * author : liguo
 * date : 2021/3/10 20:30
 * description :
 */
public class RestMealTypeAdapter extends BaseQuickAdapter<RestMealTypeBean, BaseViewHolder> {

    public RestMealTypeAdapter(int layoutResId, @Nullable List<RestMealTypeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RestMealTypeBean restMealTypeBean) {
       baseViewHolder.setText(R.id.tv_meal_type_value,restMealTypeBean.label);
    }
}
