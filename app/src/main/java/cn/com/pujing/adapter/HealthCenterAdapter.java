package cn.com.pujing.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import cn.com.pujing.R;
import cn.com.pujing.entity.HealthCenterBean;

/**
 * author : liguo
 * date : 2021/3/26 13:54
 * description :
 */
public class HealthCenterAdapter extends BaseMultiItemQuickAdapter<HealthCenterBean, BaseViewHolder> {

    public HealthCenterAdapter(ArrayList<HealthCenterBean> healthCenterBeans){
        super(healthCenterBeans);
        addItemType(0, R.layout.adapter_health_center_title);
        addItemType(-1,R.layout.adapter_health_center_content);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HealthCenterBean healthCenterBean) {
        if (baseViewHolder.getItemViewType() == 0){
            if (baseViewHolder.getAdapterPosition() == 0){
                baseViewHolder.setVisible(R.id.view_title,false);
            }else {
                baseViewHolder.setVisible(R.id.view_title,true);
            }
            baseViewHolder.setText(R.id.tv_type_name,healthCenterBean.getProjectTypeTitle());
        }else {
            baseViewHolder.setText(R.id.tv_project_name,healthCenterBean.getProjectTitleName());
            baseViewHolder.setText(R.id.tv_project_content,healthCenterBean.getProjectContent());
        }
    }
}
