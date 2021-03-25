package cn.com.pujing.activity;

import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.Base;
import cn.com.pujing.entity.QuerySelectDayBean;
import cn.com.pujing.presenter.CommunityCalendarPresenter;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.Methods;
import cn.com.pujing.R;
import cn.com.pujing.util.Urls;
import cn.com.pujing.adapter.AnotherExerciseAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.entity.ActivityDate;
import cn.com.pujing.entity.ActivityDateAdd;
import cn.com.pujing.entity.QuerySelectDay;
import cn.com.pujing.fragment.AddThingsDialogFragment;
import cn.com.pujing.view.CommunityCalendarView;

/**
 * 我的日历
 */
public class MyCalendarActivity extends BaseActivity<CommunityCalendarView, CommunityCalendarPresenter> implements View.OnClickListener, AddThingsDialogFragment.OnDialogListener,CommunityCalendarView {
    @BindView(R.id.calendarView)
    CalendarView calendarView;
    @BindView(R.id.tv_month)
    TextView monthTextView;
    @BindView(R.id.tv_exercise)
    TextView exerciseTextView;
    @BindView(R.id.rv)
    RecyclerView recyclerView;

    private int curYear;
    private int curMonth;
    private int selectedYear;
    private int selectedMonth;
    private int selectedDay;
    private String selectDay;
    private AnotherExerciseAdapter anotherExerciseAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_calendar;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this)
                .statusBarColor(R.color.main_color)
                .fitsSystemWindows(true)
                .init();

        java.util.Calendar cal = java.util.Calendar.getInstance();
        curYear = cal.get(java.util.Calendar.YEAR);
        curMonth = cal.get(java.util.Calendar.MONTH);
        selectedYear = cal.get(java.util.Calendar.YEAR);
        selectedMonth = cal.get(java.util.Calendar.MONTH);
        selectedDay = cal.get(java.util.Calendar.DAY_OF_MONTH);
        monthTextView.setText(curYear + "年" + (curMonth + 1) + "月");

        calendarView.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                calendarView.clearSchemeDate();
                calendarView.update();
                curYear = year;
                curMonth = month - 1;
                monthTextView.setText(year + "年" + month + "月");

                mPresenter.getCommunityData(Methods.getStartDayofMonth(curYear, curMonth),Methods.getEndDayofMonth(curYear, curMonth),2);

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

                selectDay = Methods.getDate(calendar.getYear(), calendar.getMonth() - 1, calendar.getDay());
                exerciseTextView.setText(String.format(getString(R.string.format_date_exercise), selectDay));

                mPresenter.querySelectDay(selectDay,2);

            }
        });

        selectDay = Methods.getCurDate();

        exerciseTextView.setText(String.format(getString(R.string.format_date_exercise), Methods.getDate(curYear, curMonth, calendarView.getCurDay())));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//        AnotherExerciseAdapter anotherExerciseAdapter = new AnotherExerciseAdapter(R.layout.item_exercise_another, AnotherExerciseItem.getTestData());
        anotherExerciseAdapter = new AnotherExerciseAdapter(R.layout.item_exercise_another, null);


        recyclerView.setAdapter(anotherExerciseAdapter);
        anotherExerciseAdapter.setEmptyView(R.layout.empty_view);

        mPresenter.getCommunityData(Methods.getStartDayofMonth(curYear, curMonth),Methods.getEndDayofMonth(curYear, curMonth),2);

        mPresenter.querySelectDay(Methods.getDate(curYear, curMonth, calendarView.getCurDay()),2);
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
//                anotherExerciseAdapter.setNewInstance(querySelectDay.data);
            } else if (response.body() instanceof ActivityDateAdd) {
                ActivityDateAdd activityDateAdd = (ActivityDateAdd) response.body();
                if (activityDateAdd.data) {
                    String selectDay = Methods.getDate(selectedYear, selectedMonth, selectedDay);

                    mPresenter.querySelectDay(selectDay,2);
//                    OkGo.get(Urls.QUERYSELECTDAY_ANOTHER)
//                            .tag(this)
//                            .params(Constants.SELECTDAY, selectDay)
//                            .execute(new JsonCallback<>(QuerySelectDay.class, MyCalendarActivity.this));
                }
            }
        }
    }

    @Override
    public void onFail(Base base) {

        Toast.makeText(this,base.msg+"添加失败",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected CommunityCalendarPresenter createPresenter() {
        return new CommunityCalendarPresenter();
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
    @OnClick({R.id.iv_back,R.id.iv_pre,R.id.iv_next,R.id.tv_add})
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
        params.put(Constants.ACTIVITYDATE, selectDay);
        params.put(Constants.BEGINTIME, startTime);
        params.put(Constants.ENDTIME, endTime);
        params.put(Constants.CONTENT, content);
        JSONObject jsonObject = new JSONObject(params);

        OkGo.post(Urls.QUERYSELECTDAY_ADD)
                .tag(this)
                .upJson(jsonObject)
                .execute(new JsonCallback<>(ActivityDateAdd.class, this));
    }


    @Override
    public void getCommunityDataSuccess(List<Long> longs) {
        List<Long> list = longs;

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
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void getDaySuccess(List<QuerySelectDayBean> querySelectDayBeans) {
                anotherExerciseAdapter.setNewInstance(querySelectDayBeans);
    }
}
