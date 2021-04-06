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
import cn.com.pujing.adapter.ShowReserveTimeAdapter;
import cn.com.pujing.entity.ReserveDeviceBean;

import static cn.com.pujing.activity.VenueReserveActivity.ClickPos;

/**
 * author : liguo
 * date : 2021/4/6 11:34
 * description :
 */
public class ShowTimePopup extends PopupWindow {

    private View conentView;
    Context context;
    private ReserveDeviceBean reserveDeviceBean;


    public ShowTimePopup(Context context, ReserveDeviceBean reserveDeviceBean){
        this.reserveDeviceBean = reserveDeviceBean;
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

        ShowReserveTimeAdapter showReserveTimeAdapter = new ShowReserveTimeAdapter(R.layout.adapter_pop_show_reserve_time,reserveDeviceBean.timesReserveNumList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(showReserveTimeAdapter);

        showReserveTimeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (Integer.valueOf(reserveDeviceBean.timesReserveNumList.get(position).reserveNum) != 0){
                        showReserveTimeAdapter.setPosClick(position);
                        ClickPos = position;
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
