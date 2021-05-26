package cn.com.pujing.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.RestSortDetailBean;

/**
 * author : liguo
 * date : 2021/3/12 16:48
 * description :
 */
public class SetMealAdapter extends BaseMultiItemQuickAdapter<RestSortDetailBean, BaseViewHolder> {

    private Context context;

    public SetMealAdapter(Context context,List<RestSortDetailBean> restSortDetailBeans ){
        addItemType(0, R.layout.layout_setmeal_item_detail);
        addItemType(1,R.layout.layout_setmeal_item_title);
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RestSortDetailBean restSortDetailBean) {
        if (restSortDetailBean.isTitle()){

            baseViewHolder.setText(R.id.tv_setmeal_name,"· " + restSortDetailBean.getName());

        }else {

            baseViewHolder.setText(R.id.tv_setmeal_detail_name,restSortDetailBean.getName());
            if(restSortDetailBean.getCalculateUnit() == null) {
                baseViewHolder.setText(R.id.tv_setmeal_detail_number, "(" + restSortDetailBean.getGoodsNum() + ")");
            }else {
                baseViewHolder.setText(R.id.tv_setmeal_detail_number, "(" + restSortDetailBean.getGoodsNum() + restSortDetailBean.getCalculateUnit() + ")");
            }
        }
    }
}
