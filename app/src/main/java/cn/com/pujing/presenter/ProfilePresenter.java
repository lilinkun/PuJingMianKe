package cn.com.pujing.presenter;

import android.content.Context;

import org.json.JSONObject;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.MyInfoBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.ProfileView;

/**
 * author : liguo
 * date : 2021/3/1 14:18
 * description :
 */
public class ProfilePresenter extends BasePresenter<ProfileView> {

    public void getMyInfo(){
        PujingService.getMyInfo()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<MyInfoBean>() {
                    @Override
                    public void _onNext(MyInfoBean myInfoBean) {
                        getView().getMyInfoSuccess(myInfoBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }


    /**
     * 修改个人信息
     * @param nickName
     * @param personal_signature
     * @param personal_birthday
     * @param personal_room_number
     */
    public void modifyPersonalInfo(String nickName,String personal_signature,String personal_birthday,String personal_room_number){
        PujingService.editMyInfo(nickName,personal_signature,personal_birthday,personal_room_number)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<MyInfoBean>() {
                    @Override
                    public void _onNext(MyInfoBean myInfoBean) {
                        getView().modifyPersonalInfoSuccess(myInfoBean);
                    }


                    @Override
                    public void _onError(String errorMessage) {
                        getView().modifyFail(errorMessage);
                    }
                });
    }

    /**
     * 修改头像
     * @param jsonObject
     */
    public void modifyHeadImg(JSONObject jsonObject){
        PujingService.modifyHeadImg(jsonObject)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<MyInfoBean>() {
                    @Override
                    public void _onNext(MyInfoBean resultStr) {
                        getView().uploadHeadImg(resultStr);
                    }


                    @Override
                    public void _onError(String errorMessage) {
                        getView().modifyFail(errorMessage);
                    }
                });
    }



}
