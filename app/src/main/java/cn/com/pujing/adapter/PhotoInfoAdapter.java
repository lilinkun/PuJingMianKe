package cn.com.pujing.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.PictureWallBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.util.Urls;

public class PhotoInfoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    PictureWallBean pictureWallBean;

    public PhotoInfoAdapter(int layoutResId, @Nullable List<String> data, PictureWallBean pictureWallBean) {
        super(layoutResId, data);
        this.pictureWallBean = pictureWallBean;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {

        ImageView imageView = baseViewHolder.getView(R.id.iv_pic_wall);
        Glide.with(getContext())
                .load(PujingService.PREFIX + PujingService.IMG + s)
                .into(imageView);
        if (pictureWallBean != null && pictureWallBean.type != null) {
            if (pictureWallBean.type.equals("图片")) {
                baseViewHolder.setVisible(R.id.iv_video, false);
            } else {
                baseViewHolder.setVisible(R.id.iv_video, true);
            }
        }

    }
}
