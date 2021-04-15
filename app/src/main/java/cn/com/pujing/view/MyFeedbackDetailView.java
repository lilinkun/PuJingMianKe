package cn.com.pujing.view;

import cn.com.pujing.entity.MyFeedbackBean;
import cn.com.pujing.entity.PagesBean;

/**
 * author : liguo
 * date : 2021/4/15 18:43
 * description :
 */
public interface MyFeedbackDetailView {
    public void getMyFeedbackDetailSuccess(MyFeedbackBean o);

    public void getDataFail(String msg);

}
