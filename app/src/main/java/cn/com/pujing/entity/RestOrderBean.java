package cn.com.pujing.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author : liguo
 * date : 2021/3/12 16:13
 * description :
 */
public class RestOrderBean implements Serializable {

    /**
     * 餐次
     */
    public int mealTime;
    /**
     * 预定日期
     */
    public String orderingDate;
    /**
     * 预定时间
     */
    public String orderingTime;
    /**
     * 就餐人数
     */
    public String peopleNumber;
    /**
     * 餐次名
     */
    public String mealTime_label;
    /**
     * 下单时间
     */
    public String createTime;

    public List<OrderFoodList> orderFoodList;

    public class OrderFoodList{
        public double price;
        public String foodName;
        public String coverPic;
        public int number;
    }

}
