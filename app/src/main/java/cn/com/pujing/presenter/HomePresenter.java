package cn.com.pujing.presenter;

import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.BannerBean;
import cn.com.pujing.entity.NotifyInfoBean;
import cn.com.pujing.entity.PhotoBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.HomeView;

/**
 * author : liguo
 * date : 2021/2/26 17:07
 * description :
 */
public class HomePresenter extends BasePresenter<HomeView> {

    //获取轮播图数据
    public void getBannerData() {

        PujingService.getBannerData()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<List<BannerBean>>() {
                    @Override
                    public void _onNext(List<BannerBean> bannerBeans) {
                        getView().getBannerDataSuccess(bannerBeans);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataError(errorMessage);
                    }
                });
    }

    //获取通知数据
    public void getNoticeData(){

        PujingService.getHomeNotice()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<NotifyInfoBean>() {
                    @Override
                    public void _onNext(NotifyInfoBean notifyInfoBean) {
                        getView().getNoticeDataSuccess(notifyInfoBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataError(errorMessage);
                    }
                });
    }

    public void getHomePhoto(){

        PujingService.getHomePhoto()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<PhotoBean>() {
                    @Override
                    public void _onNext(PhotoBean photoBean) {
                        getView().getPhotoDataSuccess(photoBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataError(errorMessage);
                    }
                });
    }


}
