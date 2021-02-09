package cn.com.pujing.datastructure;

import java.util.ArrayList;
import java.util.List;

import cn.com.pujing.R;

public class OrderItem {
    public int imgRes;
    public String imgUrl;
    public String name;
    public String type;
    public String date;
    public String price;
    public String status;

    public static List<OrderItem> getTestData() {
        List<OrderItem> list = new ArrayList<>();

        OrderItem orderItem = new OrderItem();
        orderItem.imgRes = R.mipmap.ic_order_goods;
        orderItem.name = "营养餐组合3";
        orderItem.type = "零点餐";
        orderItem.date = "下单日期：2020年12月10日 12:00";
        orderItem.price = "20.00";
        orderItem.status = "已完成";

        OrderItem orderItem1 = new OrderItem();
        orderItem1.imgRes = R.mipmap.ic_order_goods;
        orderItem1.name = "营养餐组合3";
        orderItem1.type = "营养餐";
        orderItem1.date = "下单日期：2020年12月10日 12:00";
        orderItem1.price = "20.00";
        orderItem1.status = "待确认";

        OrderItem orderItem2 = new OrderItem();
        orderItem2.imgRes = R.mipmap.ic_order_goods;
        orderItem2.name = "营养餐组合3";
        orderItem2.type = "宴会餐";
        orderItem2.date = "下单日期：2020年12月10日 12:00";
        orderItem2.price = "20.00";
        orderItem2.status = "已确认";

        OrderItem orderItem3 = new OrderItem();
        orderItem3.imgRes = R.mipmap.ic_order_goods_1;
        orderItem3.name = "营养餐组合3";
        orderItem3.type = "美发美甲";
        orderItem3.date = "下单日期：2020年12月10日 12:00";
        orderItem3.price = "20.00";
        orderItem3.status = "已预约";

        OrderItem orderItem4 = new OrderItem();
        orderItem4.imgRes = R.mipmap.ic_order_goods_1;
        orderItem4.name = "营养餐组合3";
        orderItem4.type = "美发美甲";
        orderItem4.date = "下单日期：2020年12月10日 12:00";
        orderItem4.price = "20.00";
        orderItem4.status = "取消";

        list.add(orderItem);
        list.add(orderItem1);
        list.add(orderItem2);
        list.add(orderItem3);
        list.add(orderItem4);

        return list;
    }
}
