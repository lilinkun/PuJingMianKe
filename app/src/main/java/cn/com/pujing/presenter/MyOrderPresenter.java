package cn.com.pujing.presenter;

import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.OrderItemBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.MyOrderView;

/**
 * author : liguo
 * date : 2021/3/19 15:13
 * description :
 */
public class MyOrderPresenter extends BasePresenter<MyOrderView> {

    public void getMyOrder(int page,int ordertypeId, String startDate, String endDate){
        PujingService.getMyOrder(page,ordertypeId,startDate,endDate)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<OrderItemBean>() {
                    @Override
                    public void _onNext(OrderItemBean orderItemBean) {
                        getView().getMyOrderSuccess(orderItemBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }


}
