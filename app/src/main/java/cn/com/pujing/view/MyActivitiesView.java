package cn.com.pujing.view;

import cn.com.pujing.entity.HistoryActivitiesBean;

/**
 * author : liguo
 * date : 2021/3/1 15:11
 * description :
 */
public interface MyActivitiesView {
    public void getHistoryDataSuccess(HistoryActivitiesBean historyActivitiesBean);
    public void getDataFail(String msg);
}
