package cn.com.pujing.presenter;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.MyInfoBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.ProfileView;

/**
 * author : liguo
 * date : 2021/3/1 14:18
 * description :
 */
public class ProfilePresenter extends BasePresenter<ProfileView> {

    public void getMyInfo(){
        PujingService.getMyInfo()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<MyInfoBean>() {
                    @Override
                    public void _onNext(MyInfoBean myInfoBean) {
                        getView().getMyInfoSuccess(myInfoBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }

}
