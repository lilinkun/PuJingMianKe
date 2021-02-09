package cn.com.pujing.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;
import org.angmarch.views.SpinnerTextFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import cn.com.pujing.Constants;
import cn.com.pujing.R;
import cn.com.pujing.Urls;
import cn.com.pujing.activity.MyEnrollActivity;
import cn.com.pujing.activity.WebviewActivity;
import cn.com.pujing.adapter.ExerciseAdapter;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.datastructure.ActivityCalendar;
import cn.com.pujing.datastructure.GetAllCategory;

public class ExerciseFragment extends BaseFragment implements View.OnClickListener, DatePickerDialogFragment.OnDialogListener {
    private View view;
    private NiceSpinner niceSpinner;
    private ExerciseAdapter exerciseAdapter;
    private TextView dateTextView;
    private int selectedId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_exerciset, null);
            init(view);
        }
        return view;
    }

    private void init(View view) {
        view.findViewById(R.id.tv_my_enroll).setOnClickListener(this);

        niceSpinner = view.findViewById(R.id.ns);
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

        dateTextView = view.findViewById(R.id.tv_date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String string = simpleDateFormat.format(Calendar.getInstance().getTime());
        dateTextView.setText(string);
        dateTextView.setOnClickListener(this);

        RecyclerView recyclerView = view.findViewById(R.id.tv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        exerciseAdapter = new ExerciseAdapter(null);
        recyclerView.setAdapter(exerciseAdapter);
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

        OkGo.get(Urls.GETALLCATEGORY)
                .tag(this)
                .execute(new JsonCallback<>(GetAllCategory.class, this));
    }

    @Override
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
                            .params(Constants.CALENDARTIME, dateTextView.getText().toString().trim())
                            .params(Constants.CATEGORYID, selectedId)
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
}
