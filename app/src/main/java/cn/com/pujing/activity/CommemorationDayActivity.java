package cn.com.pujing.activity;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.CommemorationDayAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.CommemorationDayBean;
import cn.com.pujing.fragment.AddThingsDialogFragment;
import cn.com.pujing.presenter.CommemorationDayPresenter;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.CommemorationDayView;

/**
 * author : liguo
 * date : 2021/4/13 9:30
 * description :
 */
public class CommemorationDayActivity extends BaseActivity<CommemorationDayView, CommemorationDayPresenter> implements CommemorationDayView, AddThingsDialogFragment.OnDialogListener {

    private int page = 1;
    private CommemorationDayAdapter commemorationDayAdapter;

    @BindView(R.id.rv_commemoration_day)
    RecyclerView rvCommemorationDay;
    @BindView(R.id.tv_add_commemoration_day)
    TextView tvAddCommemorationDay;

    @Override
    public int getLayoutId() {
        return R.layout.activity_commemoration_day;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        commemorationDayAdapter = new CommemorationDayAdapter(R.layout.adapter_commemoration_day,null);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rvCommemorationDay.setLayoutManager(linearLayoutManager);

        rvCommemorationDay.setAdapter(commemorationDayAdapter);

        mPresenter.getCommemorationDay(page);

    }

    @Override
    protected CommemorationDayPresenter createPresenter() {
        return new CommemorationDayPresenter();
    }

    @Override
    public void getCommemorationDaySuccess(CommemorationDayBean commemorationDayBean) {
        commemorationDayAdapter.setNewInstance(commemorationDayBean.records);
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(this,msg);
    }

    @OnClick({R.id.iv_commemoration_day_back,R.id.tv_add_commemoration_day})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_commemoration_day_back:

                finish();

                break;

            case R.id.tv_add_commemoration_day:

                AddThingsDialogFragment addThingsDialogFragment = new AddThingsDialogFragment(this,1);
                addThingsDialogFragment.show(getSupportFragmentManager(), "");

                break;

            default:

                break;
        }
    }

    @Override
    public void onDialogClick(String startTime, String endTime, String content, String remainTime) {

    }
}
