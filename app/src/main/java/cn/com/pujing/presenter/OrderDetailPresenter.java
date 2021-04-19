package cn.com.pujing.presenter;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.OrderDetailBean;
import cn.com.pujing.entity.VenueDetailBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.OrderDetailView;

/**
 * author : liguo
 * date : 2021/4/9 15:55
 * description :
 */
public class OrderDetailPresenter extends BasePresenter<OrderDetailView> {


    /**
     * 预约服务订单查询
     */
    public void queryReserveServiceOrder(String orderNumber){
        PujingService.queryReserveServiceOrder(orderNumber)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<OrderDetailBean>() {

                    @Override
                    public void _onNext(OrderDetailBean orderDetailBean) {
                        getView().querySuccess(orderDetailBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().queryFail(errorMessage);
                    }

                });
    }

    /**
     * 预约服务订单查询
     */
    public void searchVenueDetail(String orderNumber){
        PujingService.searchVenueDetail(orderNumber)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<VenueDetailBean>() {

                    @Override
                    public void _onNext(VenueDetailBean venueDetailBean) {
                        getView().queryVenueSuccess(venueDetailBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().queryFail(errorMessage);
                    }

                });
    }

}
