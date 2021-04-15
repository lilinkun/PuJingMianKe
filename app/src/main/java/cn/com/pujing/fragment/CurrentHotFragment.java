package cn.com.pujing.fragment;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.pujing.R;
import cn.com.pujing.activity.WebviewActivity;
import cn.com.pujing.adapter.ExerciseAdapter;
import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.entity.ActivityCalendar;
import cn.com.pujing.entity.HotActivityBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.presenter.CurrentHotPresenter;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.CurrentHotView;

import static android.app.Activity.RESULT_OK;

/**
 * 热门活动
 */
public class CurrentHotFragment extends BaseFragment<CurrentHotView, CurrentHotPresenter> implements CurrentHotView{

    @BindView(R.id.rv_exercise1)
    RecyclerView rvExercise1;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private ExerciseAdapter exerciseAdapter;
    private List<HotActivityBean.Record> list = new ArrayList<>();
    int page  = 1;
    String endTime;
    String startTime;
    String status;
    String type;
    private static final int WEB_RESULT = 0x232;

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
                HotActivityBean.Record record = exerciseAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra(Constants.URL, PujingService.EVENTDETAILS + record.id);
                startActivityForResult(intent,WEB_RESULT);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                if (list != null){
                    list.clear();
                }
                mPresenter.getHotActivitiy(page,endTime,startTime,status,type,0);
            }
        });

        exerciseAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                mPresenter.getHotActivitiy(page,endTime,startTime,status,type,0);
            }
        });
        mPresenter.getHotActivitiy(page,endTime,startTime,status,type,0);
    }

    public void setHotPresenter(String endTime,String startTime,String status,String type){
        page = 1;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.type = type;
        mPresenter.getHotActivitiy(page,endTime,startTime,status,type,0);
    }

    @Override
    protected CurrentHotPresenter createPresenter() {
        return new CurrentHotPresenter();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == WEB_RESULT){
                page = 1;
                if (list != null){
                    list.clear();
                }
                mPresenter.getHotActivitiy(page,endTime,startTime,status,type,1);
            }
        }
    }

    @Override
    public void getHotActivitiySuccess(HotActivityBean hotActivityBean,int where) {

        if (swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }


        if (hotActivityBean.pages != 1) {

            if (hotActivityBean.records != null && hotActivityBean.records.size() > 0) {
                list.addAll(hotActivityBean.records);
            } else {
                list = hotActivityBean.records;
            }
        }else {
            list = hotActivityBean.records;
        }

            if (hotActivityBean.records != null && hotActivityBean.records.size() > 0) {
                HotActivityBean.Record record = list.get(0);
                record.itemType = -1;
            }
            if (where == 0) {
                exerciseAdapter.setNewInstance(list);
            }else {
                exerciseAdapter.setDatas(list);
            }
            if (hotActivityBean.size == hotActivityBean.total) {
                exerciseAdapter.getLoadMoreModule().loadMoreComplete();
            }else {
                exerciseAdapter.getLoadMoreModule().loadMoreEnd();
            }

    }

    @Override
    public void getHotActivitiyFail(String msg) {
        UToast.show(getActivity(),msg);
    }
}
