package cn.com.pujing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.DeviceBean;
import cn.com.pujing.entity.ServiceItemsBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.util.PuJingUtils;

/**
 * author : liguo
 * date : 2021/4/8 11:12
 * description :
 */
public class ServiceTypeAdapter extends BaseQuickAdapter<ServiceItemsBean, BaseViewHolder> {

    private int mClickPos = 0;

    public ServiceTypeAdapter(int layoutResId, @Nullable List<ServiceItemsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ServiceItemsBean serviceItemsBean) {

        baseViewHolder.setText(R.id.tv_device_name,serviceItemsBean.name);
        String content = "";

        if (serviceItemsBean.groupName == null || serviceItemsBean.groupName.trim().length() == 0){
            content = PuJingUtils.removeAmtLastZero(serviceItemsBean.price) + "元/" + serviceItemsBean.unit;
            baseViewHolder.setTextColorRes(R.id.tv_device_name,R.color.black);
        }else {
            content = serviceItemsBean.groupName;
            baseViewHolder.setTextColorRes(R.id.tv_device_name,R.color.main_color);
        }


        baseViewHolder.setText(R.id.tv_device_content,content);

        if (mClickPos == baseViewHolder.getAdapterPosition()){
            baseViewHolder.setBackgroundResource(R.id.ll_service_type,R.drawable.bg_venue_line);
        }else {
            baseViewHolder.setBackgroundResource(R.id.ll_service_type,R.drawable.bg_input_text);
        }

    }


    public void setClickPos(int position){
        mClickPos = position;
        notifyDataSetChanged();
    }

}
