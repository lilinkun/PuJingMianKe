package cn.com.pujing.view;

import java.util.List;

import cn.com.pujing.entity.ServiceBean;

/**
 * author : liguo
 * date : 2021/3/26 15:40
 * description :
 */
public interface LifeServiceView {

    public void getDataFail(String msg);

    public void getServiceDataSuccess(List<ServiceBean> serviceBeans);
}
