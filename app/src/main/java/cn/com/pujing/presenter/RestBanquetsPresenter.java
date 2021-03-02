package cn.com.pujing.presenter;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.SetMealBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.RestBanquetsView;
import cn.com.pujing.view.RestRoutineView;

/**
 * author : liguo
 * date : 2021/3/1 18:03
 * description :
 */
public class RestBanquetsPresenter extends BasePresenter<RestBanquetsView> {

    public void getBanquetsData(String dateStr,int type){

        /*PujingService.getSetMealData(dateStr,type)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<SetMealBean>() {
                    @Override
                    public void _onNext(SetMealBean setMealBean) {
                        getView().getSetMealSuccess(setMealBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }
                });*/


    }


}
