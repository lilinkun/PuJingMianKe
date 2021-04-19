package cn.com.pujing.view;

import java.util.List;

import cn.com.pujing.entity.HistoryActivitiesBean;
import cn.com.pujing.entity.PagesBean;

/**
 * author : liguo
 * date : 2021/3/1 14:47
 * description :
 */
public interface HistoryActivitiesView {

    public void getHistoryDataSuccess(PagesBean<HistoryActivitiesBean> historyActivitiesBean);
    public void getDataFail(String msg);

}
