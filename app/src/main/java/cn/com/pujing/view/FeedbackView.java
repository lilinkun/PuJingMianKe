package cn.com.pujing.view;

import java.util.List;

import cn.com.pujing.entity.FeedbackBean;

/**
 * author : liguo
 * date : 2021/3/7 15:33
 * description :
 */
public interface FeedbackView {

    public void giveFeedbackType(List<FeedbackBean> feedbackBeans);

    public void getDataFail(String msg);

    public void saveFeedback(boolean b);

}
