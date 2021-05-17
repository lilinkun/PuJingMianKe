package cn.com.pujing.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.RoutineRecordBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.util.PuJingUtils;

/**
 * author : liguo
 * date : 2021/3/17 16:49
 * description :
 */
public class MealOrderDetailAdapter extends BaseQuickAdapter<RoutineRecordBean.CycleMealVoList, BaseViewHolder> {

    Context context;

    public MealOrderDetailAdapter(int layoutResId, @Nullable List<RoutineRecordBean.CycleMealVoList> data,Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RoutineRecordBean.CycleMealVoList cycleMealVoList) {
        baseViewHolder.setText(R.id.tv_meal,cycleMealVoList.type_label);
        baseViewHolder.setText(R.id.tv_meal_name,cycleMealVoList.mealNikeName);
        baseViewHolder.setText(R.id.tv_meal_name_value,cycleMealVoList.mealName);
        if (baseViewHolder.getAdapterPosition() == 0) {
            baseViewHolder.setVisible(R.id.view_line, false);
        }else {
            baseViewHolder.setVisible(R.id.view_line, true);
        }

        ImageView ivMealPic = baseViewHolder.getView(R.id.iv_meal_pic);

        if (cycleMealVoList.coverPic != null){
            if (cycleMealVoList.coverPic.contains(",")){
                Glide.with(context).load(PujingService.PREFIX + PujingService.IMG + cycleMealVoList.coverPic.split(",")).error(R.drawable.ic_no_pic).into(ivMealPic);
            }else {
                Glide.with(context).load(PujingService.PREFIX + PujingService.IMG + cycleMealVoList.coverPic).error(R.drawable.ic_no_pic).into(ivMealPic);
            }
        }


    }
}
