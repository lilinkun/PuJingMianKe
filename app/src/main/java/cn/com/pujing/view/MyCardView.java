package cn.com.pujing.view;

import java.util.List;

import cn.com.pujing.entity.MyCardBean;

/**
 * author : liguo
 * date : 2021/3/29 11:05
 * description :
 */
public interface MyCardView {
    public void getCardDataSuccess(List<MyCardBean> myCardBeans);
    public void getCardDataFail(String msg);
}
