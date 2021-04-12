package cn.com.pujing.presenter;

import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.HistoryBillsBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.BillHistoryView;

/**
 * author : liguo
 * date : 2021/4/12 16:08
 * description :
 */
public class BillHistoryPresenter extends BasePresenter<BillHistoryView> {


    //获取历史账单
    public void getBillData(){

        PujingService.getBillData()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<List<HistoryBillsBean>>() {
                    @Override
                    public void _onNext(List<HistoryBillsBean> historyBillsBeans) {
                        getView().getDataSuccess(historyBillsBeans);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }
                });
    }

}
