package cn.com.pujing.entity;

import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * author : liguo
 * date : 2021/4/7 16:15
 * description :
 */
public class BasicServiceVoListBean extends BaseNode {
    public int id;
    public String name;
    public int type;
    public String content;

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }
}
