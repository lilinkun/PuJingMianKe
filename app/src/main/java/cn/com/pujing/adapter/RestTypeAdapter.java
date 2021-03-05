package cn.com.pujing.adapter;

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

    public RestTypeAdapter(int layoutResId, @Nullable List<RestTypeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RestTypeBean restTypeBean) {
        baseViewHolder.setText(R.id.tv_rest_type,restTypeBean.label);

        TextView  tvRestType = baseViewHolder.getView(R.id.tv_rest_type);
        View viewRestType = baseViewHolder.getView(R.id.view_rest_type);


        if (baseViewHolder.getAdapterPosition() == itemPosition){
            tvRestType.setTextSize(16);
            viewRestType.setVisibility(View.VISIBLE);
            baseViewHolder.setBackgroundResource(R.id.rl_rest_type,R.color.white);
        }else {
            tvRestType.setTextSize(14);
            viewRestType.setVisibility(View.GONE);
            baseViewHolder.setBackgroundResource(R.id.rl_rest_type,R.color.gray_line);
        }

    }

    public void setPositionView(int position){
        this.itemPosition = position;
        notifyDataSetChanged();
    }
}
