package cn.com.pujing.presenter;

import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.FeedbackBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.VenueReserveSureView;

/**
 * author : liguo
 * date : 2021/4/6 17:06
 * description :
 */
public class VenueReserveSurePresenter extends BasePresenter<VenueReserveSureView> {


    /**
     * 确认预约场馆
     */
    public void reserveSure(String venueId, String deviceId, String reserveDate,String reserveTime){
        PujingService.reserveSure(venueId, deviceId, reserveDate,reserveTime)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<Object>() {

                    @Override
                    public void _onNext(Object o) {
                        getView().venueReserveSuccess(o);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        if (errorMessage.contains("The item is null")){
                            getView().venueReserveSuccess(null);
                        }else {
                            getView().venueReserveFail(errorMessage);
                        }
                    }

                });
    }

}
