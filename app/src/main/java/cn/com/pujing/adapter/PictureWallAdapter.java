package cn.com.pujing.adapter;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.haibin.calendarview.BaseView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.activity.PhotoWallActivity;
import cn.com.pujing.activity.ShowPhotoActivity;
import cn.com.pujing.entity.PictureWallBean;
import cn.com.pujing.widget.ShowImagesDialog;

/**
 * author : liguo
 * date : 2021/4/15 16:03
 * description :
 */
public class PictureWallAdapter extends BaseQuickAdapter<PictureWallBean, BaseViewHolder> implements LoadMoreModule {

    public PictureWallAdapter(int layoutResId, @Nullable List<PictureWallBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, PictureWallBean pictureWallBean) {

        baseViewHolder.setText(R.id.tv_title, pictureWallBean.title);
        baseViewHolder.setText(R.id.tv_create_time, pictureWallBean.createTime);
        if (pictureWallBean.content == null || pictureWallBean.content.trim().toString().length() == 0){
            baseViewHolder.setVisible(R.id.tv_content,false);
        }else {
            baseViewHolder.setText(R.id.tv_content, pictureWallBean.content);
            baseViewHolder.setVisible(R.id.tv_content,true);
        }
        baseViewHolder.setImageResource(R.id.iv_collect,pictureWallBean.isCollent == 1 ? R.mipmap.ic_collect : R.mipmap.ic_uncollect);

        RecyclerView recyclerView = baseViewHolder.getView(R.id.rv_pic_wall);

        List<String> strings = Arrays.asList(pictureWallBean.photo.split(","));
        PhotoInfoAdapter photoInfoAdapter = new PhotoInfoAdapter(R.layout.item_section_content, strings);
        recyclerView.setLayoutManager(new GridLayoutManager(baseViewHolder.itemView.getContext(), 3));
        recyclerView.setAdapter(photoInfoAdapter);

        photoInfoAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (pictureWallBean.photo.split(",").length > 9) {
                    Intent intent = new Intent(baseViewHolder.itemView.getContext(), ShowPhotoActivity.class);
                    intent.putExtra("showphoto", pictureWallBean.photo.split(","));
                    intent.putExtra("pos", position);
                    baseViewHolder.itemView.getContext().startActivity(intent);
                }else {
                    new ShowImagesDialog(baseViewHolder.itemView.getContext(), strings, position, 0).show();
                }
            }
        });

    }
}
