package cn.com.pujing.adapter;

import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.activity.LifeTypeActivity;
import cn.com.pujing.entity.ServicePutawayManageTimeBean;


/**
 * author : liguo
 * date : 2021/4/8 15:32
 * description :
 */
public class ShowServiceTimeAdapter extends BaseQuickAdapter<ServicePutawayManageTimeBean, BaseViewHolder> {

    private int mClickPos = -1;

    public ShowServiceTimeAdapter(int layoutResId, @Nullable List<ServicePutawayManageTimeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ServicePutawayManageTimeBean servicePutawayManageTimeBean) {

        RadioButton radioButton = baseViewHolder.getView(R.id.rb_show_reserve_time);

        if (Integer.valueOf(servicePutawayManageTimeBean.number) != 0) {
            baseViewHolder.setText(R.id.tv_limit_person, "(剩余" + servicePutawayManageTimeBean.number + ")");
            baseViewHolder.setTextColorRes(R.id.tv_show_reserve_time,R.color.black);
            baseViewHolder.setEnabled(R.id.rb_show_reserve_time,true);
        }else {
            baseViewHolder.setEnabled(R.id.rb_show_reserve_time,false);
            baseViewHolder.setTextColorRes(R.id.tv_show_reserve_time,R.color.common_gray);
        }

        if (baseViewHolder.getAdapterPosition() == LifeTypeActivity.ClickServicePos){
            radioButton.setChecked(true);
            radioButton.setSelected(true);
        }else {
            radioButton.setChecked(false);
            radioButton.setSelected(false);
        }


        baseViewHolder.setText(R.id.tv_show_reserve_time, servicePutawayManageTimeBean.timeQuantum);

    }


    public void setPosClick(int pos){
        this.mClickPos = pos;
        notifyDataSetChanged();
    }
}
