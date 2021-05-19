package cn.com.pujing.entity;

import java.io.Serializable;

/**
 * author : liguo
 * date : 2021/4/12 10:08
 * description : 我的账单
 */
public class BillsBean implements Serializable {

    public int id;
    /**
     * 待付金额
     */
    public double arrearage;
    /**
     * 账单所属月份
     */
    public String billMonth;
    /**
     * 	操作人
     */
    public String billTime;
    /**
     * 操作人
     */
    public int operatorUser;
    /**
     * 	结算状态 1的时候已出账 2是已结清
     */
    public int payStatus;
    /**
     * 账单总金额
     */
    public double totalAmount;
    /**
     * 	结算方式
     */
    public int paymentMethod;

}