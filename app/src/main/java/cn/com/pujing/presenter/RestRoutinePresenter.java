package cn.com.pujing.presenter;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.RestTypeBean;
import cn.com.pujing.entity.SaveSetMealBean;
import cn.com.pujing.entity.SetMealBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.RestRoutineView;

/**
 * author : liguo
 * date : 2021/3/1 18:03
 * description :
 */
public class RestRoutinePresenter extends BasePresenter<RestRoutineView> {

    /**
     * 获取套餐数据
     * @param dateStr
     * @param type
     */
    public void getSetMealData(String dateStr,int type){

        PujingService.getSetMealData(dateStr,type)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<List<SetMealBean>>() {
                    @Override
                    public void _onNext(List<SetMealBean> setMealBean) {
                        getView().getSetMealSuccess(setMealBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }
                });


    }

    public void getSetMealTypeData(){

        PujingService.getSetMealTypeData()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<List<RestTypeBean>>() {
                    @Override
                    public void _onNext(List<RestTypeBean> setMealBean) {
                        getView().getSetMealTypeSuccess(setMealBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }
                });

    }

    /**
     * 保存套餐
     */
    public void saveSetMeal(SetMealBean setMealBean,String time,int type){

        SaveSetMealBean saveSetMealBean = new SaveSetMealBean();

        List<SaveSetMealBean.CycleMealVoList> cycleMealVoLists = new ArrayList<>();

        SaveSetMealBean.CycleMealVoList cycleMealVoList = saveSetMealBean.new CycleMealVoList();

        cycleMealVoList.setTime(time);
        cycleMealVoList.setMealName(setMealBean.getMealName());
        cycleMealVoList.setType(type);
        cycleMealVoList.setMealIds(setMealBean.getId()+"");

        cycleMealVoList.setCategoryList(setMealBean.getFoodDetailVoList());

        cycleMealVoLists.add(cycleMealVoList);

        saveSetMealBean.setCycleMealVoList(cycleMealVoLists);


        Gson gson = new Gson();
        String json = gson.toJson(saveSetMealBean);

        PujingService.saveSetMealData(json)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<List<RestTypeBean>>() {
                    @Override
                    public void _onNext(List<RestTypeBean> setMealBean) {
                        getView().getSetMealTypeSuccess(setMealBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }
                });

    }


}
