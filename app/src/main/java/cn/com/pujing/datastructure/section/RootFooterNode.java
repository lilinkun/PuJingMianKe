package cn.com.pujing.datastructure.section;

import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RootFooterNode extends BaseNode {
    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }
}
