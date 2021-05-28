package cn.com.pujing.activity;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.MyActivitiesAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.HistoryActivitiesBean;
import cn.com.pujing.entity.PagesBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.presenter.MyActivitiesPresenter;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.MyActivitiesView;

/**
 * author : liguo
 * date : 2021/4/19 14:02
 * description :我的活动
 */
public class MyActivitiesActivity extends BaseActivity<MyActivitiesView, MyActivitiesPresenter> implements MyActivitiesView{

    @BindView(R.id.rv_my_activities)
    RecyclerView rvMyActivities;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private MyActivitiesAdapter myActivitiesAdapter;
    private HistoryActivitiesBean historyActivitiesBean;
    private int result_myactivity = 0x454;
    private int page = 1;
    private List<HistoryActivitiesBean> historyActivitiesBeans;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_activities;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvMyActivities.setLayoutManager(linearLayoutManager);

        myActivitiesAdapter = new MyActivitiesAdapter(R.layout.adapter_myactivities,null);
        rvMyActivities.setAdapter(myActivitiesAdapter);
        myActivitiesAdapter.setEmptyView(R.layout.empty_view);

        myActivitiesAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(MyActivitiesActivity.this, WebviewActivity.class);
                intent.putExtra(Constants.URL, PujingService.h5_myinfo + historyActivitiesBeans.get(position).id);
                startActivityForResult(intent,result_myactivity);
            }
        });

        mPresenter.getMyActivitiesData(page);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mPresenter.getMyActivitiesData(page);
            }
        });


        myActivitiesAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;

                mPresenter.getMyActivitiesData(page);
            }
        });

    }

    @Override
    protected MyActivitiesPresenter createPresenter() {
        return new MyActivitiesPresenter();
    }

    @Override
    public void getHistoryDataSuccess(PagesBean<HistoryActivitiesBean> historyActivitiesBean) {

        if (historyActivitiesBeans == null){
            historyActivitiesBeans = historyActivitiesBean.records;
        }else {
            if (page > 1){
                historyActivitiesBeans.addAll(historyActivitiesBean.records);
            }else {
                historyActivitiesBeans = historyActivitiesBean.records;
            }
        }

        myActivitiesAdapter.setNewInstance(historyActivitiesBeans);

        if (historyActivitiesBeans.size() == historyActivitiesBean.total) {
            myActivitiesAdapter.getLoadMoreModule().loadMoreEnd();
        }else {
            myActivitiesAdapter.getLoadMoreModule().loadMoreComplete();
        }


        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void getDataFail(String msg) {
        if (msg.contains("sorry")){
            msg = msg.substring(5,msg.length());
            UToast.show(this,msg);
            finish();
        }else {
            UToast.show(this, msg);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            page = 1;
            mPresenter.getMyActivitiesData(page);
        }
    }

    @OnClick(R.id.iv_my_activity_back)
    public void onClick(View view){
        if (view.getId() == R.id.iv_my_activity_back){
            finish();
        }
    }
}
