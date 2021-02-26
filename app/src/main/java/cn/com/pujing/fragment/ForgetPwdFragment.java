package cn.com.pujing.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.presenter.ForgetPwdPresenter;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.ForgetPwdView;

public class ForgetPwdFragment extends BaseFragment<ForgetPwdView, ForgetPwdPresenter> implements View.OnClickListener,ForgetPwdView {

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
    @BindView(R.id.et_sure_new_pwd)
    EditText etSureNewPwd;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_forget_pwd;
    }

    @Override
    public void initEventAndData() {
    }

    @Override
    protected ForgetPwdPresenter createPresenter() {
        return new ForgetPwdPresenter();
    }


    @Override
    @OnClick({R.id.tv_get_captcha,R.id.tv_submit})
    public void onClick(View v) {
        int id = v.getId();

        String cellPhoneNumber = etCellPhoneNumber.getText().toString().trim();
        String captcha = etCaptcha.getText().toString().trim();
        String newPwd = etNewPwd.getText().toString().trim();
        String sureNewPwd = etSureNewPwd.getText().toString().trim();
        if (id == R.id.tv_get_captcha) {

            if (cellPhoneNumber.length() > 0){

                mPresenter.sendVCode(cellPhoneNumber,true);

            }else {
                UToast.show(getActivity(),getString(R.string.input_phone_tip));
            }


        } else if (id == R.id.tv_submit) {

            if (cellPhoneNumber.length() > 0 && captcha.length() > 0 && newPwd.length() > 0 && sureNewPwd.length() > 0){

                if (newPwd.equals(sureNewPwd)){
                    mPresenter.modifyPwd(cellPhoneNumber,newPwd,captcha);
                }else {
                    UToast.show(getActivity(),"两次密码不一致");
                }

            }else {
                UToast.show(getActivity(),"信息请填完整");
            }

        }
    }

    @Override
    public void sendSmsSuccess(String vCode) {
        etCaptcha.setText(vCode + "");
    }

    @Override
    public void modifyPwdSuccess(boolean result) {
        UToast.show(getActivity(), "修改成功");
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(getActivity(), msg);
    }
}
