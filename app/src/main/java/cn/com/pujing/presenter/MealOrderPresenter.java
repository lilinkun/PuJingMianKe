package cn.com.pujing.presenter;

import com.google.gson.Gson;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.RoutineRecordBean;
import cn.com.pujing.entity.SaveSetMealBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.MealOrderView;

/**
 * author : liguo
 * date : 2021/3/7 12:30
 * description :
 */
public class MealOrderPresenter extends BasePresenter<MealOrderView> {

    //检查用户是否点击完常规餐
    public void getRoutineData(){

        PujingService.getRoutineData("")
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

    /**
     * 提交常规餐
     */
    public void submitSetMeal(){

        PujingService.submitRest()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<Boolean>() {
                    @Override
                    public void _onNext(Boolean aBoolean) {
                        getView().submitSuccess(aBoolean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }
                });

    }


}
