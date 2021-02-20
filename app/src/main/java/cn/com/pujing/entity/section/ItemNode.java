package cn.com.pujing.entity.section;

import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemNode extends BaseNode {
    public String imgUrl;
    public boolean showMore;
    public int pos;
    public String photo;

    public ItemNode(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }
}
