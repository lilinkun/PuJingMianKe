package cn.com.pujing.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.BanquetBean;
import cn.com.pujing.presenter.RestBanquetsPresenter;
import cn.com.pujing.view.RestBanquetsView;

/**
 * author : liguo
 * date : 2021/3/1 18:46
 * description :
 */
public class RestBanquetsActivity extends BaseActivity<RestBanquetsView, RestBanquetsPresenter> implements RestBanquetsView {

    @BindView(R.id.tv_banquets_tip)
    TextView tvBanquetsTip;
    @BindView(R.id.iv_banquets_back)
    ImageView ivBanquetsBack;

    @Override
    public int getLayoutId() {
        return R.layout.activity_rest_banquets;
    }

    @Override
    public void init() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");
        Calendar calendar = Calendar.getInstance();
     calendar.add(Calendar.DATE,2);

        String limitDate = simpleDateFormat.format(calendar.getTime());

        if (limitDate.startsWith("0")){
            limitDate = limitDate.substring(1,limitDate.length());
        }

        tvBanquetsTip.setText("当前可预订"+ limitDate +"以后的宴会");

        mPresenter.getBanquetsData(2);

    }

    @Override
    protected RestBanquetsPresenter createPresenter() {
        return new RestBanquetsPresenter();
    }

    @OnClick({R.id.iv_banquets_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_banquets_back:

                finish();

                break;
        }
    }

    @Override
    public void getBanquetSuccess(List<BanquetBean> banquetBeanList) {

    }

    @Override
    public void getDataFail(String msg) {

    }
}
