package cn.com.pujing.presenter;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.AddRestBean;
import cn.com.pujing.entity.RestTypeBean;
import cn.com.pujing.entity.RoutineRecordBean;
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
    public void getSetMealData(String dateStr,String type,boolean isNew){

        PujingService.getSetMealData(dateStr,type)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<List<SetMealBean>>() {
                    @Override
                    public void _onNext(List<SetMealBean> setMealBean) {
                        getView().getSetMealSuccess(setMealBean,isNew);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }
                });
    }

    /**
     * 获取餐次
     */
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
    public void saveSetMeal(SetMealBean setMealBean,String time,String type,int id){

        SaveSetMealBean saveSetMealBean = new SaveSetMealBean();

        List<SaveSetMealBean.CycleMealVoList> cycleMealVoLists = new ArrayList<>();

        SaveSetMealBean.CycleMealVoList cycleMealVoList = saveSetMealBean.new CycleMealVoList();

        cycleMealVoList.setTime(time);
        cycleMealVoList.setMealName(setMealBean.getMealName());
        cycleMealVoList.setType(Integer.valueOf(type));
        cycleMealVoList.setMealIds(setMealBean.getId()+"");
        cycleMealVoList.setCoverPic(setMealBean.getCoverPic());
        cycleMealVoList.setMealNikeName(setMealBean.getMealNikeName());
        if (id != 0) {
            cycleMealVoList.setId(id);
        }
//        cycleMealVoList.setCategoryList(setMealBean.getFoodDetailVoList());

        cycleMealVoLists.add(cycleMealVoList);

        saveSetMealBean.setCycleMealVoList(cycleMealVoLists);


        Gson gson = new Gson();
        String json = gson.toJson(saveSetMealBean);

        PujingService.saveSetMealData(json)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<Boolean>() {
                    @Override
                    public void _onNext(Boolean aBoolean) {
                        getView().saveDataSuccess(aBoolean);
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
    public void submitSetMeal(int status){

        SaveSetMealBean saveSetMealBean = new SaveSetMealBean();
        SaveSetMealBean.RestaurantCycleRecord restaurantCycleRecord = saveSetMealBean.new RestaurantCycleRecord();
        restaurantCycleRecord.setStatus(status);
        saveSetMealBean.setRestaurantCycleRecord(restaurantCycleRecord);


        Gson gson = new Gson();
        String json = gson.toJson(saveSetMealBean);

        PujingService.saveSetMealData(json)
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

    /**
     * 小标识
     */
    public void identification(){

        PujingService.identification()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<ArrayList<String>>() {
                    @Override
                    public void _onNext(ArrayList<String> strings) {
                        getView().getIdentification(strings);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }
                });

    }



    //检查用户是否点击完常规餐
    public void checkCycleRecord(){

        PujingService.checkCycleRecord()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<Boolean>() {
                    @Override
                    public void _onNext(Boolean aBoolean) {
                        getView().checkCycleRecord(aBoolean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().checkCycleRecordFail(errorMessage);
                    }
                });
    }



}
