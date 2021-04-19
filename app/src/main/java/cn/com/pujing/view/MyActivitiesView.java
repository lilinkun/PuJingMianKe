package cn.com.pujing.view;

import cn.com.pujing.entity.HistoryActivitiesBean;
import cn.com.pujing.entity.PagesBean;

/**
 * author : liguo
 * date : 2021/3/1 15:11
 * description :
 */
public interface MyActivitiesView {
    public void getHistoryDataSuccess(PagesBean<HistoryActivitiesBean> historyActivitiesBean);
    public void getDataFail(String msg);
}
