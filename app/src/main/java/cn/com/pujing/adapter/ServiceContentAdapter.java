package cn.com.pujing.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.BasicServiceVoListBean;
import cn.com.pujing.http.PujingService;

/**
 * author : liguo
 * date : 2021/4/8 9:57
 * description :
 */
public class ServiceContentAdapter extends BaseQuickAdapter<BasicServiceVoListBean, BaseViewHolder> {

    public ServiceContentAdapter(int layoutResId, @Nullable List<BasicServiceVoListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, BasicServiceVoListBean basicServiceVoListBean) {
        baseViewHolder.setText(R.id.tv_project_name,basicServiceVoListBean.name);
        baseViewHolder.setText(R.id.tv_project_content,basicServiceVoListBean.content);
        ImageView ivProject = baseViewHolder.getView(R.id.iv_project);

        Glide.with(baseViewHolder.itemView.getContext()).load(PujingService.PREFIX+PujingService.IMG+basicServiceVoListBean.thumbnailPic).error(R.drawable.ic_no_pic).into(ivProject);

    }
}
