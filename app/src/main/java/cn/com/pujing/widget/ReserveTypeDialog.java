package cn.com.pujing.widget;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import cn.com.pujing.R;

/**
 * author : liguo
 * date : 2021/3/15 10:55
 * description :
 */
public class ReserveTypeDialog extends AlertDialog {

    private OnItemChoose onItemChoose;
    private int orderType;

    public ReserveTypeDialog(@NonNull Context context,OnItemChoose onItemChoose,int orderType) {
        super(context);
        this.onItemChoose = onItemChoose;
        this.orderType = orderType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_reserve_type);

        RelativeLayout rlRightnow = findViewById(R.id.rl_rightnow);
        RelativeLayout rlReserve = findViewById(R.id.rl_reserve);
        ImageView ivRightnow = findViewById(R.id.iv_rightnow);
        ImageView ivReserve = findViewById(R.id.iv_reserve);

        if (orderType == 1){
            ivRightnow.setSelected(true);
            ivReserve.setSelected(false);
        }else {
            ivRightnow.setSelected(false);
            ivReserve.setSelected(true);
        }


        rlRightnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemChoose.onType(1);
                ivRightnow.setSelected(true);
                ivReserve.setSelected(false);
                dismiss();
            }
        });

        rlReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemChoose.onType(2);
                ivRightnow.setSelected(false);
                ivReserve.setSelected(true);
                dismiss();
            }
        });

    }

    public interface OnItemChoose{
        public void onType(int type);
    }

}
