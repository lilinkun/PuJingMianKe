package cn.com.pujing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.GridItem;

/**
 * author : liguo
 * date : 2021/3/22 13:51
 * description :
 */
public class HomeItemAdapter extends BaseQuickAdapter<GridItem, BaseViewHolder> {

    public HomeItemAdapter(int layoutResId, @Nullable List<GridItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, GridItem gridItem) {

        baseViewHolder.setText(R.id.tv_home_grid_item,gridItem.getTitle());

    }
}
