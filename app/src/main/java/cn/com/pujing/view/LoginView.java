package cn.com.pujing.view;

import cn.com.pujing.entity.LoginToken;
import cn.com.pujing.entity.PublicKey;

/**
 * author : liguo
 * date : 2021/2/26 16:22
 * description :
 */
public interface LoginView {
    void getPublicKeySucccess(PublicKey publicKey);
    void getDataFail(String msg);
    void getLoginSuccess(LoginToken loginToken);
}
