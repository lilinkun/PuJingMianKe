package cn.com.pujing.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

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

    OnClickListener onClickListener;

    public PictureWallAdapter(int layoutResId, @Nullable List<PictureWallBean> data,OnClickListener onClickListener) {
        super(layoutResId, data);
        this.onClickListener = onClickListener;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, PictureWallBean pictureWallBean) {

        ImageView ivShare = baseViewHolder.getView(R.id.iv_share);
        ImageView ivCollect = baseViewHolder.getView(R.id.iv_collect);

        baseViewHolder.setText(R.id.tv_title, pictureWallBean.title);
        baseViewHolder.setText(R.id.tv_create_time, pictureWallBean.createTime);
        baseViewHolder.setText(R.id.tv_collect_num, pictureWallBean.favoriteNumber+"");
        baseViewHolder.setText(R.id.tv_give_like_num, pictureWallBean.likeNumber+"");
        if (pictureWallBean.content == null || pictureWallBean.content.trim().toString().length() == 0){
            baseViewHolder.setVisible(R.id.tv_content,false);
        }else {
            baseViewHolder.setText(R.id.tv_content, pictureWallBean.content);
            baseViewHolder.setVisible(R.id.tv_content,true);
        }
        baseViewHolder.setImageResource(R.id.iv_collect,pictureWallBean.isCollent == 1 ? R.mipmap.ic_collect : R.mipmap.ic_uncollect);
        baseViewHolder.setImageResource(R.id.iv_share,pictureWallBean.islike == 1 ? R.mipmap.ic_give_likes : R.mipmap.ic_ungive_likes);

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

        ivCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pictureWallBean.isCollent == 0) {
                    onClickListener.getCollect(pictureWallBean.id);
                }else {
                    onClickListener.getUnCollect(pictureWallBean.id);
                }
            }
        });

        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pictureWallBean.islike == 0) {
                    onClickListener.getLike(pictureWallBean.id);
                }else {
                    onClickListener.getUnLike(pictureWallBean.id);
                }
            }
        });

    }

    public interface OnClickListener{
        public void getCollect(int id);

        public void getUnCollect(int id);

        public void getLike(int id);

        public void getUnLike(int id);
    }

}
