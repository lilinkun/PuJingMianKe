package cn.com.pujing.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author : liguo
 * date : 2021/3/10 19:40
 * description :
 */
public class RestBanquetsBean implements Serializable {

    /**
     * 餐次
     */
    private int mealTime;
    /**
     * 订单类型1常规餐2宴会餐3零点餐
     */
    private int orderType;
    /**
     * 预定时间
     */
    private String orderingTime;
    /**
     * 就餐人数
     */
    private int peopleNumber;
    /**
     * 预约类型 1.实时，2.预约
     */
    private int reservationType;

    public String mealTime_label; //餐次名
    public String createTime; //下单时间

    public String orderStatus_label;
    //订单状态:1.待确认,2.已确认,3.已出餐,4.已结单-待付款,5.已完成,6.已取消
    public int orderStatus;

    /**
     * 订单金额
     */
    public double orderMoney;

    /**
     * 日期
     */
    private String orderingDate;

    /**
     * 订单关联菜品list
     */
    private List<OrderFoodList> orderFoodList;


    public class OrderFoodList implements Serializable {
        /**
         * 菜单id
         */
        private int menuItemId;
        /**
         * 	数量
         */
        private int number;
        /**
         *商品名
         */
        private String foodName;

        private double price;

        private String coverPic;

        public String getCoverPic() {
            return coverPic;
        }

        public void setCoverPic(String coverPic) {
            this.coverPic = coverPic;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getMenuItemId() {
            return menuItemId;
        }

        public void setMenuItemId(int menuItemId) {
            this.menuItemId = menuItemId;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }

        @Override
        public String toString() {
            return "{" +
                    "\"menuItemId\":" + menuItemId +
                    ", \"number\":" + number +
                    ", \"foodName\":\"" + foodName + '\"' +
                    '}';
        }
    }

    public int getMealTime() {
        return mealTime;
    }

    public void setMealTime(int mealTime) {
        this.mealTime = mealTime;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getOrderingTime() {
        return orderingTime;
    }

    public void setOrderingTime(String orderingTime) {
        this.orderingTime = orderingTime;
    }

    public int getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(int peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public int getReservationType() {
        return reservationType;
    }

    public void setReservationType(int reservationType) {
        this.reservationType = reservationType;
    }

    public List<OrderFoodList> getOrderFoodList() {
        return orderFoodList;
    }

    public void setOrderFoodList(List<OrderFoodList> orderFoodList) {
        this.orderFoodList = orderFoodList;
    }

    public String getOrderingDate() {
        return orderingDate;
    }

    public void setOrderingDate(String orderingDate) {
        this.orderingDate = orderingDate;
    }

    @Override
    public String toString() {
        return "{" +
                "\"mealTime\":" + mealTime +
                ", \"orderType\":" + orderType +
                ", \"orderingTime\":\"" + orderingTime + '\"' +
                ", \"peopleNumber\":" + peopleNumber +
                ", \"reservationType\":" + reservationType +
                ", \"orderingDate\":\"" + orderingDate + '\"' +
                ", \"orderFoodList\":" + orderFoodList +
                '}';
    }
}
