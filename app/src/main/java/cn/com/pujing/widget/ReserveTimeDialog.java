package cn.com.pujing.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import java.util.Calendar;

import cn.com.pujing.R;
import cn.com.pujing.util.UToast;

/**
 * author : liguo
 * date : 2021/3/11 16:11
 * description :
 */
public class ReserveTimeDialog extends AlertDialog implements NumberPicker.Formatter, NumberPicker.OnValueChangeListener, NumberPicker.OnScrollListener {

    private Context context;
    private String min;
    private String max;
    private OnTimeListenr onListener;
    private NumberPicker hourPicker;
    private NumberPicker minutePicker;
    private int hourInt;
    private int minuteInt;
    private int minHour;
    private int maxHour;
    private int minMinute;
    private int maxMinute;
    private int minMinute1;
    private int maxMinute1;

    public ReserveTimeDialog(@NonNull Context context,String min,String max) {
        super(context);
        this.context = context;
        this.min = min;
        this.max = max;

        String[] hour = min.split(":");
        String[] minute = max.split(":");
        minHour = Integer.valueOf(hour[0]);
        maxHour = Integer.valueOf(minute[0]);
        minMinute = Integer.valueOf(hour[1]);
        maxMinute = 59;
        minMinute1 = 0;
        maxMinute1 = Integer.valueOf(minute[1]);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_reserve_time);

        hourPicker=(NumberPicker) findViewById(R.id.hourpicker);
        minutePicker=(NumberPicker) findViewById(R.id.minuteicker);

        TextView tvSure = findViewById(R.id.tv_sure);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onListener.getTimeData(String.format("%02d:%02d", hourInt, minuteInt));
                dismiss();
            }
        });


        hourPicker.setFormatter(this);
        hourPicker.setOnValueChangedListener(this);
        hourPicker.setOnScrollListener(this);
        hourPicker.setMaxValue(maxHour);
        hourPicker.setMinValue(minHour);

        minutePicker.setFormatter(this);
        minutePicker.setOnValueChangedListener(this);
        minutePicker.setOnScrollListener(this);
        minutePicker.setMaxValue(maxMinute);
        minutePicker.setMinValue(minMinute);
        minutePicker.setValue(minMinute);
        minuteInt = minMinute;
        hourInt = minHour;
    }

    @Override
    public String format(int value) {
        String tmpStr = String.valueOf(value);
        if (value < 10) {
            tmpStr = "0" + tmpStr;
        }
        return tmpStr;
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        switch (picker.getId()){
            case R.id.hourpicker:
                hourInt = newVal;
                if (hourInt == minHour){
                    minutePicker.setMaxValue(maxMinute);
                    minutePicker.setMinValue(minMinute);
                }else if (hourInt == maxHour){
                    minutePicker.setMaxValue(maxMinute1);
                    minutePicker.setMinValue(minMinute1);
                }else {
                    minutePicker.setMaxValue(60);
                    minutePicker.setMinValue(0);
                }

                break;

            case R.id.minuteicker:
                minuteInt = newVal;
                break;
        }
//        onListener.getTimeData(newVal);
    }

    @Override
    public void onScrollStateChange(NumberPicker view, int scrollState) {
        switch (scrollState) {
            case NumberPicker.OnScrollListener.SCROLL_STATE_FLING:
//                Toast.makeText(context, "后续滑动(飞呀飞，根本停下来)", Toast.LENGTH_LONG)
//                        .show();
                break;
            case NumberPicker.OnScrollListener.SCROLL_STATE_IDLE:
//                Toast.makeText(context, "不滑动", Toast.LENGTH_LONG).show();

                break;
            case NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
//                Toast.makeText(context, "滑动中", Toast.LENGTH_LONG)
//                        .show();
                break;
        }
    }

    public void setOnListener(OnTimeListenr onListener){
        this.onListener = onListener;
    }

    public interface OnTimeListenr{
        public void getTimeData(String time);
    }

}
