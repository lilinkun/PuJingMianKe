package cn.com.pujing.presenter;

import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.ServiceBean;
import cn.com.pujing.entity.VipBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.HealthCenterView;

/**
 * author : liguo
 * date : 2021/3/26 13:34
 * description :
 */
public class HealthCenterPresenter extends BasePresenter<HealthCenterView> {


    //健管中心
    public void getService(){

        PujingService.getService(2)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<List<ServiceBean>>() {
                    @Override
                    public void _onNext(List<ServiceBean> serviceBeans) {
                        getView().getServiceDataSuccess(serviceBeans);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });

    }

    //是否vip
    public void getVip(){

        PujingService.vipExpireandDiscount()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<VipBean>() {
                    @Override
                    public void _onNext(VipBean vipBean) {
                        getView().getVip(vipBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });

    }
}
