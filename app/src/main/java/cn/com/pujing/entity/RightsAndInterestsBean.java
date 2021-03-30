package cn.com.pujing.entity;

import java.io.Serializable;

/**
 * author : liguo
 * date : 2021/3/30 10:33
 * description :
 */
public class RightsAndInterestsBean<T> implements Serializable {


    public int id;
    /**
     * 权益包名称
     */
    public String name;
    /**
     * 价格
     */
    public double price;
    /**
     * 限购次数
     */
    public int quotaNumber;
    /**
     * 限购类型
     */
    public int quotaType;
    /**
     * 权益说明
     */
    public String rightsDescription;
    /**
     * 权益简介
     */
    public String rightsResume;
    /**
     * 权益时间
     */
    public String rightsTime;
    /**
     * 权益时间单位
     */
    public String rightsTimeUnit;
    /**
     * 状态(待启用 启用 停用)
     */
    public int status;

    public T rightsVoucherVoList;


}
