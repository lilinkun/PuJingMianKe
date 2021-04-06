package cn.com.pujing.adapter.provider;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import cn.com.pujing.R;
import cn.com.pujing.entity.section.RootNode;

public class RootNodeProvider extends BaseNodeProvider {

    @Override
    public int getItemViewType() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.def_section_head;
    }

    @Override
    public void convert(@NotNull BaseViewHolder helper, @Nullable BaseNode data) {
        RootNode rootNode = (RootNode) data;
        helper.setText(R.id.tv_title, rootNode.title);
        if (rootNode.content == null || rootNode.content.trim().toString().length() == 0){
            helper.setVisible(R.id.tv_content,false);
        }else {
            helper.setText(R.id.tv_content, rootNode.content);
            helper.setVisible(R.id.tv_content,true);
        }
    }
}
