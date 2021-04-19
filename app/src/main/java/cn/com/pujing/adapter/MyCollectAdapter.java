package cn.com.pujing.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.CollectBean;
import cn.com.pujing.http.PujingService;

/**
 * author : liguo
 * date : 2021/4/16 16:41
 * description :
 */
public class MyCollectAdapter extends BaseQuickAdapter<CollectBean, BaseViewHolder> implements LoadMoreModule {
    public MyCollectAdapter(int layoutResId, @Nullable List<CollectBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CollectBean collectBean) {
        baseViewHolder.setText(R.id.tv_collect_time,collectBean.createTime+"");
        baseViewHolder.setText(R.id.tv_title,collectBean.title);
        ImageView ivMyCollect = baseViewHolder.getView(R.id.iv_mycollect);

        String url = "";

        if (collectBean.photo.contains(",")){
            url = collectBean.photo.split(",")[0];
        }else {
            url = collectBean.photo;
        }

        Glide.with(baseViewHolder.itemView.getContext()).load(PujingService.PREFIX + PujingService.IMG + url)
                .error(R.drawable.ic_no_pic).into(ivMyCollect);

    }
}
