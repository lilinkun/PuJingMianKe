package cn.com.pujing.fragment;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import butterknife.BindView;
import cn.com.pujing.R;
import cn.com.pujing.activity.WebviewActivity;
import cn.com.pujing.adapter.HistoryActivitiesAdapter;
import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.entity.HistoryActivitiesBean;
import cn.com.pujing.presenter.HistoryActivitiesPresenter;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.UToast;
import cn.com.pujing.util.Urls;
import cn.com.pujing.view.HistoryActivitiesView;

public class HistoryActivitiesFragment extends BaseFragment<HistoryActivitiesView, HistoryActivitiesPresenter> implements HistoryActivitiesView {
    @BindView(R.id.rv_history_activities)
    RecyclerView rvHistoryActivities;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    HistoryActivitiesAdapter historyActivitiesAdapter;
    private HistoryActivitiesBean historyActivitiesBean;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_history_activities;
    }

    @Override
    public void initEventAndData() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvHistoryActivities.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rvHistoryActivities.addItemDecoration(dividerItemDecoration);

        historyActivitiesAdapter = new HistoryActivitiesAdapter(R.layout.item_exercise,null);
        rvHistoryActivities.setAdapter(historyActivitiesAdapter);
        historyActivitiesAdapter.setEmptyView(R.layout.empty_view);

        historyActivitiesAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra(Constants.URL, Urls.EVENTDETAILS + historyActivitiesBean.getRecords().get(position).id);
                startActivity(intent);
            }
        });

        mPresenter.getActivitiesDataSuccess();

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getActivitiesDataSuccess();
            }
        });

    }

    @Override
    protected HistoryActivitiesPresenter createPresenter() {
        return new HistoryActivitiesPresenter();
    }



    @Override
    public void getHistoryDataSuccess(HistoryActivitiesBean historyActivitiesBean) {
        if (historyActivitiesBean != null){
            this.historyActivitiesBean = historyActivitiesBean;
            historyActivitiesAdapter.setNewInstance(historyActivitiesBean.getRecords());
        }

        if (swipeLayout.isRefreshing()){
            swipeLayout.setRefreshing(false);
        }
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(getActivity(),msg);
    }
}
