package cn.com.pujing.presenter;

import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.ActivityTypeBean;
import cn.com.pujing.entity.UpdateBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.MainView;
import cn.jpush.android.api.JPushInterface;

/**
 * author : liguo
 * date : 2021/4/20 13:55
 * description :
 */
public class MainPresenter extends BasePresenter<MainView> {

    /**
     * 推送信息
     */
    public void sendPushDevice(String pushId){
        PujingService.sendPushDevice(pushId)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<Object>() {
                    @Override
                    public void _onNext(Object o) {
                        getView().sendPushDeviceSuccess(o);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().sendPushDeviceFail(errorMessage);
                    }

                });
    }


    /**
     * 检查更新
     */
    public void checkUpdate(){
        PujingService.updateApk()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<UpdateBean>() {
                    @Override
                    public void _onNext(UpdateBean updateBean) {
                        getView().getUpdateDataSuccess(updateBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }
}
