package cn.com.pujing.adapter;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.PhotoWall;

public class PhotoWallAdapter extends BaseQuickAdapter<PhotoWall.Data.Record, BaseViewHolder> {

    private Context context;


    public PhotoWallAdapter(int layoutResId, @Nullable List<PhotoWall.Data.Record> data,Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, PhotoWall.Data.Record baseNode) {
        baseViewHolder.setText(R.id.tv_title, baseNode.title);
        baseViewHolder.setText(R.id.tv_content, baseNode.content);
        RecyclerView recyclerView = baseViewHolder.getView(R.id.rv_adapter_photo_wall);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.setAdapter(new PhotoInfoAdapter(R.layout.item_section_content, Arrays.asList(baseNode.photo.split(",")),null));
    }
}
