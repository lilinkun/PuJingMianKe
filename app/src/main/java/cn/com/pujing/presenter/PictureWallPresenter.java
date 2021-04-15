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

}
