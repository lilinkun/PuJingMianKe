package cn.com.pujing.presenter;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.MyFeedbackBean;
import cn.com.pujing.entity.PagesBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.MyFeedbackDetailView;

/**
 * author : liguo
 * date : 2021/4/15 18:43
 * description :
 */
public class MyFeedbackDetailPresenter extends BasePresenter<MyFeedbackDetailView> {


    /**
     * 获取意见反馈列表
     */
    public void getFeedbakDetail(int id){
        PujingService.getFeedbakDetail(id)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<MyFeedbackBean>() {
                    @Override
                    public void _onNext(MyFeedbackBean o) {
                        getView().getMyFeedbackDetailSuccess(o);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }


}
