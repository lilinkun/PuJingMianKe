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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.Methods;
import cn.com.pujing.R;
import cn.com.pujing.util.Urls;
import cn.com.pujing.adapter.AnotherExerciseAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.entity.ActivityDate;
import cn.com.pujing.entity.QuerySelectDay;

public class CommunityCalendarActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.calendarView)
    CalendarView calendarView;
    @BindView(R.id.rv_community_calendar)
    RecyclerView rvCommunityCalendar;
    @BindView(R.id.tv_month)
    TextView monthTextView;
    @BindView(R.id.tv_exercise)
    TextView exerciseTextView;
    private int curYear;
    private int curMonth;
    private AnotherExerciseAdapter anotherExerciseAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_community_calendar;
    }

    @Override
    public void init() {

        ImmersionBar.with(this)
                .statusBarColor(R.color.main_color)
                .fitsSystemWindows(true)
                .init();

        java.util.Calendar cal = java.util.Calendar.getInstance();
        curYear = cal.get(java.util.Calendar.YEAR);
        curMonth = cal.get(java.util.Calendar.MONTH);
        monthTextView.setText(curYear + "年" + (curMonth + 1) + "月");


        calendarView.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                calendarView.clearSchemeDate();
                calendarView.update();
                curYear = year;
                curMonth = month - 1;
                monthTextView.setText(year + "年" + month + "月");

                OkGo.get(Urls.ACTIVITYDATE)
                        .tag(this)
                        .params(Constants.STARTTIME, Methods.getStartDayofMonth(curYear, curMonth))
                        .params(Constants.ENDTIME, Methods.getEndDayofMonth(curYear, curMonth))
                        .execute(new JsonCallback<>(ActivityDate.class, CommunityCalendarActivity.this));
            }
        });
        calendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {

            }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {
                String selectDay = Methods.getDate(calendar.getYear(), calendar.getMonth() - 1, calendar.getDay());
                exerciseTextView.setText(String.format(getString(R.string.format_date_exercise), selectDay));

                OkGo.get(Urls.QUERYSELECTDAY)
                        .tag(this)
                        .params(Constants.SELECTDAY, selectDay)
                        .execute(new JsonCallback<>(QuerySelectDay.class, CommunityCalendarActivity.this));

            }
        });

        exerciseTextView.setText(String.format(getString(R.string.format_date_exercise), Methods.getDate(curYear, curMonth, calendarView.getCurDay())));

        rvCommunityCalendar.setLayoutManager(new LinearLayoutManager(this));
//        rvCommunityCalendar.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//        AnotherExerciseAdapter anotherExerciseAdapter = new AnotherExerciseAdapter(R.layout.item_exercise_another, AnotherExerciseItem.getTestData());
        anotherExerciseAdapter = new AnotherExerciseAdapter(R.layout.item_exercise_another, null);
        rvCommunityCalendar.setAdapter(anotherExerciseAdapter);
        anotherExerciseAdapter.setEmptyView(R.layout.empty_view);

        OkGo.get(Urls.ACTIVITYDATE)
                .tag(this)
                .params(Constants.STARTTIME, Methods.getStartDayofMonth(curYear, curMonth))
                .params(Constants.ENDTIME, Methods.getEndDayofMonth(curYear, curMonth))
                .execute(new JsonCallback<>(ActivityDate.class, this));

        OkGo.get(Urls.QUERYSELECTDAY)
                .tag(this)
                .params(Constants.SELECTDAY, Methods.getDate(curYear, curMonth, calendarView.getCurDay()))
                .execute(new JsonCallback<>(QuerySelectDay.class, CommunityCalendarActivity.this));
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
    @OnClick({R.id.iv_back,R.id.iv_pre,R.id.iv_next})
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.iv_pre) {
            calendarView.scrollToPre();
        } else if (id == R.id.iv_next) {
            calendarView.scrollToNext();
        }
    }

}
