package cn.com.pujing.presenter;

import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.MyInfoBean;
import cn.com.pujing.entity.RightsAndInterestsBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.RightsAndInterestsView;

/**
 * author : liguo
 * date : 2021/3/29 16:52
 * description :
 */
public class RightsAndInterestsPresenter extends BasePresenter<RightsAndInterestsView> {

    /**
     * 获取权益包
     */
    public void getRightsAndInterestsData(){
        PujingService.getRightsAndInterestsList()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<List<RightsAndInterestsBean>>() {
                    @Override
                    public void _onNext(List<RightsAndInterestsBean> rightsAndInterestsBeans) {
                        getView().getRightsAndInterestsListSuccess(rightsAndInterestsBeans);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }

}
