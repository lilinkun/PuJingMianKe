package cn.com.pujing.presenter;

import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.FeedbackBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.FeedbackView;

/**
 * author : liguo
 * date : 2021/3/7 15:32
 * description :
 */
public class FeedbackPresenter extends BasePresenter<FeedbackView> {


    public void giveFeedback(){
        PujingService.giveFeedbackType()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<List<FeedbackBean>>() {

                    @Override
                    public void _onNext(List<FeedbackBean> feedbackBeans) {
                        getView().giveFeedbackType(feedbackBeans);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }

}
