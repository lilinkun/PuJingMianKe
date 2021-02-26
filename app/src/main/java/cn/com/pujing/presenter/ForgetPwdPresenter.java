package cn.com.pujing.presenter;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.ForgetPwdView;

/**
 * author : liguo
 * date : 2021/2/26 18:39
 * description :
 */
public class ForgetPwdPresenter extends BasePresenter<ForgetPwdView> {

        //发送验证码
        public void sendVCode(String phoneNo,boolean isChangePwd){

            PujingService.sendSms(phoneNo,isChangePwd)
                    .compose(RxSchedulersHelper.io_main())
                    .compose(RxResultHelper.handleResult())
                    .subscribe(new RxObserver<String>() {
                        @Override
                        public void _onNext(String sendSms) {
                            getView().sendSmsSuccess(sendSms);
                        }

                        @Override
                        public void _onError(String errorMessage) {
                            getView().getDataFail(errorMessage);
                        }
                    });
        }

        //发送验证码
        public void modifyPwd(String phone,String pwd,String captcha){

            PujingService.modifyPwd(phone,pwd,captcha)
                    .compose(RxSchedulersHelper.io_main())
                    .compose(RxResultHelper.handleResult())
                    .subscribe(new RxObserver<Boolean>() {
                        @Override
                        public void _onNext(Boolean pwd) {
                            getView().modifyPwdSuccess(pwd);
                        }

                        @Override
                        public void _onError(String errorMessage) {
                            getView().getDataFail(errorMessage);
                        }
                    });
        }

}
