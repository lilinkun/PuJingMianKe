package cn.com.pujing.entity;

import java.io.Serializable;

/**
 * author : liguo
 * date : 2021/4/18 12:27
 * description :
 */
public class MessageBean implements Serializable {

    public int id;
    /**
     * 消息内容
     */
    public String messageContent;
    /**
     * 消息类型
     */
    public String messageType;
    /**
     * 状态(0未读 1已读)
     */
    public int status;

    public String createTime;

}
