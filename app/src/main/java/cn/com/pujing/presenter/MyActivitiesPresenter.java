package cn.com.pujing.presenter;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.HistoryActivitiesBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.HistoryActivitiesView;
import cn.com.pujing.view.MyActivitiesView;

/**
 * author : liguo
 * date : 2021/3/1 14:54
 * description :
 */
public class MyActivitiesPresenter extends BasePresenter<MyActivitiesView> {

    public void getMyActivitiesData(){

        PujingService.getMyActivitiy()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<HistoryActivitiesBean>() {
                    @Override
                    public void _onNext(HistoryActivitiesBean historyActivitiesBean) {
                        getView().getHistoryDataSuccess(historyActivitiesBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }
                });


    }

}
