package cn.com.pujing.view;

import java.util.List;

import cn.com.pujing.entity.ServiceBean;
import cn.com.pujing.entity.VipBean;

/**
 * author : liguo
 * date : 2021/3/26 13:34
 * description :
 */
public interface HealthCenterView {

    public void getDataFail(String msg);

    public void getServiceDataSuccess(List<ServiceBean> serviceBeans);

    public void getVip(VipBean vipBean);

}
