package cn.com.pujing.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.util.Urls;

public class ShowPhotoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context context;

    public ShowPhotoAdapter(int layoutResId, @Nullable List<String> data,Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
        ImageView ivShowPhoto = baseViewHolder.getView(R.id.iv_show_photo);
        Glide.with(context)
                .load(PujingService.PREFIX + PujingService.IMG + s)
                .apply(new RequestOptions().override(100, 100))
                .into(ivShowPhoto);
    }
}
