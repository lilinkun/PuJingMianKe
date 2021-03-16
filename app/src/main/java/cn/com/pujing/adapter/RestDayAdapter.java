package cn.com.pujing.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.RestDayBean;

/**
 * author : liguo
 * date : 2021/3/7 13:17
 * description :
 */
public class RestDayAdapter extends BaseQuickAdapter<RestDayBean, BaseViewHolder> {

    private int itemPosition = 0;
    private Context context;

    public RestDayAdapter(int layoutResId, @Nullable List<RestDayBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RestDayBean restDayBean) {
        baseViewHolder.setText(R.id.tv_week_day,restDayBean.weekDay);
        baseViewHolder.setText(R.id.tv_date_day,restDayBean.monthDay);

        if (itemPosition == baseViewHolder.getAdapterPosition()){
            baseViewHolder.setVisible(R.id.view_day_line,true);
            baseViewHolder.setBackgroundColor(R.id.rl_day_top,context.getResources().getColor(R.color.white));
        }else {
            baseViewHolder.setVisible(R.id.view_day_line,false);
            baseViewHolder.setBackgroundColor(R.id.rl_day_top,context.getResources().getColor(R.color.gray_line));
        }

    }



    public void setPositionView(int position){
        this.itemPosition = position;
        notifyDataSetChanged();
    }
}
