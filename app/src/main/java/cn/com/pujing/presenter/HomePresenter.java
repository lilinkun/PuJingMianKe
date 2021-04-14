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
import io.reactivex.disposables.Disposable;

/**
 * author : liguo
 * date : 2021/2/26 17:07
 * description :
 */
public class HomePresenter extends BasePresenter<HomeView> {

    //获取轮播图数据
    public void getBannerData() {

        PujingService.getBannerData(1)
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

    /**
     * 获取首页的照片墙
     */
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
                        if (!errorMessage.contains("null")) {
                            getView().getDataError(errorMessage);
                        }
                    }
                });
    }

    /**
     * 通过id查询照片墙详情
     */
    public void queryPhotoWall(String id){

        PujingService.queryPhotoWall(id)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<PhotoBean>() {
                    @Override
                    public void _onNext(PhotoBean photoBean) {
                        getView().queryPhotoWall(photoBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataError(errorMessage);
                    }
                });
    }


}
