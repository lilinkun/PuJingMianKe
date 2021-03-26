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

/**
 * author : liguo
 * date : 2021/3/26 13:09
 * description :
 */
public class RightAndInterestsDialog extends Dialog {

    public RightAndInterestsDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_health_center);

    }
}
