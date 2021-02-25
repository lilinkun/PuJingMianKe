package cn.com.pujing.fragment;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.List;

import butterknife.BindView;
import cn.com.pujing.R;
import cn.com.pujing.adapter.HistoryActivitiesAdapter;
import cn.com.pujing.adapter.MyActivitiesAdapter;
import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.entity.ActivityCalendar;
import cn.com.pujing.entity.ExerciseBean;
import cn.com.pujing.util.Urls;

public class HistoryActivitiesFragment extends BaseFragment {
    @BindView(R.id.rv_history_activities)
    RecyclerView rvHistoryActivities;

    HistoryActivitiesAdapter historyActivitiesAdapter;

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

        OkGo.get(Urls.QUERY_HISTORY_ACTIVITY)
                .tag(this).execute(new JsonCallback<>(ExerciseBean.class, this));
    }

    @Override
    public void onSuccess(Response response) {
        super.onSuccess(response);
        if (response != null) {

            if (response.body() instanceof ExerciseBean) {
                ExerciseBean exerciseBean = (ExerciseBean) response.body();

                historyActivitiesAdapter.setNewInstance(exerciseBean.data);
            }
        }
    }
}
