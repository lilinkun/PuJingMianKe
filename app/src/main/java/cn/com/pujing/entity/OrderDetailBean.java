package cn.com.pujing.entity;

import java.io.Serializable;

/**
 * author : liguo
 * date : 2021/4/9 16:23
 * description :
 */
public class OrderDetailBean implements Serializable {
    public String orderNunber;
    public String createTime;
    public String orderStatus_label;
    public double money;
    public String basicServiceItemsName;
    public String orderingTime;
    public String orderingDate;
    public String customerVoucherId;
    public String customerVoucherName;
    public String themePic;
}
