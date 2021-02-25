package cn.com.pujing.entity.section;

import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RootFooterNode extends BaseNode {
    public int pos;
    public boolean isCollect;

    public RootFooterNode(int pos,boolean isCollect) {
        this.pos = pos;
        this.isCollect = isCollect;
    }

    public int getPos() {
        return pos;
    }

    public boolean isCollect() {
        return isCollect;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }
}
