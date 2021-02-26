package cn.com.pujing.presenter;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.LoginToken;
import cn.com.pujing.entity.PublicKey;
import cn.com.pujing.fragment.LoginFragment;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.LoginView;

/**
 * author : liguo
 * date : 2021/2/26 16:21
 * description :
 */
public class LoginPresenter extends BasePresenter<LoginView> {


    //获取登录公钥
    public void getPublicKey(){

        PujingService.getGetPublickey()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<PublicKey>() {
                    @Override
                    public void _onNext(PublicKey publicKey) {
                        getView().getPublicKeySucccess(publicKey);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });

    }

    //登录接口
    public void getLoginSuccess(String userName,String password,String rsaKey){

        PujingService.getLogin(userName,password,rsaKey)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<LoginToken>() {
                    @Override
                    public void _onNext(LoginToken loginToken) {
                        getView().getLoginSuccess(loginToken);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }

}
