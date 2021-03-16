package cn.com.pujing.presenter;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.ChangeDataBean;
import cn.com.pujing.entity.RestDetailBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.RestDetailView;

/**
 * author : liguo
 * date : 2021/3/9 17:11
 * description :
 */
public class RestDetailPresenter extends BasePresenter<RestDetailView> {

    //获取菜品详情
    public void getRestDetail(int id){

        PujingService.queryRestDetail(id)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<RestDetailBean>() {
                    @Override
                    public void _onNext(RestDetailBean restDetailBean) {
                        getView().getRestDetail(restDetailBean);
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
