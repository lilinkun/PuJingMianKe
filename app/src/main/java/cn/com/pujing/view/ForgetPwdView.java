package cn.com.pujing.view;

/**
 * author : liguo
 * date : 2021/2/26 18:37
 * description :
 */
public interface ForgetPwdView {

    void sendSmsSuccess(String vCode);
    void modifyPwdSuccess(boolean result);

    void getDataFail(String msg);

}
