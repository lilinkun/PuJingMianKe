package cn.com.pujing.view;

import java.util.List;

import cn.com.pujing.entity.AddRestBean;
import cn.com.pujing.entity.RestTypeBean;
import cn.com.pujing.entity.RoutineRecordBean;
import cn.com.pujing.entity.SetMealBean;

/**
 * author : liguo
 * date : 2021/3/1 18:03
 * description :
 */
public interface RestRoutineView {

    public void getSetMealSuccess(List<SetMealBean> setMealBean);

    public void getSetMealTypeSuccess(List<RestTypeBean> restTypeBeans);

    public void getDataFail(String msg);

    public void getRestClickDataFail(String msg);

    public void saveDataSuccess(boolean b);

    public void submitSuccess(boolean b);

    public void getRestClickData(RoutineRecordBean addRestBean);

}
