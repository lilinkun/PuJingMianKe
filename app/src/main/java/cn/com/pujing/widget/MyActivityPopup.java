package cn.com.pujing.widget;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.adapter.GridActivityAdapter;
import cn.com.pujing.adapter.GridOrderAdapter;
import cn.com.pujing.entity.ActivityTypeBean;
import cn.com.pujing.entity.OrderTpeBean;
import cn.com.pujing.util.UToast;

/**
 * author : liguo
 * date : 2021/4/13 15:51
 * description :
 */
public class MyActivityPopup extends PopupWindow {
    private View conentView;
    Context context;
    private MyOrderClickListener myOrderClickListener;
    private long startDate;
    private long endDate;
    private SimpleDateFormat simpleDateFormat;
    private int ClickPos = 0;
    private List<ActivityTypeBean> activityTypeBeans;
    private OrderTpeBean orderTpeBean;
    private OrderTpeBean orderTpeBean1;

    public MyActivityPopup(Context context, MyOrderClickListener myOrderClickListener, List<ActivityTypeBean> activityTypeBeans){
        this.myOrderClickListener = myOrderClickListener;
        this.activityTypeBeans = activityTypeBeans;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.pop_my_activity, null);
        this.context = context;
        this.setContentView(conentView);
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        this.setHeight(WindowManager.LayoutParams.MATCH_PARENT-50);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);

        initData();

    }

    private void initData(){

        RelativeLayout rlStartDate = conentView.findViewById(R.id.rl_start_date);
        RelativeLayout rlEndDate = conentView.findViewById(R.id.rl_end_date);
        TextView tvStartDate = conentView.findViewById(R.id.tv_start_date);
        TextView tvEndDate = conentView.findViewById(R.id.tv_end_date);
        TextView tvCancelPop = conentView.findViewById(R.id.tv_cancel_pop);
        TextView tvSurePop = conentView.findViewById(R.id.tv_sure_pop);

        RecyclerView rvOrderType = conentView.findViewById(R.id.rv_order_type);
        RecyclerView rvActivityType = conentView.findViewById(R.id.rv_activity_type);

        View viewOrder = conentView.findViewById(R.id.view_order);

        startDate = 0;
        endDate = 0;

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDay = simpleDateFormat.format(System.currentTimeMillis());

//        tvStartDate.setText(currentDay);
//        tvEndDate.setText(currentDay);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,4);

        GridActivityAdapter gridOrderAdapter = new GridActivityAdapter(R.layout.adapter_grid_order, OrderTpeBean.activityTpeBeans(),0);

        rvOrderType.setLayoutManager(gridLayoutManager);
        rvOrderType.setAdapter(gridOrderAdapter);
        gridOrderAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                gridOrderAdapter.setTypePos(position);
                ClickPos = position;
                orderTpeBean = OrderTpeBean.activityTpeBeans().get(position);
            }
        });

        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(context,4);

        GridActivityAdapter gridOrderAdapter1 = new GridActivityAdapter(R.layout.adapter_grid_order, OrderTpeBean.activityBeans(activityTypeBeans),-1);

        rvActivityType.setLayoutManager(gridLayoutManager1);
        rvActivityType.setAdapter(gridOrderAdapter1);
        gridOrderAdapter1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                gridOrderAdapter1.setTypePos(position);
                ClickPos = position;
                orderTpeBean1 =  OrderTpeBean.activityBeans(activityTypeBeans).get(position);
            }
        });

        rlStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDailog(tvStartDate,0);
            }
        });
        rlEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDailog(tvEndDate,1);
            }
        });

        tvCancelPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvStartDate.setText("");
                tvEndDate.setText("");
                orderTpeBean1 = null;
                gridOrderAdapter1.setTypePos(-1);
                gridOrderAdapter.setTypePos(0);
            }
        });

        tvSurePop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startDate != 0 && endDate != 0) {
                    if (startDate <= endDate) {
                        myOrderClickListener.setItemValue(orderTpeBean,orderTpeBean1,tvStartDate.getText().toString(),tvEndDate.getText().toString());
                        dismiss();
                    }else {
                        UToast.show(context,"开始时间不能大于截止时间");
                    }
                }else {
                    myOrderClickListener.setItemValue(orderTpeBean,orderTpeBean1,tvStartDate.getText().toString(),tvEndDate.getText().toString());
                    dismiss();
                }
            }
        });

        viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    private void setDailog(TextView tvDate,int type){
        Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(
                context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {

                        tvDate.setText(year + "-" + String.format("%02d-%02d",(month+1),dayOfMonth));

                        if (type == 1){
                            try {
                                endDate = simpleDateFormat.parse(tvDate.getText().toString()).getTime();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else {
                            try {
                                startDate = simpleDateFormat.parse(tvDate.getText().toString()).getTime();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                // 传入年份
                c.get(Calendar.YEAR),
                // 传入月份
                c.get(Calendar.MONTH),
                // 传入天数
                c.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();
    }


    public void setListener(MyOrderClickListener listener){
        this.myOrderClickListener = listener;
    }


    public interface MyOrderClickListener{
        public void setItemValue(OrderTpeBean orderTpeBean,OrderTpeBean orderTpeBean1,String startDate,String endDate);
    }

}

