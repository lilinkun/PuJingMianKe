package cn.com.pujing.activity;

import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import cn.com.pujing.R;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.base.BasePresenter;
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

    }

    @Override
    protected RestBanquetsPresenter createPresenter() {
        return new RestBanquetsPresenter();
    }
}
