package cn.com.pujing.presenter;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.MessageBean;
import cn.com.pujing.entity.OrderItemBean;
import cn.com.pujing.entity.PagesBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.MessageView;

/**
 * author : liguo
 * date : 2021/4/18 12:30
 * description :
 */
public class MessagePresenter extends BasePresenter<MessageView> {

    /**
     * 消息
     * @param page
     */
    public void getMessageList(int page){
        PujingService.getMessageList(page)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<PagesBean<MessageBean>>() {
                    @Override
                    public void _onNext(PagesBean<MessageBean> orderItemBean) {
                        getView().getMessageSuccess(orderItemBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }

    /**
     * 读取消息
     * @param ids
     */
    public void getMessageList(String ids){
        PujingService.getReadMessage(ids)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<Object>() {
                    @Override
                    public void _onNext(Object o) {
                        getView().getReadMessageSuccess(o);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getReadMessageFail(errorMessage);
                    }

                });
    }

}
