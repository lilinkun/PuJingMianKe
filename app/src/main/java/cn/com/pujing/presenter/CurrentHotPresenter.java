package cn.com.pujing.presenter;

import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.HotActivityBean;
import cn.com.pujing.entity.MyCardBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.CurrentHotView;

/**
 * author : liguo
 * date : 2021/4/10 14:48
 * description :
 */
public class CurrentHotPresenter extends BasePresenter<CurrentHotView> {


    //热门活动
    public void getHotActivitiy(int page,String endTime,String startTime,String status,String type){

        if (status != null && status.equals("0")){
            status = null;
        }

        PujingService.getHotActivitiy(page,endTime,startTime,status,type)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<HotActivityBean>() {
                    @Override
                    public void _onNext(HotActivityBean hotActivityBean) {
                        getView().getHotActivitiySuccess(hotActivityBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getHotActivitiyFail(errorMessage);
                    }
                });
    }

}
