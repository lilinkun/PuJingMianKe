package cn.com.pujing.presenter;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.RestMealBean;
import cn.com.pujing.entity.UpdateBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.SettingView;

/**
 * author : liguo
 * date : 2021/4/22 10:19
 * description :
 */
public class SettingPresenter extends BasePresenter<SettingView> {

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
