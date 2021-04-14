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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import cn.com.pujing.R;
import cn.com.pujing.util.PuJingUtils;
import cn.com.pujing.util.UToast;

public class AddThingsDialogFragment extends DialogFragment implements View.OnClickListener {
    private OnDialogListener onDialogListener;
    private TextView startTimeTextView;
    private TextView endTimeTextView;
    private TextView limitTimeTextView;
    private TextView limitTextView;
    private EditText editText;
    private RelativeLayout rlRemainDate;
    private static final int MAX_NUM = 50;
    private String startTime;
    private String endTime;
    private String remainTime;
    private int type = 0;

    public AddThingsDialogFragment(OnDialogListener onDialogListener,int type) {
        this.onDialogListener = onDialogListener;
        this.type = type;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogFullScreen); //dialog全屏
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

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
        rlRemainDate = view.findViewById(R.id.rl_remain_date);
        editText = view.findViewById(R.id.et_add_thing);
        view.findViewById(R.id.rl_start_date).setOnClickListener(this);
        view.findViewById(R.id.rl_end_date).setOnClickListener(this);
        rlRemainDate.setOnClickListener(this);
        limitTextView = view.findViewById(R.id.tv_add_limit_text);
        editText.addTextChangedListener(watcher);

        if (type == 1){
            rlRemainDate.setVisibility(View.GONE);
        }

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
                        onDialogListener.onDialogClick(startTimeTextView.getText().toString(), endTimeTextView.getText().toString(), editText.getText().toString(),limitTimeTextView.getText().toString());
                    }
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "结束事件必须大于开始时间", Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(getActivity(), "请填写事项", Toast.LENGTH_LONG).show();
            }


        } else if (id == R.id.rl_start_date) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    startTime = String.format("%02d:%02d", hourOfDay, minute);
                    startTimeTextView.setText(startTime);
                    if (endTimeTextView.getText().toString().trim().length() > 0) {
                        if (!PuJingUtils.checkTimeRange(startTime, endTimeTextView.getText().toString(), "HH:mm")) {
                            endTimeTextView.setText("");
                        }
                    }
                }
            }, 0, 0, true);
            timePickerDialog.show();
        } else if (id == R.id.rl_end_date) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    endTime = String.format("%02d:%02d", hourOfDay, minute);
                    if (PuJingUtils.checkTimeRange(startTime, endTime, "HH:mm")) {
                        endTimeTextView.setText(endTime);

                        if (limitTimeTextView.getText().toString().trim().length() > 0) {
                            if (!PuJingUtils.checkTimeRange(limitTimeTextView.getText().toString(),endTime , "HH:mm")) {
                                limitTimeTextView.setText("");
                            }
                        }
                    }else {
                        UToast.show(getActivity(),"结束事件必须大于开始时间");
                    }

                }
            }, 0, 0, true);
            timePickerDialog.show();
        } else if (id == R.id.rl_remain_date){
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    remainTime = String.format("%02d:%02d", hourOfDay, minute);
                    if (PuJingUtils.checkTimeRange(remainTime,endTime, "HH:mm")) {
                        limitTimeTextView.setText(remainTime);
                    }else {
                        UToast.show(getActivity(),"提醒时间必须小于结束事件");
                    }
                }
            }, 0, 0, true);
            timePickerDialog.show();
        }
    }

    public interface OnDialogListener {
        void onDialogClick(String startTime, String endTime, String content,String remainTime);
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
