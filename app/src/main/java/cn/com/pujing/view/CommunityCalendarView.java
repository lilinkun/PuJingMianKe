package cn.com.pujing.view;

import java.util.List;

import cn.com.pujing.entity.QuerySelectDayBean;

/**
 * author : liguo
 * date : 2021/3/5 18:18
 * description :
 */
public interface CommunityCalendarView{

    public void getCommunityDataSuccess(List<Long> longs);

    public void getDataFail(String msg);

    public void getDaySuccess(List<QuerySelectDayBean> querySelectDayBeans);


}
