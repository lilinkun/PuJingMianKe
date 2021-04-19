package cn.com.pujing.view;

import cn.com.pujing.entity.MessageBean;
import cn.com.pujing.entity.PagesBean;

/**
 * author : liguo
 * date : 2021/4/18 12:30
 * description :
 */
public interface MessageView {
    public void getMessageSuccess(PagesBean<MessageBean> messageBeans);
    public void getReadMessageSuccess(Object o);
    public void getReadMessageFail(String str);

    public void getDataFail(String msg);
}
