package cn.com.pujing.adapter;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.activity.LifeTypeActivity;
import cn.com.pujing.entity.ServiceBean;

/**
 * author : liguo
 * date : 2021/4/8 9:54
 * description :
 */
public class ServiceTitleAdapter extends BaseQuickAdapter<ServiceBean, BaseViewHolder> {

    private int category = 0;

    public ServiceTitleAdapter(int layoutResId, @Nullable List<ServiceBean> data,int category) {
        super(layoutResId, data);
        this.category = category;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ServiceBean serviceBean) {
            baseViewHolder.setText(R.id.tv_type_name,serviceBean.getServiceType_label());
            RecyclerView recyclerView = baseViewHolder.getView(R.id.rv_service_content);

            ServiceContentAdapter serviceContentAdapter = new ServiceContentAdapter(R.layout.adapter_health_center_content,serviceBean.getBasicServiceVoList());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(baseViewHolder.itemView.getContext());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(serviceContentAdapter);

            serviceContentAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    Intent intent = new Intent();
                    intent.setClass(baseViewHolder.itemView.getContext(), LifeTypeActivity.class);
                    intent.putExtra("basicservicevolistbean",serviceBean.getBasicServiceVoList().get(position));
                    intent.putExtra("category",category);
                    baseViewHolder.itemView.getContext().startActivity(intent);
                }
            });
    }
}
