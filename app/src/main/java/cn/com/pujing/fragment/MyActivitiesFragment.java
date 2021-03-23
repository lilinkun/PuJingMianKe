package cn.com.pujing.fragment;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import butterknife.BindView;
import cn.com.pujing.R;
import cn.com.pujing.activity.WebviewActivity;
import cn.com.pujing.adapter.MyActivitiesAdapter;
import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.entity.HistoryActivitiesBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.presenter.MyActivitiesPresenter;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.MyActivitiesView;

import static android.app.Activity.RESULT_OK;

public class MyActivitiesFragment extends BaseFragment<MyActivitiesView, MyActivitiesPresenter> implements MyActivitiesView {

    @BindView(R.id.rv_my_activities)
    RecyclerView rvMyActivities;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private MyActivitiesAdapter myActivitiesAdapter;
    private HistoryActivitiesBean historyActivitiesBean;
    private int result_myactivity = 0x434;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_my_activities;
    }

    @Override
    public void initEventAndData() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvMyActivities.setLayoutManager(linearLayoutManager);

        myActivitiesAdapter = new MyActivitiesAdapter(R.layout.adapter_myactivities,null);
        rvMyActivities.setAdapter(myActivitiesAdapter);
        myActivitiesAdapter.setEmptyView(R.layout.empty_view);

        myActivitiesAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra(Constants.URL, PujingService.h5_myinfo + historyActivitiesBean.getRecords().get(position).id);
                startActivityForResult(intent,result_myactivity);
            }
        });

        mPresenter.getMyActivitiesData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getMyActivitiesData();
            }
        });
    }

    public void getData(){
        mPresenter.getMyActivitiesData();
    }

    @Override
    protected MyActivitiesPresenter createPresenter() {
        return new MyActivitiesPresenter();
    }


    @Override
    public void getHistoryDataSuccess(HistoryActivitiesBean historyActivitiesBean) {
        if (historyActivitiesBean != null) {
            this.historyActivitiesBean = historyActivitiesBean;
            myActivitiesAdapter.setNewInstance(historyActivitiesBean.getRecords());
        }

        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(getActivity(),msg);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            mPresenter.getMyActivitiesData();
        }
    }
}
