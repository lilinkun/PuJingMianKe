package cn.com.pujing.datastructure;

import java.util.ArrayList;
import java.util.List;

import cn.com.pujing.R;

public class PaidBillItem {
    public int imgRes;
    public String imgUrl;
    public String name;
    public String time;
    public String content;
    public String price;

    public static List<PaidBillItem> getTestData() {
        List<PaidBillItem> list = new ArrayList<>();

        PaidBillItem paidBillItem = new PaidBillItem();
        paidBillItem.imgRes = R.mipmap.ic_order_goods;
        paidBillItem.name = "零点餐";
        paidBillItem.time = "今天11:50";
        paidBillItem.content = "餐饮美食";
        paidBillItem.price = "20.00";

        PaidBillItem paidBillItem1 = new PaidBillItem();
        paidBillItem1.imgRes = R.mipmap.ic_order_goods_1;
        paidBillItem1.name = "美甲美发服务";
        paidBillItem1.time = "01-10 11:50";
        paidBillItem1.content = "服务";
        paidBillItem1.price = "20.00";

        list.add(paidBillItem);
        list.add(paidBillItem1);
        return list;
    }
}
