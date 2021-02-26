package cn.com.pujing.view;

import cn.com.pujing.entity.SendSms;

/**
 * author : liguo
 * date : 2021/2/26 18:06
 * description :
 */
public interface RegisterView {

    void getVCodeSuccess(String sendSms);

    void registerSuccess();

    void getDataFail(String msg);

}
