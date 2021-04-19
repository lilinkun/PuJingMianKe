package cn.com.pujing.presenter;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.HistoryActivitiesBean;
import cn.com.pujing.entity.PagesBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.HistoryActivitiesView;

/**
 * author : liguo
 * date : 2021/3/1 14:54
 * description :
 */
public class HistoryActivitiesPresenter extends BasePresenter<HistoryActivitiesView> {

    public void getActivitiesDataSuccess(int page){


        PujingService.getHistoryActivitiy(page)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<PagesBean<HistoryActivitiesBean>>() {
                    @Override
                    public void _onNext(PagesBean<HistoryActivitiesBean> historyActivitiesBean) {
                        getView().getHistoryDataSuccess(historyActivitiesBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }
                });


    }

}
