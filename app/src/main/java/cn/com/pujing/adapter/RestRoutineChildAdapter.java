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

    List<SetMealBean.FoodDetailVoList> data;

    public RestRoutineChildAdapter(int layoutResId, @Nullable List<SetMealBean.FoodDetailVoList> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SetMealBean.FoodDetailVoList foodDetailVoList) {

        if (baseViewHolder.getAdapterPosition() > 0){
            if(data.get(baseViewHolder.getAdapterPosition() - 1).getCategoryName() != null && foodDetailVoList.getCategoryName() != null) {
                if (data.get(baseViewHolder.getAdapterPosition() - 1).getCategoryName().equals(foodDetailVoList.getCategoryName())) {
                    baseViewHolder.setVisible(R.id.tv_child_restroutine_type, false);
                } else {
                    baseViewHolder.setVisible(R.id.tv_child_restroutine_type, true);
                }
            }else {
                if (foodDetailVoList.getCategoryName() == null) {
                    baseViewHolder.setVisible(R.id.tv_child_restroutine_type, false);
                }else {
                    baseViewHolder.setVisible(R.id.tv_child_restroutine_type, true);
                }
            }
        }else {
            if (foodDetailVoList.getCategoryName() == null) {
                baseViewHolder.setVisible(R.id.tv_child_restroutine_type, false);
            }else {
                baseViewHolder.setVisible(R.id.tv_child_restroutine_type, true);
            }
        }

        baseViewHolder.setText(R.id.tv_child_restroutine_type,foodDetailVoList.getCategoryName());

        baseViewHolder.setText(R.id.tv_child_restroutine_content,foodDetailVoList.getFoodCategoryName());
    }
}
