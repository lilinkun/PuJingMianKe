package cn.com.pujing.view;

import cn.com.pujing.entity.MyFeedbackBean;
import cn.com.pujing.entity.PagesBean;

/**
 * author : liguo
 * date : 2021/4/15 13:52
 * description :
 */
public interface MyFeedBackView {

    public void getFeedbakListSuccess(PagesBean<MyFeedbackBean> pagesBean);

    public void getDataFail(String msg);

}
