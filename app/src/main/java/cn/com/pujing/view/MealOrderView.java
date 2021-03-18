package cn.com.pujing.view;

import cn.com.pujing.entity.RoutineRecordBean;

/**
 * author : liguo
 * date : 2021/3/7 12:30
 * description :
 */
public interface MealOrderView {

    public void getRestData(RoutineRecordBean addRestBean);

    public void getDataFail(String msg);

    public void submitSuccess(boolean b);

}
