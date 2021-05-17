package cn.com.pujing.widget;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import java.util.Calendar;

import cn.com.pujing.R;
import cn.com.pujing.entity.CommemorationDayBean;
import cn.com.pujing.util.UToast;

/**
 * author : liguo
 * date : 2021/4/14 13:57
 * description :
 */
public class CommemorationDayDialog extends AlertDialog implements View.OnClickListener {

    private OnDialogCDListener onDialogCDListener;
    private TextView tvDateValue;
    private EditText etAddThing;
    private Context context;
    private CommemorationDayBean.CommemorationDay commemorationDay;
    private int type;

    public CommemorationDayDialog(@NonNull Context context, OnDialogCDListener onDialogCDListener,int type) {
        super(context);
        this.context = context;
        this.onDialogCDListener = onDialogCDListener;
        this.type = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_commemoration_day);

        initView();

    }

    public void setEdit(CommemorationDayBean.CommemorationDay commemorationDay){
        this.commemorationDay = commemorationDay;
    }

    private void initView(){
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.tv_add).setOnClickListener(this);
        findViewById(R.id.rl_date).setOnClickListener(this);

        tvDateValue = findViewById(R.id.tv_date_value);
        etAddThing = findViewById(R.id.et_add_thing);

        if (commemorationDay != null){

            tvDateValue.setText(commemorationDay.commemorationDay);
            etAddThing.setText(commemorationDay.commemorationName);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                dismiss();
                break;

            case R.id.tv_add:
//                onDialogCDListener.onDialogClick();
                if (tvDateValue == null || tvDateValue.getText().toString().trim().length() == 0){
                    UToast.show(context,"日期不能为空");
                }else if (etAddThing == null || etAddThing.getText().toString().trim().length() == 0){
                    UToast.show(context,"事项不能为空");
                }else {
                    onDialogCDListener.onDialogClick(tvDateValue.getText().toString(),etAddThing.getText().toString(),type);
                    dismiss();
                }


                break;

            case R.id.rl_date:

                Calendar c = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
                                tvDateValue.setText(year + "-" + String.format("%02d-%02d",(month+1),dayOfMonth));
                            }
                        },
                        // 传入年份
                        c.get(Calendar.YEAR),
                        // 传入月份
                        c.get(Calendar.MONTH),
                        // 传入天数
                        c.get(Calendar.DAY_OF_MONTH)
                );

                datePickerDialog.show();

                break;

            default:

                break;
        }
    }


    public interface OnDialogCDListener{
        void onDialogClick(String date,String content,int type);
    }
}
