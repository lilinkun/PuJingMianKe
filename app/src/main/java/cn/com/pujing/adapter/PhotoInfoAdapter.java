package cn.com.pujing.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.util.Urls;

public class PhotoInfoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public PhotoInfoAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {

        ImageView imageView = baseViewHolder.getView(R.id.iv);
        Glide.with(getContext())
                .load(Urls.PREFIX + Urls.IMG + s)
                .into(imageView);
    }
}
