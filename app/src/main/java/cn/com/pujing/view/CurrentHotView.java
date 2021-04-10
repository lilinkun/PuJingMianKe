package cn.com.pujing.view;

import cn.com.pujing.entity.HotActivityBean;

/**
 * author : liguo
 * date : 2021/4/10 14:48
 * description :
 */
public interface CurrentHotView {

    public void getHotActivitiySuccess(HotActivityBean hotActivityBean);

    public void getHotActivitiyFail(String msg);

}
