package cn.com.pujing.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
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
import cn.com.pujing.widget.CommemorationDayDialog;

/**
 * author : liguo
 * date : 2021/4/13 9:30
 * description :
 */
public class CommemorationDayActivity extends BaseActivity<CommemorationDayView, CommemorationDayPresenter> implements CommemorationDayView, CommemorationDayDialog.OnDialogCDListener, CommemorationDayAdapter.SetDayListener {

    private int page = 1;
    private CommemorationDayAdapter commemorationDayAdapter;
    private CommemorationDayBean commemorationDayBean;

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

        commemorationDayAdapter = new CommemorationDayAdapter(R.layout.adapter_commemoration_day,null,this);

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
        this.commemorationDayBean = commemorationDayBean;
        commemorationDayAdapter.setNewInstance(commemorationDayBean.records);
    }

    @Override
    public void saveCommemorationDaySuccess(Object commemorationDayBean) {
        page = 1;
        mPresenter.getCommemorationDay(page);
    }

    @Override
    public void deleteCommemorationDaySuccess(Object commemorationDayBean) {
        UToast.show(this,"删除成功");
        page = 1;
        mPresenter.getCommemorationDay(page);
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

                CommemorationDayDialog commemorationDayDialog = new CommemorationDayDialog(this,this);
                commemorationDayDialog.setView(new EditText(this));
                commemorationDayDialog.show();

                break;

            default:

                break;
        }
    }

    @Override
    public void onDialogClick(String date, String content) {
        mPresenter.addCommemorationDay(date,content);
    }

    @Override
    public void setDelete(int position) {

        View vipView = LayoutInflater.from(this).inflate(R.layout.dialog_delete_commemoration_day,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(vipView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        TextView cancelView = vipView.findViewById(R.id.tv_exit);
        TextView sureView = vipView.findViewById(R.id.tv_choose);

        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        sureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                mPresenter.deleteCommemorationDay(commemorationDayBean.records.get(position).id+"");
            }
        });
    }

    @Override
    public void setEdit(int position) {

        CommemorationDayDialog commemorationDayDialog = new CommemorationDayDialog(this,this);
        commemorationDayDialog.setView(new EditText(this));
        commemorationDayDialog.setEdit(commemorationDayBean.records.get(position));
        commemorationDayDialog.show();
    }
}
