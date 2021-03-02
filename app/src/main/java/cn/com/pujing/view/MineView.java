package cn.com.pujing.view;

import cn.com.pujing.entity.MyInfoBean;

/**
 * author : liguo
 * date : 2021/3/1 12:16
 * description :
 */
public interface MineView {

    public void getMyInfoSuccess(MyInfoBean myInfoBean);
    public void getDataFail(String msg);

}
