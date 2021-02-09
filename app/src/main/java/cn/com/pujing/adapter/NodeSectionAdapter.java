package cn.com.pujing.adapter;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.adapter.provider.RootFooterNodeProvider;
import cn.com.pujing.adapter.provider.RootNodeProvider;
import cn.com.pujing.adapter.provider.SecondNodeProvider;
import cn.com.pujing.datastructure.section.ItemNode;
import cn.com.pujing.datastructure.section.RootFooterNode;
import cn.com.pujing.datastructure.section.RootNode;

public class NodeSectionAdapter extends BaseNodeAdapter {

    public NodeSectionAdapter() {
        super();
        addFullSpanNodeProvider(new RootNodeProvider());
        addNodeProvider(new SecondNodeProvider());
        addFooterNodeProvider(new RootFooterNodeProvider());
        addChildClickViewIds(R.id.iv);
    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> data, int position) {
        BaseNode node = data.get(position);
        if (node instanceof RootNode) {
            return 0;
        } else if (node instanceof ItemNode) {
            return 1;
        } else if (node instanceof RootFooterNode) {
            return 2;
        }
        return -1;
    }
}
