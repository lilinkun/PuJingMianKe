package cn.com.pujing.entity;

import java.util.ArrayList;
import java.util.List;

import cn.com.pujing.R;

public class PayBillItem {
    public int imgRes;
    public String imgUrl;
    public String name;
    public String time;
    public String content;
    public String price;

    public static List<PayBillItem> getTestData() {
        List<PayBillItem> list = new ArrayList<>();

        PayBillItem payBillItem = new PayBillItem();
        payBillItem.imgRes = R.mipmap.ic_order_goods;
        payBillItem.name = "零点餐";
        payBillItem.content = "餐饮美食";
        payBillItem.time = "01-10 11:50";
        payBillItem.price = "20.00";

        PayBillItem payBillItem1 = new PayBillItem();
        payBillItem1.imgRes = R.mipmap.ic_order_goods_1;
        payBillItem1.name = "美甲美发服务";
        payBillItem1.time = "01-10 11:50";
        payBillItem1.content = "服务";
        payBillItem1.price = "20.00";

        list.add(payBillItem);
        list.add(payBillItem1);
        return list;
    }
}
