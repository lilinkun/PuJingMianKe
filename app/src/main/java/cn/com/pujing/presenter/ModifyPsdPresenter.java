package cn.com.pujing.presenter;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.HistoryActivitiesBean;
import cn.com.pujing.entity.PagesBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.ModifyPsdView;

/**
 * author : liguo
 * date : 2021/4/21 11:37
 * description :
 */
public class ModifyPsdPresenter extends BasePresenter<ModifyPsdView> {


    /**
     * 修改密码
     */
    public void modifyPsd(String oldPsd,String newPsd){

        PujingService.modifyPsd(oldPsd,newPsd)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<Object>() {
                    @Override
                    public void _onNext(Object o) {
                        getView().modifyPsdSuccess(o);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }
                });


    }
}
