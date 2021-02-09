package cn.com.pujing.adapter.provider;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import cn.com.pujing.R;
import cn.com.pujing.util.Urls;
import cn.com.pujing.datastructure.section.ItemNode;

public class SecondNodeProvider extends BaseNodeProvider {

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_section_content;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, @Nullable BaseNode data) {
        ItemNode itemNode = (ItemNode) data;
        ImageView imageView = baseViewHolder.getView(R.id.iv);
        Glide.with(getContext())
                .load(Urls.PREFIX + Urls.IMG + ((ItemNode) data).imgUrl)
                .into(imageView);
        baseViewHolder.setVisible(R.id.tv, itemNode.showMore);
    }

    @Override
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {

    }
}
