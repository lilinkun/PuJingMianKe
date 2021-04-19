package cn.com.pujing.entity;

import java.util.ArrayList;
import java.util.List;

import cn.com.pujing.R;

public class OrderItemBean {
    public int current;
    public int pages;
    public int size;
    public int total;
    public List<MyOrder> records;


    public class MyOrder{

        /**
         * 订单号
         */
        public String orderNumber;
        /**
         * 订单id
         */
        public String id;
        /**
         * 订单类型(1餐饮 2服务 3活动 4场馆预约)
         */
        public int orderCategory;
        /**
         * 创建时间
         */
        public String createTime;
        /**
         * 订单名称
         */
        public String orderName;
        /**
         * 订单图片
         */
        public String orderPic;
        /**
         * 订单状态
         */
        public String orderStatus;
        /**
         * 订单状态名
         */
        public String orderStatus_label;
        /**
         * 具体订单类型
         */
        public int orderType;
        /**
         * 具体订单类型名称
         */
        public String orderType_label;
        /**
         * 实收金额
         */
        public double realMoney;
        /**
         * 实收金额
         */
        public double orderMoney;

    }


}
