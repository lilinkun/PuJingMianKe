package cn.com.pujing.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author : liguo
 * date : 2021/3/15 18:12
 * description :
 */
public class AddRestBean implements Serializable {

    private String orderNumber;
    private List<ChangeDataBean.DetailList> orderFoodList;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<ChangeDataBean.DetailList> getFoodList() {
        return orderFoodList;
    }

    public void setFoodList(List<ChangeDataBean.DetailList> orderFoodList) {
        this.orderFoodList = orderFoodList;
    }


}
