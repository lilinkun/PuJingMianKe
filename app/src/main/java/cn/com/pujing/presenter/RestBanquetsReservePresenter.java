package cn.com.pujing.presenter;

import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.ChangeDataBean;
import cn.com.pujing.entity.FeedbackBean;
import cn.com.pujing.entity.RestMealTypeBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.RestBanquetsReserveView;

/**
 * author : liguo
 * date : 2021/3/9 20:53
 * description :
 */
public class RestBanquetsReservePresenter extends BasePresenter<RestBanquetsReserveView> {


    //查询购物车
    public void queryShoppingCart(int rest_type){

        PujingService.queryShoppingCart(rest_type)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<ChangeDataBean>() {
                    @Override
                    public void _onNext(ChangeDataBean changeDataBean) {
                        getView().queryShoppingCartSuccess(changeDataBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }
                });
    }


    /**
     * 删除购物车
     * @param type
     */
    public void clearMyShoppingCart(int type){

        PujingService.clearMyShoppingCart(type)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<ChangeDataBean>() {
                    @Override
                    public void _onNext(ChangeDataBean changeDataBean) {
                        getView().clearMyShoppingCart(changeDataBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }
                });

    }


    /**
     * 下单
     */
    public void restOrder(String orderInfo){

        PujingService.restOrder(orderInfo)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<String>() {
                    @Override
                    public void _onNext(String orderNumber) {
                        getView().getOrderNumber(orderNumber);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getOrderNumberFail(errorMessage);
                    }
                });

    }

    /**
     * 获取餐次
     */
    public void getMealType(){

        PujingService.queryMealTimesType()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<List<RestMealTypeBean>>() {
                    @Override
                    public void _onNext(List<RestMealTypeBean> changeDataBean) {
                        getView().getMealType(changeDataBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }
                });
    }

}
