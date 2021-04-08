package cn.com.pujing.view;

import java.util.List;

import cn.com.pujing.entity.MyCardBean;

/**
 * author : liguo
 * date : 2021/3/26 17:52
 * description :
 */
public interface ServiceReserveView {

    public void serviceReserveSuccess();
    public void serviceReserveFail(String msg);


    public void getCardDataSuccess(List<MyCardBean> myCardBeans);
    public void getCardDataFail(String msg);
}
