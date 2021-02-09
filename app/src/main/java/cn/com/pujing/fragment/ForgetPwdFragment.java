package cn.com.pujing.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cn.com.pujing.R;

public class ForgetPwdFragment extends Fragment implements View.OnClickListener {
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_forget_pwd, null);
        }
        init(view);
        return view;
    }

    private void init(View view) {
        view.findViewById(R.id.tv_get_captcha).setOnClickListener(this);
        view.findViewById(R.id.tv_submit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.tv_get_captcha) {

        } else if (id == R.id.tv_submit) {
            String cellPhoneNumber = ((EditText) view.findViewById(R.id.et_cell_phone_number)).getText().toString().trim();
            String captcha = ((EditText) view.findViewById(R.id.et_captcha)).getText().toString().trim();
            String newPwd = ((EditText) view.findViewById(R.id.et_new_pwd)).getText().toString().trim();
        }
    }
}
