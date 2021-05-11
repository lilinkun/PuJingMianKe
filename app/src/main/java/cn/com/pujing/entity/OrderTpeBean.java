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

    public static List<OrderTpeBean> collectBeans(){
        List<OrderTpeBean> orderTpeBeans = new ArrayList<>();

        String[] orderStrs = {"全部","活动","照片墙"};

        for (int i = 0;i<orderStrs.length;i++) {
            OrderTpeBean orderTpeBean = new OrderTpeBean(i-1, orderStrs[i]);
            orderTpeBeans.add(orderTpeBean);
        }

        return orderTpeBeans;
    }

    public static List<OrderTpeBean> activityTpeBeans(){
        List<OrderTpeBean> orderTpeBeans = new ArrayList<>();

        String[] orderStrs = {"全部","即将开始","正在报名","进行中"};
        int[] orderId = {0,5,2,6};

        for (int i = 0;i<orderStrs.length;i++) {
            OrderTpeBean orderTpeBean = new OrderTpeBean(orderId[i], orderStrs[i]);
            orderTpeBeans.add(orderTpeBean);
        }

        return orderTpeBeans;
    }

    public static List<OrderTpeBean> activityBeans(List<ActivityTypeBean> activityTypeBeans){
        List<OrderTpeBean> orderTpeBeans = new ArrayList<>();

        for (int i = 0;i<activityTypeBeans.size();i++) {
            OrderTpeBean orderTpeBean = new OrderTpeBean(activityTypeBeans.get(i).id, activityTypeBeans.get(i).name);
            orderTpeBeans.add(orderTpeBean);
        }

        return orderTpeBeans;

    }


}
