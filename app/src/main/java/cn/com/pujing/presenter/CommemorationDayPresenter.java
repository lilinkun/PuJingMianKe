package cn.com.pujing.presenter;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.CommemorationDayBean;
import cn.com.pujing.entity.MyInfoBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.CommemorationDayView;

/**
 * author : liguo
 * date : 2021/4/13 9:31
 * description :
 */
public class CommemorationDayPresenter extends BasePresenter<CommemorationDayView> {


    /**
     * 获取纪念日
     * @param page
     */
    public void getCommemorationDay(int page){
        PujingService.getCommemorationDay(page)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<CommemorationDayBean>() {
                    @Override
                    public void _onNext(CommemorationDayBean commemorationDayBean) {
                        getView().getCommemorationDaySuccess(commemorationDayBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }

    /**
     * 增加纪念日
     */
    public void addCommemorationDay(String commemorationDay,String commemorationName,int type){
        PujingService.addCommemorationDay(commemorationDay,commemorationName,type)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<Object>() {
                    @Override
                    public void _onNext(Object o) {
                        getView().saveCommemorationDaySuccess(o);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }


    /**
     * 增加纪念日
     */
    public void deleteCommemorationDay(String commemorationDayId){
        PujingService.deleteCommemorationDay(commemorationDayId)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<Object>() {
                    @Override
                    public void _onNext(Object o) {
                        getView().deleteCommemorationDaySuccess(o);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }


}
