package cn.com.pujing.presenter;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.PhotoBean;
import cn.com.pujing.entity.SendSms;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.RegisterView;

/**
 * author : liguo
 * date : 2021/2/26 18:10
 * description :
 */
public class RegisterPresenter extends BasePresenter<RegisterView> {


    //发送验证码
    public void sendVCode(String phoneNo,boolean isChangePwd){

        PujingService.sendSms(phoneNo,isChangePwd)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<String>() {
                    @Override
                    public void _onNext(String sendSms) {
                        getView().getVCodeSuccess(sendSms);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }
                });
    }

    //注册
    public void register(String userName,String phone,String pwd,String captcha,String name,String sex){

        PujingService.register(userName,phone,pwd,captcha,name,sex)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<Boolean>() {
                    @Override
                    public void _onNext(Boolean sendSms) {
                        if (sendSms) {
                            getView().registerSuccess();
                        }
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }
                });
    }





}
