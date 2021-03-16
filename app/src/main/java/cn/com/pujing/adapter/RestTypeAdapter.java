package cn.com.pujing.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.RestTypeBean;

/**
 * author : liguo
 * date : 2021/3/4 15:45
 * description :
 */
public class RestTypeAdapter extends BaseQuickAdapter<RestTypeBean, BaseViewHolder> {

    private int itemPosition = 0;
    private Context context;

    public RestTypeAdapter(int layoutResId,Context context, @Nullable List<RestTypeBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RestTypeBean restTypeBean) {
        baseViewHolder.setText(R.id.tv_rest_type,restTypeBean.label);

        TextView  tvRestType = baseViewHolder.getView(R.id.tv_rest_type);
        View viewRestType = baseViewHolder.getView(R.id.view_rest_type);

        if (baseViewHolder.getAdapterPosition() == 0){
            baseViewHolder.setVisible(R.id.view_rest_devide,false);
        }else {
            baseViewHolder.setVisible(R.id.view_rest_devide,true);
        }

        if (baseViewHolder.getAdapterPosition() == itemPosition){
            tvRestType.setTextSize(16);
            tvRestType.setTextColor(context.getResources().getColor(R.color.main_color));
            viewRestType.setVisibility(View.VISIBLE);
        }else {
            tvRestType.setTextSize(14);
            tvRestType.setTextColor(context.getResources().getColor(R.color.black));
            viewRestType.setVisibility(View.GONE);
        }

    }

    public void setPositionView(int position){
        this.itemPosition = position;
        notifyDataSetChanged();
    }
}
