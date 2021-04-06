package cn.com.pujing.adapter;

import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.ReserveDeviceBean;

import static cn.com.pujing.activity.VenueReserveActivity.ClickPos;

/**
 * author : liguo
 * date : 2021/4/6 11:38
 * description :
 */
public class ShowReserveTimeAdapter extends BaseQuickAdapter<ReserveDeviceBean.TimesReserveNumList, BaseViewHolder> {

    private int mClickPos = -1;

    public ShowReserveTimeAdapter(int layoutResId, @Nullable List<ReserveDeviceBean.TimesReserveNumList> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ReserveDeviceBean.TimesReserveNumList timesReserveNumList) {

        RadioButton radioButton = baseViewHolder.getView(R.id.rb_show_reserve_time);

        if (Integer.valueOf(timesReserveNumList.reserveNum) != 0) {
            baseViewHolder.setText(R.id.tv_limit_person, "(剩余" + timesReserveNumList.reserveNum + ")");
            baseViewHolder.setTextColorRes(R.id.tv_show_reserve_time,R.color.black);
            baseViewHolder.setEnabled(R.id.rb_show_reserve_time,true);
        }else {
            baseViewHolder.setEnabled(R.id.rb_show_reserve_time,false);
            baseViewHolder.setTextColorRes(R.id.tv_show_reserve_time,R.color.common_gray);
        }

        if (baseViewHolder.getAdapterPosition() == ClickPos){
            radioButton.setChecked(true);
            radioButton.setSelected(true);
        }else {
            radioButton.setChecked(false);
            radioButton.setSelected(false);
        }


        baseViewHolder.setText(R.id.tv_show_reserve_time, timesReserveNumList.startEndTime);

    }


    public void setPosClick(int pos){
        this.mClickPos = pos;
        notifyDataSetChanged();
    }
}
