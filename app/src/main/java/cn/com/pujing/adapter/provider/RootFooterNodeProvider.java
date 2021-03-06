package cn.com.pujing.adapter.provider;

import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import cn.com.pujing.R;
import cn.com.pujing.entity.section.RootFooterNode;

public class RootFooterNodeProvider extends BaseNodeProvider {

    public RootFooterNodeProvider() {
//        addChildClickViewIds(R.id.iv_download);
//        addChildClickViewIds(R.id.iv_collect);
//        addChildClickViewIds(R.id.iv_share);
    }

    @Override
    public int getItemViewType() {
        return 2;
    }

    @Override
    public int getLayoutId() {
        return R.layout.node_footer;
    }

    @Override
    public void convert(@NotNull BaseViewHolder helper, @Nullable BaseNode data) {
        RootFooterNode rootFooterNode = (RootFooterNode) data;

        helper.setImageResource(R.id.iv_collect,rootFooterNode.isCollect ? R.mipmap.ic_collect : R.mipmap.ic_uncollect);

    }

    @Override
    public void onChildClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        Log.v("LG","position"+position);
    }


}
