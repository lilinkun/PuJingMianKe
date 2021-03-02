package cn.com.pujing.presenter;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.MyInfoBean;
import cn.com.pujing.entity.PublicKey;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.MineView;

/**
 * author : liguo
 * date : 2021/3/1 12:15
 * description :
 */
public class MinePresenter extends BasePresenter<MineView> {

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
