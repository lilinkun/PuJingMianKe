package cn.com.pujing.view;

import cn.com.pujing.entity.AddRestBean;
import cn.com.pujing.entity.RoutineRecordBean;

/**
 * author : liguo
 * date : 2021/3/16 14:27
 * description :
 */
public interface RestaurantView {

    public void getRestData(RoutineRecordBean addRestBean);

    public void getDataFail(String msg);

}
