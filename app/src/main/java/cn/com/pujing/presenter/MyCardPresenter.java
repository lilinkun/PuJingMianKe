package cn.com.pujing.presenter;

import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.MyCardBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.MyCardView;

/**
 * author : liguo
 * date : 2021/3/29 11:05
 * description :
 */
public class MyCardPresenter extends BasePresenter<MyCardView> {


    //获取卡包
    public void getMycard(int type){

        PujingService.getMycard(type)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<List<MyCardBean>>() {
                    @Override
                    public void _onNext(List<MyCardBean> sendSms) {
                        getView().getCardDataSuccess(sendSms);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getCardDataFail(errorMessage);
                    }
                });
    }


}
