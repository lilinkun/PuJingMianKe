package cn.com.pujing.view;

import cn.com.pujing.entity.UpdateBean;

/**
 * author : liguo
 * date : 2021/4/20 13:55
 * description :
 */
public interface MainView {

    public void sendPushDeviceSuccess(Object o);
    public void sendPushDeviceFail(String msg);


    public void getUpdateDataSuccess(UpdateBean updateBean);

    public void getDataFail(String msg);

}
