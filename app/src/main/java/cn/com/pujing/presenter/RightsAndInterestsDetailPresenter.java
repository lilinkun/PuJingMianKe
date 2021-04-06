package cn.com.pujing.presenter;

import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.RightsAndInterestsBean;
import cn.com.pujing.entity.RightsVoucherVoBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.RightsAndInterestsDetailView;

/**
 * author : liguo
 * date : 2021/3/30 13:50
 * description :
 */
public class RightsAndInterestsDetailPresenter extends BasePresenter<RightsAndInterestsDetailView> {

    /**
     * 获取权益包
     */
    public void getRightsAndInterestsData(String id){
        PujingService.getRightsAndInterests(id)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<RightsAndInterestsBean<List<RightsVoucherVoBean>>>() {
                    @Override
                    public void _onNext(RightsAndInterestsBean<List<RightsVoucherVoBean>> rightsAndInterestsBeans) {
                        getView().getRightsAndInterestsListSuccess(rightsAndInterestsBeans);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }

}
