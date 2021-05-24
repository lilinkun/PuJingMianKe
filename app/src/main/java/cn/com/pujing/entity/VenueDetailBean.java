package cn.com.pujing.entity;

import java.io.Serializable;

/**
 * author : liguo
 * date : 2021/4/19 16:36
 * description :
 */
public class VenueDetailBean implements Serializable {

    public int id;
    /**
     * 设备ID
     */
    public String deviceId;
    /**
     * 设备名称
     */
    public String deviceName;
    /**
     * 订单编号
     */
    public String orderNum;
    /**
     * 下单时间
     */
    public String orderTime;
    /**
     * 预约日期
     */
    public String reserveDate;
    /**
     * 预约时间
     */
    public String reserveTime;
    /**
     * 1:待确认,2:已确认,3:已取消
     */
    public int status;
    /**
     * 状态值
     */
    public String status_label;
    /**
     * 主题图片
     */
    public String topic;


}
