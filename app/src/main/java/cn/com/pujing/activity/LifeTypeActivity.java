package cn.com.pujing.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.presenter.LifeTypePresenter;
import cn.com.pujing.view.LifeTypeView;

/**
 * author : liguo
 * date : 2021/3/26 16:20
 * description :
 */
public class LifeTypeActivity extends BaseActivity<LifeTypeView, LifeTypePresenter> implements LifeTypeView {

    @BindView(R.id.tv_reserve_date)
    TextView tvReserveDate;

    @Override
    public int getLayoutId() {
        return R.layout.activity_life_type;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this)
                .statusBarColor(R.color.main_color)
                .fitsSystemWindows(true)
                .init();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String cDay = simpleDateFormat.format(System.currentTimeMillis());

        tvReserveDate.setText(cDay);

    }

    @Override
    protected LifeTypePresenter createPresenter() {
        return new LifeTypePresenter();
    }


    @OnClick({R.id.tv_reserve_order,R.id.ll_reserve_order_time})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_reserve_order:

                Intent intent = new Intent();
                intent.setClass(this,ServiceReserveActivity.class);
                startActivity(intent);

                break;

            case R.id.ll_reserve_order_time:

                Calendar c = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(
                        this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
                                tvReserveDate.setText(year + "-" + String.format("%02d-%02d",(month+1),dayOfMonth));
                            }
                        },
                        // 传入年份
                        c.get(Calendar.YEAR),
                        // 传入月份
                        c.get(Calendar.MONTH),
                        // 传入天数
                        c.get(Calendar.DAY_OF_MONTH)
                );

                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dialog.show();

                break;

            default:
                break;
        }
    }

}
