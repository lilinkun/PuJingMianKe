package cn.com.pujing.presenter;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.AddRestBean;
import cn.com.pujing.entity.BanquetBean;
import cn.com.pujing.entity.RoutineRecordBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.RestaurantView;

/**
 * author : liguo
 * date : 2021/3/16 14:27
 * description :
 */
public class RestaurantPresenter extends BasePresenter<RestaurantView> {

    //检查用户是否点击完常规餐
    public void getRoutineData(){

        PujingService.getRoutineData(0)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<RoutineRecordBean>() {
                    @Override
                    public void _onNext(RoutineRecordBean addRestBean) {
                        getView().getRestData(addRestBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }
                });
    }

}
