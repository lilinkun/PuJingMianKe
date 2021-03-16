package cn.com.pujing.widget;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import cn.com.pujing.R;

/**
 * author : liguo
 * date : 2021/3/11 18:38
 * description :
 */
public class RestNumDialog extends AlertDialog {

    private OnNumListener onNumListener;
    private Context context;

    public RestNumDialog(@NonNull Context context, OnNumListener onNumListener) {
        super(context);

        this.context = context;

        this.onNumListener = onNumListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_restnum);

        EditText etRestNum = findViewById(R.id.et_num);

        TextView tvSure = findViewById(R.id.tv_sure);

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumListener.onNum(Integer.valueOf(etRestNum.getText().toString()));
            }
        });
    }

    public interface OnNumListener{
        public void onNum(int person_no);
    }
}
