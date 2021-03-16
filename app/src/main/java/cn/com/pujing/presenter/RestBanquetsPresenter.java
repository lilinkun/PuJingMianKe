package cn.com.pujing.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.AddRestBean;
import cn.com.pujing.entity.BanquetBean;
import cn.com.pujing.entity.ChangeDataBean;
import cn.com.pujing.entity.SetMealBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.RestBanquetsView;
import cn.com.pujing.view.RestRoutineView;

/**
 * author : liguo
 * date : 2021/3/1 18:03
 * description :
 */
public class RestBanquetsPresenter extends BasePresenter<RestBanquetsView> {


    //得到零点餐和宴会餐数据 菜单类别2宴会菜单1零点
    public void getBanquetsData(int type){

        PujingService.getBanquetsData(type)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<BanquetBean>() {
                    @Override
                    public void _onNext(BanquetBean banquetBeans) {
                        getView().getBanquetSuccess(banquetBeans);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }
                });
    }

    //点餐数据发生变化的时候
    public void restDataChange(int menuItemId,int quantity,int type){

        PujingService.addShoppingCart(menuItemId,quantity,type)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<ChangeDataBean>() {
                    @Override
                    public void _onNext(ChangeDataBean changeDataBean) {
                        getView().onAddSuccess(changeDataBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }
                });

    }

    //查询购物车
    public void queryShoppingCart(int type,int rest_type){

        PujingService.queryShoppingCart(rest_type)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<ChangeDataBean>() {
                    @Override
                    public void _onNext(ChangeDataBean changeDataBean) {
                        getView().queryShoppingCartSuccess(changeDataBean,type);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }
                });

    }


    /**
     * 删除购物车
     * @param type
     */
    public void clearMyShoppingCart(int type){

        PujingService.clearMyShoppingCart(type)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<ChangeDataBean>() {
                    @Override
                    public void _onNext(ChangeDataBean changeDataBean) {
                        getView().clearMyShoppingCart(changeDataBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }
                });

    }


    /**
     * 加菜
     */
    public void addRest(ChangeDataBean changeDataBean,String orderNumber){

        AddRestBean addRestBean = new AddRestBean();

        for (int i = 0; i <changeDataBean.detailList.size();i++ ){
            changeDataBean.detailList.get(i).number = changeDataBean.detailList.get(i).quantity;
        }

        addRestBean.setFoodList(changeDataBean.detailList);
        addRestBean.setOrderNumber(orderNumber);

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(addRestBean);


        PujingService.addRest(json)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<Boolean>() {
                    @Override
                    public void _onNext(Boolean aBoolean) {
                        getView().addRestSuccess(aBoolean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }
                });

    }


}
