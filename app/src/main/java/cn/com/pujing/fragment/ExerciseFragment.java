package cn.com.pujing.fragment;

import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;
import org.angmarch.views.SpinnerTextFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.util.Constants;
import cn.com.pujing.R;
import cn.com.pujing.util.Urls;
import cn.com.pujing.activity.WebviewActivity;
import cn.com.pujing.adapter.ExerciseAdapter;
import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.entity.ActivityCalendar;
import cn.com.pujing.entity.GetAllCategory;

public class ExerciseFragment extends BaseFragment implements View.OnClickListener, DatePickerDialogFragment.OnDialogListener {

    private ExerciseAdapter exerciseAdapter;
    private int selectedId;

    @BindView(R.id.tv_my_enroll)
    TextView tvMyEnroll;
    @BindView(R.id.ns)
    NiceSpinner niceSpinner;
    @BindView(R.id.tv_date)
    TextView dateTextView;
    @BindView(R.id.rv_exercise)
    RecyclerView rvExercise;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_exerciset;
    }

    @Override
    public void initEventAndData() {


        SpinnerTextFormatter textFormatter = new SpinnerTextFormatter<GetAllCategory.Data>() {

            @Override
            public Spannable format(GetAllCategory.Data data) {
                return new SpannableString(data.categoryName);
            }
        };
        niceSpinner.setSpinnerTextFormatter(textFormatter);
        niceSpinner.setSelectedTextFormatter(textFormatter);
        niceSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {

            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                GetAllCategory.Data data = (GetAllCategory.Data) niceSpinner.getSelectedItem();
                if (data != null) {
                    selectedId = data.id;

                    OkGo.get(Urls.ACTIVITYCALENDAR)
                            .tag(this)
                            .params(Constants.CALENDARTIME, dateTextView.getText().toString().trim())
                            .params(Constants.CATEGORYID, selectedId)
                            .execute(new JsonCallback<>(ActivityCalendar.class, ExerciseFragment.this));
                }
            }
        });
        niceSpinner.setClickable(false);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String string = simpleDateFormat.format(Calendar.getInstance().getTime());
        dateTextView.setText(string);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvExercise.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rvExercise.addItemDecoration(dividerItemDecoration);

        exerciseAdapter = new ExerciseAdapter(null);
        rvExercise.setAdapter(exerciseAdapter);
        exerciseAdapter.setEmptyView(R.layout.empty_view);
        exerciseAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ActivityCalendar.Data.Record record = exerciseAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra(Constants.URL, PujingService.EVENTDETAILS + record.id);
                startActivity(intent);
            }
        });

        OkGo.get(Urls.GETALLCATEGORY)
                .tag(this)
                .execute(new JsonCallback<>(GetAllCategory.class, this));
    }

    @Override
    @OnClick({R.id.tv_my_enroll,R.id.tv_date})
    public void onClick(View v) {
        if (v.getId() == R.id.tv_my_enroll) {
//            startActivity(new Intent(getContext(), MyEnrollActivity.class));
        } else if (v.getId() == R.id.tv_date) {
            DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment(this);
            datePickerDialogFragment.show(getFragmentManager(), "");
        }
    }

    @Override
    public void onDialogClick(String string) {
        dateTextView.setText(string);
        OkGo.get(Urls.ACTIVITYCALENDAR)
                .tag(this)
                .params(Constants.CALENDARTIME, dateTextView.getText().toString().trim())
                .params(Constants.CATEGORYID, selectedId)
                .execute(new JsonCallback<>(ActivityCalendar.class, this));
    }

    @Override
    public void onSuccess(Response response) {

        if (response != null) {

            if (response.body() instanceof GetAllCategory) {
                GetAllCategory getAllCategory = (GetAllCategory) response.body();

                List<GetAllCategory.Data> list = getAllCategory.data;
                niceSpinner.attachDataSource(list);

                if (list != null && list.size() > 0) {
                    niceSpinner.setClickable(true);
                    selectedId = list.get(0).id;
                    OkGo.get(Urls.ACTIVITYCALENDAR)
                            .tag(this)
//                            .params(Constants.CALENDARTIME, dateTextView.getText().toString().trim())
//                            .params(Constants.CATEGORYID, selectedId)
                            .execute(new JsonCallback<>(ActivityCalendar.class, this));
                }
            } else if (response.body() instanceof ActivityCalendar) {
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

    @Override
    public boolean immersionBarEnabled() {
        return true;
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).titleBar((R.id.v_title_bar)).init();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

}
