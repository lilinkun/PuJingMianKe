package cn.com.pujing.presenter;

import java.util.ArrayList;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.RestMealBean;
import cn.com.pujing.entity.VenueBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.VenueView;

/**
 * author : liguo
 * date : 2021/4/2 13:43
 * description :
 */
public class VenuePresenter extends BasePresenter<VenueView> {

    public void getVenueType(){
        PujingService.getVenueType()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<ArrayList<VenueBean>>() {
                    @Override
                    public void _onNext(ArrayList<VenueBean> venueBeans) {
                        getView().getVenueType(venueBeans);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getVenueFail(errorMessage);
                    }

                });
    }
}
