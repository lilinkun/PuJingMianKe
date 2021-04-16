package cn.com.pujing.presenter;

import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.FeedbackBean;
import cn.com.pujing.entity.LifeTypeBean;
import cn.com.pujing.entity.ServicePutawayManageTimeBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.LifeTypeView;

/**
 * author : liguo
 * date : 2021/3/26 16:20
 * description :
 */
public class LifeTypePresenter extends BasePresenter<LifeTypeView> {

    /**
     * 获取生活服务类型
     */
    public void getLifeType(int id,String date){
        PujingService.getLifeType(id,date)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<LifeTypeBean>() {

                    @Override
                    public void _onNext(LifeTypeBean lifeTypeBeans) {
                        getView().getLifeType(lifeTypeBeans);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getLifeTypeDataFail(errorMessage);
                    }

                });
    }
    /**
     * 获取生活服务预约时间
     */
    public void getLifeTime(String id,String itemsId,String date){
        PujingService.getLifeTime(id,itemsId,date)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<List<ServicePutawayManageTimeBean>>() {

                    @Override
                    public void _onNext(List<ServicePutawayManageTimeBean> lifeTypeBeans) {
                        getView().getLifeTimeSuccess(lifeTypeBeans);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        if (errorMessage.contains("The item is null")){
                            getView().getLifeTimeSuccess(null);
                        }else {
                            getView().getLifeTimeFail(errorMessage);
                        }
                    }

                });
    }
}
