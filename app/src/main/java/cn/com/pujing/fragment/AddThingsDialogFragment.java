package cn.com.pujing.fragment;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import cn.com.pujing.R;
import cn.com.pujing.util.PuJingUtils;

public class AddThingsDialogFragment extends DialogFragment implements View.OnClickListener {
    private OnDialogListener onDialogListener;
    private TextView startTimeTextView;
    private TextView endTimeTextView;
    private TextView limitTimeTextView;
    private TextView limitTextView;
    private EditText editText;
    private int MAX_NUM = 50;

    public AddThingsDialogFragment(OnDialogListener onDialogListener) {
        this.onDialogListener = onDialogListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogFullScreen); //dialog全屏
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        //去掉dialog的标题
//        this.getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
//        Window window = this.getDialog().getWindow();
//        //去掉dialog默认的padding
//        window.getDecorView().setPadding(0, 0, 0, 0);
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//        window.setAttributes(lp);

        View view = inflater.inflate(R.layout.dialog_fragment_add_things, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        view.findViewById(R.id.tv_cancel).setOnClickListener(this);
        view.findViewById(R.id.tv_add).setOnClickListener(this);
        startTimeTextView = view.findViewById(R.id.tv_start_time_value);
        endTimeTextView = view.findViewById(R.id.tv_end_time_value);
        limitTimeTextView = view.findViewById(R.id.tv_limit_time_value);
        editText = view.findViewById(R.id.et_add_thing);
        view.findViewById(R.id.iv_start_time).setOnClickListener(this);
        view.findViewById(R.id.iv_end_time).setOnClickListener(this);
        view.findViewById(R.id.iv_limit_time).setOnClickListener(this);
        limitTextView = view.findViewById(R.id.tv_add_limit_text);
        editText.addTextChangedListener(watcher);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.tv_cancel) {
            dismiss();
        } else if (id == R.id.tv_add) {

            if (editText.getText().toString().trim().length() > 0) {

                if (PuJingUtils.checkTimeRange(startTimeTextView.getText().toString(), endTimeTextView.getText().toString(), "HH:mm")) {

                    if (onDialogListener != null) {
                        onDialogListener.onDialogClick(startTimeTextView.getText().toString(), endTimeTextView.getText().toString(), editText.getText().toString());
                    }
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "结束事件必须大于开始时间", Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(getActivity(), "请填写事项", Toast.LENGTH_LONG).show();
            }


        } else if (id == R.id.iv_start_time) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String startTime = String.format("%02d:%02d", hourOfDay, minute);
                    startTimeTextView.setText(startTime);
                }
            }, 0, 0, true);
            timePickerDialog.show();
        } else if (id == R.id.iv_end_time) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String startTime = String.format("%02d:%02d", hourOfDay, minute);
                    endTimeTextView.setText(startTime);
                }
            }, 0, 0, true);
            timePickerDialog.show();
        } else if (id == R.id.iv_limit_time){
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String startTime = String.format("%02d:%02d", hourOfDay, minute);
                    limitTimeTextView.setText(startTime);
                }
            }, 0, 0, true);
            timePickerDialog.show();
        }
    }

    public interface OnDialogListener {
        void onDialogClick(String startTime, String endTime, String content);
    }

    TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //只要编辑框内容有变化就会调用该方法，s为编辑框变化后的内容
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //编辑框内容变化之前会调用该方法，s为编辑框内容变化之前的内容
        }

        @Override
        public void afterTextChanged(Editable s) {
            //编辑框内容变化之后会调用该方法，s为编辑框内容变化后的内容
            if (s.length() > MAX_NUM) {
                s.delete(MAX_NUM, s.length());
            }
            int num = MAX_NUM - s.length();
            limitTextView.setText( num + "");
        }
    };
}
