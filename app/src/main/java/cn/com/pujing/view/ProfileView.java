package cn.com.pujing.view;

import cn.com.pujing.entity.MyInfoBean;

/**
 * author : liguo
 * date : 2021/3/1 14:17
 * description :
 */
public interface ProfileView {

    public void getMyInfoSuccess(MyInfoBean myInfoBean);
    public void getDataFail(String msg);


}
