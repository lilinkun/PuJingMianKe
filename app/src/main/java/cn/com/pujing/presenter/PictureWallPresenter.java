package cn.com.pujing.presenter;

import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.ActivityTypeBean;
import cn.com.pujing.entity.PagesBean;
import cn.com.pujing.entity.PictureWallBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.PictureWallView;

/**
 * author : liguo
 * date : 2021/4/15 15:36
 * description :
 */
public class PictureWallPresenter extends BasePresenter<PictureWallView> {

    /**
     * 获取照片墙信息
     */
    public void showPicInfoList(int page){
        PujingService.showPicInfoList(page)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<PagesBean<PictureWallBean>>() {
                    @Override
                    public void _onNext(PagesBean<PictureWallBean> activityTypeBeans) {
                        getView().showPicInfoListSuccess(activityTypeBeans);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }

    /**
     * 添加收藏
     */
    public void addCollect(int id){
        PujingService.addCollect(id)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<Object>() {
                    @Override
                    public void _onNext(Object o) {
                        getView().addCollectSuccess(o);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }
    /**
     * 取消收藏
     */
    public void cancelCollect(int id){
        PujingService.cancelCollect(id)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<Object>() {
                    @Override
                    public void _onNext(Object o) {
                        getView().cancelCollectSuccess(o);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }

    /**
     * 取消收藏
     */
    public void doLike(int id){
        PujingService.doLike(id)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<Object>() {
                    @Override
                    public void _onNext(Object o) {
                        getView().doLikeSuccess(o);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }

    /**
     * 取消收藏
     */
    public void unDoLike(int id){
        PujingService.unDoLike(id)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<Object>() {
                    @Override
                    public void _onNext(Object o) {
                        getView().unDoLikeSuccess(o);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }

}
