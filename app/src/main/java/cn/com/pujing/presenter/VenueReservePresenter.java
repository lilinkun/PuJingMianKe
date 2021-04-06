package cn.com.pujing.presenter;

import java.util.ArrayList;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.DeviceBean;
import cn.com.pujing.entity.ReserveDeviceBean;
import cn.com.pujing.entity.VenueBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.VenueReserveView;

/**
 * author : liguo
 * date : 2021/4/2 18:13
 * description :
 */
public class VenueReservePresenter extends BasePresenter<VenueReserveView> {

    /**
     * 获取所有设备
     * @param venueId
     */
    public void getDevice(String venueId){
        PujingService.getDevice(venueId)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<ArrayList<DeviceBean>>() {
                    @Override
                    public void _onNext(ArrayList<DeviceBean> venueBeans) {
                        getView().getDeviceListSuccess(venueBeans);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDeviceFail(errorMessage);
                    }

                });
    }
    /**
     * 预约时间
     * @param venueId
     */
    public void reserveDevice(String venueId,String deviceId,String reserveDate){

        PujingService.reserveDevice(venueId,deviceId,reserveDate)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<ReserveDeviceBean>() {
                    @Override
                    public void _onNext(ReserveDeviceBean venueBeans) {
                        getView().getReserveDevice(venueBeans);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getReserveDeviceFail(errorMessage);
                    }

                });

    }
}
