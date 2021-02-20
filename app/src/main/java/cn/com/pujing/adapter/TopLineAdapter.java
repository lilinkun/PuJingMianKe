package cn.com.pujing.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.util.BannerUtils;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.NotifyInfo;

/**
 * 自定义布局，实现类似1号店、淘宝头条的滚动效果
 */
public class TopLineAdapter extends BannerAdapter<NotifyInfo.Data.Record, TopLineAdapter.TopLineHolder> {

    public TopLineAdapter(List<NotifyInfo.Data.Record> mDatas) {
        super(mDatas);
    }

    @Override
    public TopLineHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new TopLineHolder(BannerUtils.getView(parent, R.layout.item_top_line));
    }

    @Override
    public void onBindView(TopLineHolder holder, NotifyInfo.Data.Record data, int position, int size) {
        holder.contentTextView.setText(data.title);
    }

    class TopLineHolder extends RecyclerView.ViewHolder {
        public TextView contentTextView;

        public TopLineHolder(@NonNull View view) {
            super(view);
            contentTextView = view.findViewById(R.id.tv_content);
        }
    }
}
