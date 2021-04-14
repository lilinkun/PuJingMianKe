package cn.com.pujing.view;

import cn.com.pujing.entity.CommemorationDayBean;

/**
 * author : liguo
 * date : 2021/4/13 9:31
 * description :
 */
public interface CommemorationDayView {
    public void getCommemorationDaySuccess(CommemorationDayBean commemorationDayBean);
    public void saveCommemorationDaySuccess(Object commemorationDayBean);
    public void deleteCommemorationDaySuccess(Object commemorationDayBean);
    public void getDataFail(String msg);

}
