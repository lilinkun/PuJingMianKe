package cn.com.pujing.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.MyInfoBean;
import cn.com.pujing.presenter.ModifyPersonalInfoPresenter;
import cn.com.pujing.util.PuJingUtils;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.ModifyPersonalInfoView;

/**
 * author : liguo
 * date : 2021/2/25 11:12
 * description :修改个人信息
 */
public class ModifyPersonalInfoActivity extends BaseActivity<ModifyPersonalInfoView, ModifyPersonalInfoPresenter> implements ModifyPersonalInfoView {

    // 1,昵称 2，个性签名 3，出生日期 4，房号
    private int modifytype;
    private MyInfoBean myInfoBean;

    @BindView(R.id.tv_modify_personalinfo_title)
    TextView tvModifyPersonalinfoTitle;
    @BindView(R.id.et_modify)
    EditText etModify;
    @BindView(R.id.tv_modify_date)
    TextView tvModifyDate;
    @BindView(R.id.tv_choose_date)
    TextView tvChooseDate;

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_personalinfo;
    }

    @Override
    public void init() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();
        
        modifytype = getIntent().getIntExtra("modifyType",0);
        myInfoBean = (MyInfoBean) getIntent().getSerializableExtra("personalinfo");

        if (modifytype == 1){
            tvModifyPersonalinfoTitle.setText("修改昵称");
        }else if (modifytype == 2){
            tvModifyPersonalinfoTitle.setText("编辑个人签名");
        }else if (modifytype == 3){
            tvModifyPersonalinfoTitle.setText("选择出生日期");
            tvModifyDate.setVisibility(View.VISIBLE);
            tvChooseDate.setVisibility(View.VISIBLE);
            etModify.setVisibility(View.GONE);

            tvModifyDate.setText(myInfoBean.getBirthday());

        } else if (modifytype == 4){
            tvModifyPersonalinfoTitle.setText("编辑房号");
        }

    }

    @Override
    protected ModifyPersonalInfoPresenter createPresenter() {
        return new ModifyPersonalInfoPresenter();
    }

    @OnClick({R.id.tv_modify_personalinfo_save,R.id.iv_modify_personalinfo,R.id.tv_choose_date})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_modify_personalinfo_save:

                if(!PuJingUtils.isFastDoubleClick()) {

                    if (modifytype == 1) {
                        mPresenter.modifyPersonalInfo(etModify.getText().toString(), "", "", "");
                    } else if (modifytype == 2) {
                        mPresenter.modifyPersonalInfo("", etModify.getText().toString(), "", "");
                    } else if (modifytype == 4) {
                        mPresenter.modifyPersonalInfo("", "", "", etModify.getText().toString());
                    } else if (modifytype == 3){
                        mPresenter.modifyPersonalInfo("", "", tvModifyDate.getText().toString(), "");
                    }
                }

                break;

            case R.id.iv_modify_personalinfo:

                finish();

                break;

            case R.id.tv_choose_date:


                Calendar c = Calendar.getInstance();
                Dialog dialog = new DatePickerDialog(
                        this,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
//                                et.setText("您选择了：" + year + "年" + (month+1) + "月" + dayOfMonth + "日");
//                                tvModifyDate.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                                tvModifyDate.setText(year + "-" + String.format("%02d-%02d",(month+1),dayOfMonth));
                            }
                        },
                        c.get(Calendar.YEAR), // 传入年份
                        c.get(Calendar.MONTH), // 传入月份
                        c.get(Calendar.DAY_OF_MONTH) // 传入天数
                );

                dialog.show();

                break;
        }
    }

    @Override
    public void modifyPersonalInfoSuccess(MyInfoBean myInfoBean) {

        Intent intent = new Intent();
        intent.putExtra("myinfo",myInfoBean);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void modifyFail(String msg) {
        UToast.show(this,msg);
    }
}
