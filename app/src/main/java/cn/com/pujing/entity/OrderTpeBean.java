package cn.com.pujing.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author : liguo
 * date : 2021/4/9 11:17
 * description :
 */
public class OrderTpeBean implements Serializable {

    public int typeId;
    public String orderTypeName;

    public OrderTpeBean(int typeId,String orderTypeName){
        this.typeId = typeId;
        this.orderTypeName = orderTypeName;
    }

    public static List<OrderTpeBean> orderTpeBeans(){
        List<OrderTpeBean> orderTpeBeans = new ArrayList<>();

        String[] orderStrs = {"全部","订餐","服务","活动","场馆"};

        for (int i = 0;i<orderStrs.length;i++) {
            OrderTpeBean orderTpeBean = new OrderTpeBean(i, orderStrs[i]);
            orderTpeBeans.add(orderTpeBean);
        }

        return orderTpeBeans;
    }


}
