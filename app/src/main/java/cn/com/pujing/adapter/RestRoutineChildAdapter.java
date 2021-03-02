package cn.com.pujing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.haibin.calendarview.BaseView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.SetMealBean;

/**
 * author : liguo
 * date : 2021/3/2 17:55
 * description :
 */
public class RestRoutineChildAdapter extends BaseQuickAdapter<SetMealBean.FoodDetailVoList, BaseViewHolder> {

    public RestRoutineChildAdapter(int layoutResId, @Nullable List<SetMealBean.FoodDetailVoList> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SetMealBean.FoodDetailVoList foodDetailVoList) {
        baseViewHolder.setText(R.id.tv_child_restroutine_type,foodDetailVoList.getCategoryName());
        baseViewHolder.setText(R.id.tv_child_restroutine_content,foodDetailVoList.getFoodCategoryName());
    }
}
