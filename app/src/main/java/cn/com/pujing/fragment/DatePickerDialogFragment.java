package cn.com.pujing.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.com.pujing.R;

public class DatePickerDialogFragment extends DialogFragment {
    private OnDialogListener onDialogListener;

    public DatePickerDialogFragment(OnDialogListener onDialogListener) {
        this.onDialogListener = onDialogListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_date_picker, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        DatePicker datePicker = view.findViewById(R.id.dp);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar tempCalendar = Calendar.getInstance();
                tempCalendar.set(Calendar.YEAR, year);
                tempCalendar.set(Calendar.MONTH, monthOfYear);
                tempCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String string = simpleDateFormat.format(tempCalendar.getTime());

                if (onDialogListener != null) {
                    onDialogListener.onDialogClick(string);
                }
                dismiss();
            }
        });
    }

    public interface OnDialogListener {
        void onDialogClick(String string);
    }
}
