package cn.com.pujing.entity;

import java.util.List;

/**
 * author : liguo
 * date : 2021/4/12 11:28
 * description :我的账单
 */
public class MyBillBean {

        /**
         * 账单id
         */
        public String billId;
        /**
         * 订单id
         */
        public String id;
        /**
         * 姓名
         */
        public int name;
        /**
         * 订单分类
         */
        public String orderCategory;
        /**
         * 订单分类名称
         */
        public String orderCategory_label;
        /**
         * 订单名称
         */
        public String orderName;
        /**
         * 订单状态
         */
        public String orderStatus;
        /**
         * 订单用户id
         */
        public String orderingUserId;
        /**
         * 支付状态：1待支付2支付成功
         */
        public int payStatus;
        /**
         * 实收金额
         */
        public double realMoney;
        /**
         * 创建时间
         */
        public String createTime;


}

