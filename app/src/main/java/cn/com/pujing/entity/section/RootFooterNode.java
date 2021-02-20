package cn.com.pujing.entity.section;

import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RootFooterNode extends BaseNode {
    public int pos;

    public RootFooterNode(int pos) {
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }
}
