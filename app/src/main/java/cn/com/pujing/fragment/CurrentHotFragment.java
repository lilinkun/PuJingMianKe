package cn.com.pujing.fragment;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.List;

import butterknife.BindView;
import cn.com.pujing.R;
import cn.com.pujing.activity.WebviewActivity;
import cn.com.pujing.adapter.ExerciseAdapter;
import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.entity.ActivityCalendar;
import cn.com.pujing.entity.GetAllCategory;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.Urls;

public class CurrentHotFragment extends BaseFragment {

    @BindView(R.id.rv_exercise1)
    RecyclerView rvExercise1;

    private ExerciseAdapter exerciseAdapter;

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


        OkGo.get(Urls.ACTIVITYCALENDAR)
                .tag(this)
//                            .params(Constants.CALENDARTIME, dateTextView.getText().toString().trim())
//                            .params(Constants.CATEGORYID, selectedId)
                .execute(new JsonCallback<>(ActivityCalendar.class, this));
    }

    @Override
    public void onSuccess(Response response) {
        if (response != null) {

            if (response.body() instanceof ActivityCalendar) {
                ActivityCalendar activityCalendar = (ActivityCalendar) response.body();

                List<ActivityCalendar.Data.Record> list = activityCalendar.data.records;
                if (list != null && list.size() > 0) {
                    ActivityCalendar.Data.Record record = list.get(0);
                    record.itemType = -1;
                }
                exerciseAdapter.setNewInstance(list);
            }
        }
    }
}
