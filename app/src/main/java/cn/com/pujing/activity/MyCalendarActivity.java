package cn.com.pujing.activity;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.pujing.Constants;
import cn.com.pujing.Methods;
import cn.com.pujing.R;
import cn.com.pujing.Urls;
import cn.com.pujing.adapter.AnotherExerciseAdapter;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.datastructure.ActivityDate;
import cn.com.pujing.datastructure.ActivityDateAdd;
import cn.com.pujing.datastructure.QuerySelectDay;
import cn.com.pujing.fragment.AddThingsDialogFragment;

public class MyCalendarActivity extends BaseActivity implements View.OnClickListener, AddThingsDialogFragment.OnDialogListener {
    private CalendarView calendarView;
    private int curYear;
    private int curMonth;
    private int selectedYear;
    private int selectedMonth;
    private int selectedDay;
    private AnotherExerciseAdapter anotherExerciseAdapter;
    private TextView exerciseTextView;

    @Override
    public void init() {
        setContentView(R.layout.activity_my_calendar);

        ImmersionBar.with(this)
                .statusBarColor("#ED6D0F")
                .fitsSystemWindows(true)
                .init();

        findViewById(R.id.iv_back).setOnClickListener(this);

        TextView monthTextView = findViewById(R.id.tv_month);
        java.util.Calendar cal = java.util.Calendar.getInstance();
        curYear = cal.get(java.util.Calendar.YEAR);
        curMonth = cal.get(java.util.Calendar.MONTH);
        selectedYear = cal.get(java.util.Calendar.YEAR);
        selectedMonth = cal.get(java.util.Calendar.MONTH);
        selectedDay = cal.get(java.util.Calendar.DAY_OF_MONTH);
        monthTextView.setText(curYear + "年" + (curMonth + 1) + "月");

        findViewById(R.id.iv_pre).setOnClickListener(this);
        findViewById(R.id.iv_next).setOnClickListener(this);


        calendarView = findViewById(R.id.calendarView);

        calendarView.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                calendarView.clearSchemeDate();
                calendarView.update();
                curYear = year;
                curMonth = month - 1;
                monthTextView.setText(year + "年" + month + "月");

                OkGo.get(Urls.ACTIVITYDATE_ANOTHER)
                        .tag(this)
                        .params(Constants.STARTTIME, Methods.getStartDayofMonth(curYear, curMonth))
                        .params(Constants.ENDTIME, Methods.getEndDayofMonth(curYear, curMonth))
                        .execute(new JsonCallback<>(ActivityDate.class, MyCalendarActivity.this));
            }
        });
        calendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {

            }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {
                selectedYear = calendar.getYear();
                selectedMonth = calendar.getMonth() - 1;
                selectedDay = calendar.getDay();

                String selectDay = Methods.getDate(calendar.getYear(), calendar.getMonth() - 1, calendar.getDay());
                exerciseTextView.setText(String.format(getString(R.string.format_date_exercise), selectDay));

                OkGo.get(Urls.QUERYSELECTDAY_ANOTHER)
                        .tag(this)
                        .params(Constants.SELECTDAY, selectDay)
                        .execute(new JsonCallback<>(QuerySelectDay.class, MyCalendarActivity.this));

            }
        });

        exerciseTextView = findViewById(R.id.tv_exercise);
        exerciseTextView.setText(String.format(getString(R.string.format_date_exercise), Methods.getDate(curYear, curMonth, calendarView.getCurDay())));

        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//        AnotherExerciseAdapter anotherExerciseAdapter = new AnotherExerciseAdapter(R.layout.item_exercise_another, AnotherExerciseItem.getTestData());
        anotherExerciseAdapter = new AnotherExerciseAdapter(R.layout.item_exercise_another, null);

//        View footerView = LayoutInflater.from(this).inflate(R.layout.footer_my_calendar, null);
//        footerView.findViewById(R.id.tv_add).setOnClickListener(this);
//        anotherExerciseAdapter.addFooterView(footerView);

        recyclerView.setAdapter(anotherExerciseAdapter);
        anotherExerciseAdapter.setEmptyView(R.layout.empty_view);

        findViewById(R.id.tv_add).setOnClickListener(this);

        OkGo.get(Urls.ACTIVITYDATE_ANOTHER)
                .tag(this)
                .params(Constants.STARTTIME, Methods.getStartDayofMonth(curYear, curMonth))
                .params(Constants.ENDTIME, Methods.getEndDayofMonth(curYear, curMonth))
                .execute(new JsonCallback<>(ActivityDate.class, this));

        OkGo.get(Urls.QUERYSELECTDAY_ANOTHER)
                .tag(this)
                .params(Constants.SELECTDAY, Methods.getDate(selectedYear, selectedMonth, selectedDay))
                .execute(new JsonCallback<>(QuerySelectDay.class, MyCalendarActivity.this));
    }

    @Override
    public void onSuccess(Response response) {

        if (response != null) {

            if (response.body() instanceof ActivityDate) {
                ActivityDate activityDate = (ActivityDate) response.body();
                List<Long> list = activityDate.data;

                if (list != null && list.size() > 0) {
                    Map<String, Calendar> map = new HashMap<>();

                    for (Long l : list) {
                        java.util.Calendar calendar = java.util.Calendar.getInstance();
                        calendar.setTimeInMillis(l);
                        int year = calendar.get(java.util.Calendar.YEAR);
                        int month = calendar.get(java.util.Calendar.MONTH) + 1;
                        int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);

                        map.put(getSchemeCalendar(year, month, day, 0xFFe69138, "事").toString(),
                                getSchemeCalendar(year, month, day, 0xFFe69138, "事"));
                    }
                    calendarView.setSchemeDate(map);
                    calendarView.update();
                }

            } else if (response.body() instanceof QuerySelectDay) {
                QuerySelectDay querySelectDay = (QuerySelectDay) response.body();
                anotherExerciseAdapter.setNewInstance(querySelectDay.data);
            } else if (response.body() instanceof ActivityDateAdd) {
                ActivityDateAdd activityDateAdd = (ActivityDateAdd) response.body();
                if (activityDateAdd.data) {
                    String selectDay = Methods.getDate(selectedYear, selectedMonth, selectedDay);

                    OkGo.get(Urls.QUERYSELECTDAY_ANOTHER)
                            .tag(this)
                            .params(Constants.SELECTDAY, selectDay)
                            .execute(new JsonCallback<>(QuerySelectDay.class, MyCalendarActivity.this));
                }
            }
        }
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.iv_pre) {
            calendarView.scrollToPre();
        } else if (id == R.id.iv_next) {
            calendarView.scrollToNext();
        } else if (id == R.id.tv_add) {
            AddThingsDialogFragment addThingsDialogFragment = new AddThingsDialogFragment(this);
            addThingsDialogFragment.show(getSupportFragmentManager(), "");
        }
    }


    @Override
    public void onDialogClick(String startTime, String endTime, String content) {
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.ACTIVITYDATE, Methods.getDate(curYear, curMonth, calendarView.getCurDay()));
        params.put(Constants.BEGINTIME, startTime);
        params.put(Constants.ENDTIME, endTime);
        params.put(Constants.CONTENT, content);
        JSONObject jsonObject = new JSONObject(params);

        OkGo.post(Urls.QUERYSELECTDAY_ADD)
                .tag(this)
                .upJson(jsonObject)
                .execute(new JsonCallback<>(ActivityDateAdd.class, this));
    }
}
