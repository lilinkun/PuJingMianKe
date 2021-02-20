package cn.com.pujing.entity.section;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.entity.node.NodeFooterImp;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RootNode extends BaseExpandNode implements NodeFooterImp {
    private List<BaseNode> childNode;
    public String title;
    public String content;
    public int pos;

    public RootNode(List<BaseNode> childNode, String title, String content,int pos) {
        this.childNode = childNode;
        this.title = title;
        this.content = content;
        this.pos = pos;

        if (this.childNode != null && this.childNode.size() > 9) {
            ItemNode itemNode = (ItemNode) this.childNode.get(8);
            itemNode.showMore = true;
        }
    }


    /**
     * {@link BaseNode}
     * 重写此方法，获取子节点。如果没有子节点，返回 null 或者 空数组
     *
     * @return child nodes
     */
    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return childNode;
    }

    /**
     * {@link NodeFooterImp}
     * （可选实现）
     * 重写此方法，获取脚部节点
     *
     * @return
     */
    @Nullable
    @Override
    public BaseNode getFooterNode() {
        return new RootFooterNode(pos);
    }
}
