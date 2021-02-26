package cn.com.pujing.fragment;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.List;

import butterknife.BindView;
import cn.com.pujing.R;
import cn.com.pujing.adapter.MyActivitiesAdapter;
import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.entity.ActivityCalendar;
import cn.com.pujing.entity.ExerciseBean;
import cn.com.pujing.util.Urls;

public class MyActivitiesFragment extends BaseFragment {

    @BindView(R.id.rv_my_activities)
    RecyclerView rvMyActivities;

    private MyActivitiesAdapter myActivitiesAdapter;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_my_activities;
    }

    @Override
    public void initEventAndData() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvMyActivities.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rvMyActivities.addItemDecoration(dividerItemDecoration);

        myActivitiesAdapter = new MyActivitiesAdapter(R.layout.adapter_myactivities,null);
        rvMyActivities.setAdapter(myActivitiesAdapter);
        myActivitiesAdapter.setEmptyView(R.layout.empty_view);

        OkGo.get(Urls.QUERY_MYACTIVITY)
                .tag(this).execute(new JsonCallback<>(ExerciseBean.class, this));

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    public void onSuccess(Response response) {
        super.onSuccess(response);
        if (response != null) {

            if (response.body() instanceof ExerciseBean) {
                ExerciseBean exerciseBean = (ExerciseBean) response.body();

                myActivitiesAdapter.setNewInstance(exerciseBean.data);
            }

        }
    }
}
