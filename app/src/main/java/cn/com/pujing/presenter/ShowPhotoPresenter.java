package cn.com.pujing.presenter;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.PhotoBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.ShowPhotoView;

/**
 * author : liguo
 * date : 2021/4/19 17:43
 * description :
 */
public class ShowPhotoPresenter extends BasePresenter<ShowPhotoView> {

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
