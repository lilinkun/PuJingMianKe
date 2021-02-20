package cn.com.pujing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.DraggableModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.GridItem;

public class GridAdapter extends BaseQuickAdapter<GridItem, BaseViewHolder> implements DraggableModule {

    public GridAdapter(int layoutResId, @Nullable List<GridItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, GridItem gridItem) {
        baseViewHolder.setImageResource(R.id.iv, gridItem.imageRes);
//        baseViewHolder.setText(R.id.tv, gridItem.title);
    }

}
