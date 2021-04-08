package cn.com.pujing.adapter;

import android.text.TextPaint;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;

/**
 * author : liguo
 * date : 2021/4/8 16:02
 * description :
 */
public class ServiceTipAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private int pos = 0;


    public ServiceTipAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {

        TextView tvServiceTip = baseViewHolder.getView(R.id.tv_service_tip);

        if (pos == baseViewHolder.getAdapterPosition()){
            tvServiceTip.setText(s);
            TextPaint tp = tvServiceTip.getPaint();
            tp.setFakeBoldText(true);
            baseViewHolder.setVisible(R.id.view_service_tip,true);
        }else {
            tvServiceTip.setText(s);
            TextPaint tp = tvServiceTip.getPaint();
            tp.setFakeBoldText(false);
            baseViewHolder.setVisible(R.id.view_service_tip,false);
        }
    }

    public void onClickItem(int position){
        pos = position;
        notifyDataSetChanged();
    }


}
