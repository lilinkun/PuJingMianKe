package cn.com.pujing.presenter;

import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.BannerBean;
import cn.com.pujing.entity.RestDetailBean;
import cn.com.pujing.entity.ServiceBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.LifeServiceView;

/**
 * author : liguo
 * date : 2021/3/26 15:40
 * description :
 */
public class LifeServicePresenter extends BasePresenter<LifeServiceView> {


    //服务
    public void getService(){

        PujingService.getService(1)
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

    //获取轮播图数据
    public void getBannerData() {

        PujingService.getBannerData(2)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<List<BannerBean>>() {
                    @Override
                    public void _onNext(List<BannerBean> bannerBeans) {
                        getView().getBannerDataSuccess(bannerBeans);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getBannerDataFail(errorMessage);
                    }

                });
    }


}
