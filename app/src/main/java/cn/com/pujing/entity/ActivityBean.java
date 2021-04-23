package cn.com.pujing.entity;

import java.io.Serializable;

/**
 * author : liguo
 * date : 2021/4/23 15:17
 * description :
 */
public class ActivityBean implements Serializable {
    public int id;
    public String activityName;
    public String activityId;
    public String orderNumber;
    public String joinStatus;
    public String joinStatus_label;
    public String orderStatus;
    public String orderStatus_label;
    public String payStatus;
    public String payStatus_label;
    public double orderMoney;
    public String activityType;
    public String createTime;
    public String activityAddress;
}
