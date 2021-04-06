package cn.com.pujing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.DeviceBean;

/**
 * author : liguo
 * date : 2021/4/2 18:33
 * description :
 */
public class DeviceAdapter extends BaseQuickAdapter<DeviceBean, BaseViewHolder> {

    private int mClickPos = 0;

    public DeviceAdapter(int layoutResId, @Nullable List<DeviceBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, DeviceBean deviceBean) {

        baseViewHolder.setText(R.id.tv_device_name,deviceBean.deviceName);

        if (mClickPos == baseViewHolder.getAdapterPosition()){
            baseViewHolder.setBackgroundResource(R.id.tv_device_name,R.drawable.bg_venue_line);
        }else {
            baseViewHolder.setBackgroundResource(R.id.tv_device_name,R.drawable.bg_input_text);
        }

    }


    public void setClickPos(int position){
        mClickPos = position;
        notifyDataSetChanged();
    }

}
