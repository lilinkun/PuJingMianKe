package cn.com.pujing.view;

import java.util.List;

import cn.com.pujing.entity.SetMealBean;

/**
 * author : liguo
 * date : 2021/3/1 18:03
 * description :
 */
public interface RestRoutineView {

    public void getSetMealSuccess(List<SetMealBean> setMealBean);

    public void getDataFail(String msg);

}
