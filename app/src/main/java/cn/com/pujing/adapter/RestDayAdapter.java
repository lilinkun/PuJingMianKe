package cn.com.pujing.adapter;

import android.content.Context;
import android.widget.TextView;

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

        TextView tvWeekDay = baseViewHolder.getView(R.id.tv_week_day);
        TextView tvDateDay = baseViewHolder.getView(R.id.tv_date_day);

        baseViewHolder.setText(R.id.tv_week_day,restDayBean.weekDay);
        baseViewHolder.setText(R.id.tv_date_day,restDayBean.monthDay);

        if (restDayBean.flag.equals("1")){
            baseViewHolder.setVisible(R.id.view_circle,true);
        }else {
            baseViewHolder.setVisible(R.id.view_circle,false);
        }

        if (itemPosition == baseViewHolder.getAdapterPosition()){
            baseViewHolder.setVisible(R.id.view_day_line,true);
            baseViewHolder.setTextColor(R.id.tv_week_day,context.getResources().getColor(R.color.main_color));
            baseViewHolder.setTextColor(R.id.tv_date_day,context.getResources().getColor(R.color.main_color));
            tvWeekDay.setTextSize(16);
            baseViewHolder.setBackgroundColor(R.id.rl_day_top,context.getResources().getColor(R.color.white));
        }else {
            baseViewHolder.setVisible(R.id.view_day_line,false);
            baseViewHolder.setBackgroundColor(R.id.rl_day_top,context.getResources().getColor(R.color.gray_line));

            baseViewHolder.setTextColor(R.id.tv_week_day,context.getResources().getColor(R.color.text_black));
            baseViewHolder.setTextColor(R.id.tv_date_day,context.getResources().getColor(R.color.text_black));
            tvWeekDay.setTextSize(14);
        }

    }


    public void setPositionView(int position){
        this.itemPosition = position;
        notifyDataSetChanged();
    }
}
