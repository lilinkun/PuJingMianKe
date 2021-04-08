package cn.com.pujing.presenter;

import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.MyCardBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.ServiceReserveView;

/**
 * author : liguo
 * date : 2021/3/26 17:51
 * description :
 */
public class ServiceReservePresenter extends BasePresenter<ServiceReserveView> {

    /**
     * 确认预约服务
     */
    public void reserveService(String orderingDate, String orderingTime, String basicServiceItemsId,String basicServiceItemsName
            , String serviceBasicId,String category,String customerVoucherId){
        PujingService.reserveService(orderingDate, orderingTime, basicServiceItemsId,basicServiceItemsName,serviceBasicId,category,customerVoucherId)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<Object>() {

                    @Override
                    public void _onNext(Object aBoolean) {
                        getView().serviceReserveSuccess();
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().serviceReserveFail(errorMessage);
                    }

                });
    }


    //获取卡包
    public void useCoupon(String date,String serviceItemId){

        PujingService.useCoupon(date,serviceItemId)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<List<MyCardBean>>() {
                    @Override
                    public void _onNext(List<MyCardBean> sendSms) {
                        getView().getCardDataSuccess(sendSms);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getCardDataFail(errorMessage);
                    }
                });
    }

}
