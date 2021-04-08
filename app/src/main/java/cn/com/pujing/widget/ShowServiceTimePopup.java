package cn.com.pujing.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.activity.LifeTypeActivity;
import cn.com.pujing.adapter.ShowServiceTimeAdapter;
import cn.com.pujing.entity.LifeTypeBean;
import cn.com.pujing.entity.ServicePutawayManageTimeBean;

/**
 * author : liguo
 * date : 2021/4/8 15:30
 * description :
 */
public class ShowServiceTimePopup  extends PopupWindow {

    private View conentView;
    Context context;
    private List<ServicePutawayManageTimeBean> servicePutawayManageTimeBeans;


    public ShowServiceTimePopup(Context context, List<ServicePutawayManageTimeBean> servicePutawayManageTimeBeans){
        this.servicePutawayManageTimeBeans = servicePutawayManageTimeBeans;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.pop_show_reserve_time, null);
        this.context = context;
        this.setContentView(conentView);
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        this.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);

        initData();

    }

    private void initData(){

        RecyclerView recyclerView = conentView.findViewById(R.id.rv_show_reserve_time);
        LinearLayout llShowReserve = conentView.findViewById(R.id.ll_show_reserve);

        ShowServiceTimeAdapter showReserveTimeAdapter = new ShowServiceTimeAdapter(R.layout.adapter_pop_show_reserve_time,servicePutawayManageTimeBeans);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(showReserveTimeAdapter);

        showReserveTimeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (Integer.valueOf(servicePutawayManageTimeBeans.get(position).number) != 0){
                    showReserveTimeAdapter.setPosClick(position);
                    LifeTypeActivity.ClickServicePos = position;
                    dismiss();
                }
            }
        });

        llShowReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

}
