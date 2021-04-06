package cn.com.pujing.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.BanquetBean;

/**
 * author : liguo
 * date : 2021/3/5 11:17
 * description :
 */
public class RestSortAdapter extends BaseQuickAdapter<BanquetBean.CategoryList, BaseViewHolder> {

    private int checkedPosition;
    private Context context;

    public RestSortAdapter(int layoutResId, Context context,@Nullable List<BanquetBean.CategoryList> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, BanquetBean.CategoryList banquetBean) {
        TextView tvName = baseViewHolder.getView(R.id.tv_rest_sort);
        tvName.setText(banquetBean.getGroupName());

        RelativeLayout mView = baseViewHolder.getView(R.id.linear_layout);

        TextPaint tp = tvName.getPaint();

        if (baseViewHolder.getAdapterPosition() == checkedPosition) {
            mView.setBackgroundColor(context.getResources().getColor(R.color.white));
            tvName.setTextColor(context.getResources().getColor(R.color.black));
            tvName.setTextSize(20);
            tp.setFakeBoldText(true);
            baseViewHolder.setVisible(R.id.view_rest_line, true);
        } else {
            mView.setBackgroundColor(context.getResources().getColor(R.color.gray_line));
            tvName.setTextColor(context.getResources().getColor(R.color.text_black));
            tvName.setTextSize(16);
            tp.setFakeBoldText(false);
            baseViewHolder.setVisible(R.id.view_rest_line, false);
        }

        if (banquetBean.getQuantity() > 0){
            baseViewHolder.setVisible(R.id.tv_choose_num, true);
            baseViewHolder.setText(R.id.tv_choose_num,banquetBean.getQuantity()+"");
        }else {
            baseViewHolder.setVisible(R.id.tv_choose_num, false);
            baseViewHolder.setText(R.id.tv_choose_num,banquetBean.getQuantity()+"");
        }

    }


    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
        notifyDataSetChanged();
    }

}
