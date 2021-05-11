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
    //状态(1.待确认，2.已确认，3.已收款，4.已取消)
    public int orderStatus;
    public String orderStatus_label;
    //订单金额
    public double money;
    //实付金额
    public double realityMoney;
    //应收金额
    public double receivableMoney;
    public String basicServiceItemsName;
    public String orderingTime;
    public String orderingDate;
    public String customerVoucherId;
    public String customerVoucherName;
    //支付状态：1待支付2支付成功
    public int payStatus;
    public String payStatus_label;
    public String themePic;
}
