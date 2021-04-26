package cn.com.pujing.presenter;

import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.BillsBean;
import cn.com.pujing.entity.BillsItemBean;
import cn.com.pujing.entity.MyBillBean;
import cn.com.pujing.entity.MyInfoBean;
import cn.com.pujing.entity.OrderItemBean;
import cn.com.pujing.entity.PagesBean;
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
                .subscribe(new RxObserver<List<BillsBean>>() {
                    @Override
                    public void _onNext(List<BillsBean> billsBeans) {
                        getView().getMyCurrentBillsSuccess(billsBeans);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }


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
