package cn.com.pujing.presenter;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.ChangeDataBean;
import cn.com.pujing.entity.RestMealBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.SetMealDetailView;

/**
 * author : liguo
 * date : 2021/3/7 15:28
 * description :
 */
public class SetMealDetailPresenter extends BasePresenter<SetMealDetailView> {

    /**
     *  查询套餐详情
     */
    public void queryRestMealDetail(int id){
        PujingService.queryRestMealDetail(id)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<RestMealBean>() {
                    @Override
                    public void _onNext(RestMealBean restMealBean) {
                        getView().getSetMealDetail(restMealBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }



    /**
     *点餐数据发生变化的时候
     */
    public void restDataChange(int menuItemId,int quantity,int type){

        PujingService.addShoppingCart(menuItemId,quantity,type)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<ChangeDataBean>() {
                    @Override
                    public void _onNext(ChangeDataBean changeDataBean) {
                        getView().onChangeSuccess(changeDataBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }
                });

    }

}
