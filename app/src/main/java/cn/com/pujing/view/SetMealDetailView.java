package cn.com.pujing.view;

import cn.com.pujing.entity.ChangeDataBean;
import cn.com.pujing.entity.RestMealBean;

/**
 * author : liguo
 * date : 2021/3/7 15:27
 * description :
 */
public interface SetMealDetailView {

    public void getSetMealDetail(RestMealBean restMealBean);
    public void getDataFail(String msg);
    public void onChangeSuccess(ChangeDataBean changeDataBean);

}
