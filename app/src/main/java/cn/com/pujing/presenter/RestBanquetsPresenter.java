package cn.com.pujing.presenter;

import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.BanquetBean;
import cn.com.pujing.entity.SetMealBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.RestBanquetsView;
import cn.com.pujing.view.RestRoutineView;

/**
 * author : liguo
 * date : 2021/3/1 18:03
 * description :
 */
public class RestBanquetsPresenter extends BasePresenter<RestBanquetsView> {

    public void getBanquetsData(int type){

        PujingService.getBanquetsData(type)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<List<BanquetBean>>() {
                    @Override
                    public void _onNext(List<BanquetBean> banquetBeans) {
                        getView().getBanquetSuccess(banquetBeans);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }
                });


    }


}
