package cn.com.pujing.view;

import cn.com.pujing.entity.HistoryActivitiesBean;

/**
 * author : liguo
 * date : 2021/3/1 14:47
 * description :
 */
public interface HistoryActivitiesView {

    public void getHistoryDataSuccess(HistoryActivitiesBean historyActivitiesBean);
    public void getDataFail(String msg);

}
