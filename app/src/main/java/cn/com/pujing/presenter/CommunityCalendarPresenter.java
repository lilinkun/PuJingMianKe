package cn.com.pujing.presenter;

import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.QuerySelectDayBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.CommunityCalendarView;

/**
 * author : liguo
 * date : 2021/3/5 18:19
 * description :
 */
public class CommunityCalendarPresenter extends BasePresenter<CommunityCalendarView> {

    public void getCommunityData(String startTime,String endTime,int type){

        PujingService.getCommunityData(startTime,endTime,type)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<List<Long>>() {

                    @Override
                    public void _onNext(List<Long> longs) {
                        getView().getCommunityDataSuccess(longs);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });


    }


    public void querySelectDay(String dayTime,int type){

        PujingService.querySelectDay(dayTime,type)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<List<QuerySelectDayBean>>() {

                    @Override
                    public void _onNext(List<QuerySelectDayBean> querySelectDayBeans) {
                        getView().getDaySuccess(querySelectDayBeans);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }



}
