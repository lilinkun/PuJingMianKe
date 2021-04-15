package cn.com.pujing.presenter;

import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.BillsBean;
import cn.com.pujing.entity.MyFeedbackBean;
import cn.com.pujing.entity.PagesBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.MyFeedBackView;

/**
 * author : liguo
 * date : 2021/4/15 13:52
 * description :
 */
public class MyFeedBackPresenter extends BasePresenter<MyFeedBackView> {


    /**
     * 获取意见反馈列表
     */
    public void getFeedbakList(int page){
        PujingService.getFeedbakList(page)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<PagesBean<MyFeedbackBean>>() {
                    @Override
                    public void _onNext(PagesBean<MyFeedbackBean> pagesBean) {
                        getView().getFeedbakListSuccess(pagesBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }

}
