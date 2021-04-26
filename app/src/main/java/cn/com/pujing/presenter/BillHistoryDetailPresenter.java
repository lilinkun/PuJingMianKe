package cn.com.pujing.presenter;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.MyBillBean;
import cn.com.pujing.entity.PagesBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.BillHistoryDetailView;

/**
 * author : liguo
 * date : 2021/4/12 18:41
 * description :
 */
public class BillHistoryDetailPresenter extends BasePresenter<BillHistoryDetailView> {


    /**
     * 获取账单详情
     */
    public void getMyBills(String billId,int page){
        PujingService.queryBills(billId,page)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<PagesBean<MyBillBean>>() {
                    @Override
                    public void _onNext(PagesBean<MyBillBean> orderItemBean) {
                        getView().getMyBillsSuccess(orderItemBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }
}
