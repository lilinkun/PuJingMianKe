package cn.com.pujing.presenter;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.RestBanquetsBean;
import cn.com.pujing.entity.RestOrderBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.RanquetsOrderView;

/**
 * author : liguo
 * date : 2021/3/10 18:34
 * description :
 */
public class RanquetsOrderPresenter extends BasePresenter<RanquetsOrderView> {

    /**
     * 查询订单
     */
    public void orderDetail(String orderNumber){

        PujingService.orderDetail(orderNumber)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<RestBanquetsBean>() {
                    @Override
                    public void _onNext(RestBanquetsBean restOrderBean) {
                        getView().RanquetsOrderDataSuccess(restOrderBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });

    }

    /**
     * 取消订单
     */
    public void restOrderClean(String orderNumber){

        PujingService.restOrderClean(orderNumber)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<Boolean>() {
                    @Override
                    public void _onNext(Boolean restOrderBean) {
                        getView().exitOrderSuccess(restOrderBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });

    }

}
