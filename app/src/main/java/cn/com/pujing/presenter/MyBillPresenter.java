package cn.com.pujing.presenter;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.MyInfoBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.MyBillView;

/**
 * author : liguo
 * date : 2021/4/6 20:37
 * description :
 */
public class MyBillPresenter extends BasePresenter<MyBillView> {


    /**
     * 获取上月和这月账单
     */
    public void getMyCurrentBills(){
        PujingService.myCurrentBills()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<Object>() {
                    @Override
                    public void _onNext(Object o) {
                        getView().getMyCurrentBillsSuccess(0);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }

}
