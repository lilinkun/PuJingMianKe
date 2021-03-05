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
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.List;

import butterknife.BindView;
import cn.com.pujing.R;
import cn.com.pujing.activity.WebviewActivity;
import cn.com.pujing.adapter.ExerciseAdapter;
import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.entity.ActivityCalendar;
import cn.com.pujing.entity.GetAllCategory;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.Urls;

public class CurrentHotFragment extends BaseFragment {

    @BindView(R.id.rv_exercise1)
    RecyclerView rvExercise1;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private ExerciseAdapter exerciseAdapter;
    private List<ActivityCalendar.Data.Record> list;
    int page  = 1;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_current_hot;
    }

    @Override
    public void initEventAndData() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvExercise1.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rvExercise1.addItemDecoration(dividerItemDecoration);

        if (list != null){
            list.clear();
            page = 1;
        }

        exerciseAdapter = new ExerciseAdapter(null);
        rvExercise1.setAdapter(exerciseAdapter);
        exerciseAdapter.setEmptyView(R.layout.empty_view);
        exerciseAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ActivityCalendar.Data.Record record = exerciseAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra(Constants.URL, Urls.EVENTDETAILS + record.id);
                startActivity(intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                OkGo.get(Urls.ACTIVITYCALENDAR)
                        .tag(this)
//                            .params(Constants.CALENDARTIME, dateTextView.getText().toString().trim())
                        .params("page", page+"")
                        .execute(new JsonCallback<>(ActivityCalendar.class, CurrentHotFragment.this));
            }
        });

        exerciseAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                page++;
                OkGo.get(Urls.ACTIVITYCALENDAR)
                        .tag(this)
//                            .params(Constants.CALENDARTIME, dateTextView.getText().toString().trim())
                        .params("page", page+"")
                        .execute(new JsonCallback<>(ActivityCalendar.class, CurrentHotFragment.this));
            }
        });

        OkGo.get(Urls.ACTIVITYCALENDAR)
                .tag(this)
//                            .params(Constants.CALENDARTIME, dateTextView.getText().toString().trim())
                            .params("page", page+"")
                .execute(new JsonCallback<>(ActivityCalendar.class, this));
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onSuccess(Response response) {
        if (response != null) {

            if (response.body() instanceof ActivityCalendar) {
                ActivityCalendar activityCalendar = (ActivityCalendar) response.body();
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }

                if (list != null && list.size() > 0){
                    list.addAll(activityCalendar.data.records);
                }else {
                    list = activityCalendar.data.records;
                }

                if (list != null && list.size() > 0) {
                    ActivityCalendar.Data.Record record = list.get(0);
                    record.itemType = -1;
                }

                exerciseAdapter.setNewInstance(list);

                if (exerciseAdapter.getLoadMoreModule().isLoading()){
                    exerciseAdapter.getLoadMoreModule().loadMoreEnd();
                }
            }
        }
    }
}
