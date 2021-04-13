package cn.com.pujing.presenter;

import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.ActivityTypeBean;
import cn.com.pujing.entity.MyBillBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.ActivitiesView;

/**
 * author : liguo
 * date : 2021/4/13 16:37
 * description :
 */
public class ActivitiesPresenter extends BasePresenter<ActivitiesView> {

    /**
     * 获取活动分类
     */
    public void getActivityType(){
        PujingService.getActivityType()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<List<ActivityTypeBean>>() {
                    @Override
                    public void _onNext(List<ActivityTypeBean> activityTypeBeans) {
                        getView().getActivityTypeSuccess(activityTypeBeans);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }

}
