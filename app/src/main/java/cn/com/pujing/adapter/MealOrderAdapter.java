package cn.com.pujing.adapter;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.MealOrderBean;
import cn.com.pujing.entity.RestSortDetailBean;
import cn.com.pujing.entity.RoutineRecordBean;

/**
 * author : liguo
 * date : 2021/3/17 15:05
 * description :
 */
public class MealOrderAdapter extends BaseQuickAdapter<RoutineRecordBean.CycleMeals, BaseViewHolder> {

    private Context context;

    public MealOrderAdapter(int layoutResId, @Nullable List<RoutineRecordBean.CycleMeals> data,Context context) {
        super(layoutResId, data);
        this.context = context;
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RoutineRecordBean.CycleMeals routineRecordBean) {

        baseViewHolder.setText(R.id.tv_order_date,routineRecordBean.time);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);

        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        RecyclerView recyclerView = baseViewHolder.getView(R.id.rv_adapter_meal_order);

        MealOrderDetailAdapter mealOrderDetailAdapter = new MealOrderDetailAdapter(R.layout.adapter_meal_order_detail,routineRecordBean.cycleMealVoList,context);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mealOrderDetailAdapter);

    }

}
