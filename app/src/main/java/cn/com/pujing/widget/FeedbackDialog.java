package cn.com.pujing.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.com.pujing.R;

public class FeedbackDialog extends Dialog {

    private int type = 0;
    private String timeTip;

    public FeedbackDialog(@NonNull Context context,int type,String timeTip) {
        super(context);
        this.type = type;
        this.timeTip = timeTip;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_feedback);

        TextView tvFeedbackSuccessTime = (TextView) findViewById(R.id.tv_feedback_success_time);
        TextView tvKnow = (TextView) findViewById(R.id.tv_know);
        TextView tvContentTip = (TextView) findViewById(R.id.tv_content_tip);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        if (type == 0) {
            tvContentTip.setText("意见反馈成功！请耐心等待");
            tvFeedbackSuccessTime.setText(simpleDateFormat.format(new Date()));
        }else {
            tvContentTip.setText("预订成功！");
            tvFeedbackSuccessTime.setText("使用时间：" + timeTip);
        }





        tvKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
