package cn.com.pujing.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.base.BasePresenter;

public class ForgetPwdFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.tv_get_captcha)
    TextView tvGetCaptcha;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.et_cell_phone_number)
    EditText etCellPhoneNumber;
    @BindView(R.id.et_captcha)
    EditText etCaptcha;
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_forget_pwd;
    }

    @Override
    public void initEventAndData() {
    }


    @Override
    @OnClick({R.id.tv_get_captcha,R.id.tv_submit})
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.tv_get_captcha) {

        } else if (id == R.id.tv_submit) {
            String cellPhoneNumber = etCellPhoneNumber.getText().toString().trim();
            String captcha = etCaptcha.getText().toString().trim();
            String newPwd = etNewPwd.getText().toString().trim();
        }
    }
}
