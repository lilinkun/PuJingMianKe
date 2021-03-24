package cn.com.pujing.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import cn.com.pujing.R;
import cn.com.pujing.activity.RanquetsOrderActivity;
import cn.com.pujing.util.ActivityUtil;

/**
 * author : liguo
 * date : 2021/3/7 15:16
 * description :下单结果
 */
public class OnAccountDialog extends Dialog {
    private double orderPrice;
    private String orderNumber;
    private Context context;
    private int type;

    public OnAccountDialog(@NonNull Context context,double price,int type,String orderNumber) {
        super(context);
        this.orderPrice = price;
        this.orderNumber = orderNumber;
        this.context = context;
        this.type = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_on_account);

        setCancelable(false);

        TextView tvSuccessInfo = findViewById(R.id.tv_success_info);
        tvSuccessInfo.setText("下单成功");
//        tvSuccessInfo.setText("挂账成功，费用" + orderPrice + "元将计入月末账单统一管理");


        TextView tvBackHome = findViewById(R.id.tv_back_home);

        tvBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                ActivityUtil.finishHomeAll();
            }
        });

        TextView tvSearchOrder = findViewById(R.id.tv_search_order);
        tvSearchOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Intent intent = new Intent(context,RanquetsOrderActivity.class);
                intent.putExtra("ordernumber",orderNumber);
                intent.putExtra("type",type);
                context.startActivity(intent);

                ActivityUtil.finishHomeAll();
            }
        });
    }
}
