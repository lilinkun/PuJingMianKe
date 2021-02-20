package cn.com.pujing.view;

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

    public FeedbackDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_feedback);

        TextView tvFeedbackSuccessTime = (TextView) findViewById(R.id.tv_feedback_success_time);
        TextView tvKnow = (TextView) findViewById(R.id.tv_know);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");

        tvFeedbackSuccessTime.setText(simpleDateFormat.format(new Date()));

        tvKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
