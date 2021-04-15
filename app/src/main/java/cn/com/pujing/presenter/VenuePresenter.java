package cn.com.pujing.presenter;

import java.util.ArrayList;
import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.BannerBean;
import cn.com.pujing.entity.RestMealBean;
import cn.com.pujing.entity.VenueBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.VenueView;

/**
 * author : liguo
 * date : 2021/4/2 13:43
 * description :
 */
public class VenuePresenter extends BasePresenter<VenueView> {

    public void getVenueType(){
        PujingService.getVenueType()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<VenueBean>() {
                    @Override
                    public void _onNext(VenueBean venueBeans) {
                        getView().getVenueType(venueBeans);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getVenueFail(errorMessage);
                    }

                });
    }

    //获取轮播图数据
    public void getBannerData() {

        PujingService.getBannerData(4)
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
