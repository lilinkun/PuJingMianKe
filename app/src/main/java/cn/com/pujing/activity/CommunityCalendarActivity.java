package cn.com.pujing.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gyf.immersionbar.ImmersionBar;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.AnotherExerciseAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.QuerySelectDayBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.presenter.CommunityCalendarPresenter;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.Methods;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.CommunityCalendarView;

/**
 * 社区日历
 */
public class CommunityCalendarActivity extends BaseActivity<CommunityCalendarView, CommunityCalendarPresenter> implements View.OnClickListener,CommunityCalendarView {
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
    private List<QuerySelectDayBean> querySelectDayBeans;

    @Override
    public int getLayoutId() {
        return R.layout.activity_community_calendar;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        java.util.Calendar cal = java.util.Calendar.getInstance();
        curYear = cal.get(java.util.Calendar.YEAR);
        curMonth = cal.get(java.util.Calendar.MONTH);
        monthTextView.setText(curYear + getString(R.string.year) + (curMonth + 1) + "月");


        calendarView.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                calendarView.clearSchemeDate();
                calendarView.update();
                curYear = year;
                curMonth = month - 1;
                monthTextView.setText(year + getString(R.string.year) + month + "月");
                mPresenter.getCommunityData(Methods.getStartDayofMonth(curYear, curMonth),Methods.getEndDayofMonth(curYear, curMonth),1);

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

                mPresenter.querySelectDay(selectDay,1);

            }
        });

        exerciseTextView.setText(String.format(getString(R.string.format_date_exercise), Methods.getDate(curYear, curMonth, calendarView.getCurDay())));

        rvCommunityCalendar.setLayoutManager(new LinearLayoutManager(this));
//        rvCommunityCalendar.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//        AnotherExerciseAdapter anotherExerciseAdapter = new AnotherExerciseAdapter(R.layout.item_exercise_another, AnotherExerciseItem.getTestData());
        anotherExerciseAdapter = new AnotherExerciseAdapter(R.layout.item_exercise_another, null);
        rvCommunityCalendar.setAdapter(anotherExerciseAdapter);
        anotherExerciseAdapter.setEmptyView(R.layout.empty_view);

        anotherExerciseAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(CommunityCalendarActivity.this, WebviewActivity.class);
                intent.putExtra(Constants.URL, PujingService.EVENTDETAILS + querySelectDayBeans.get(position).activityId);
                startActivity(intent);
            }
        });


        mPresenter.getCommunityData(Methods.getStartDayofMonth(curYear, curMonth),Methods.getEndDayofMonth(curYear, curMonth),1);

        mPresenter.querySelectDay(Methods.getDate(curYear, curMonth, calendarView.getCurDay()),1);

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

        if (msg.contains("sorry")){
            msg = msg.substring(5,msg.length());
            UToast.show(this,msg);
            finish();
        }else {
            UToast.show(this, msg);
        }
    }

    @Override
    public void getDaySuccess(List<QuerySelectDayBean> querySelectDayBeans) {
        this.querySelectDayBeans = querySelectDayBeans;
        anotherExerciseAdapter.setNewInstance(querySelectDayBeans);
    }
}
