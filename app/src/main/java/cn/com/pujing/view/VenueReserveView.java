package cn.com.pujing.view;

import java.util.List;

import cn.com.pujing.entity.DeviceBean;
import cn.com.pujing.entity.ReserveDeviceBean;

/**
 * author : liguo
 * date : 2021/4/2 18:13
 * description :
 */
public interface VenueReserveView {
    public void getDeviceListSuccess(List<DeviceBean> deviceBeans);

    public void getDeviceFail(String msg);

    public void getReserveDevice(ReserveDeviceBean reserveDeviceBean);

    public void getReserveDeviceFail(String msg);
}
