package cn.com.pujing.entity;

import java.io.Serializable;

/**
 * author : liguo
 * date : 2021/4/7 18:26
 * description :
 */
public class MyCardBean implements Serializable {

    /**
     *权益劵名称
     */
    public String name;
    /**
     *	订单id
     */
    public String orderId;
    /**
     *使用标识(未使用 使用中 已使用)
     */
    public int usedFlag;
    /**
     *未使用 使用中 已使用)
     */
    public String usedFlag_label;
    /**
     *	过期时间
     */
    public String expiresTime;
    /**
     *优惠劵抵扣方式 1按次 2按费用
     */
    public String deductMethod;
    /**
     *抵扣费用
     */
    public String deductAmount;
    /**
     *1启用满减
     */
    public String fullReductionFlag;
    /**
     *满减值
     */
    public String fullReductionThreshold;



}
