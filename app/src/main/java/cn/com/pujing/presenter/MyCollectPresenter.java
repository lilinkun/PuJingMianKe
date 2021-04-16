package cn.com.pujing.presenter;

import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.BillsBean;
import cn.com.pujing.entity.CollectBean;
import cn.com.pujing.entity.PagesBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.MyCollectView;
import io.reactivex.internal.disposables.EmptyDisposable;

/**
 * author : liguo
 * date : 2021/4/16 10:52
 * description :
 */
public class MyCollectPresenter extends BasePresenter<MyCollectView> {

    /**
     * 获取收藏列表
     */
    public void getMyCollect(int page,int ordertypeId, String startDate, String endDate){
        PujingService.getMyCollect(page,ordertypeId,startDate, endDate)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<PagesBean<CollectBean>>() {
                    @Override
                    public void _onNext(PagesBean<CollectBean> billsBeans) {
                        getView().getMyCollectSuccess(billsBeans);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }

}
