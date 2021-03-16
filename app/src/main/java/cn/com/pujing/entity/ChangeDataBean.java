package cn.com.pujing.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author : liguo
 * date : 2021/3/8 20:49
 * description :
 */
public class ChangeDataBean implements Serializable {

    public int totalQuantity;
    public double totalAmount;
    public List<DetailList> detailList;

    public List<CategoryCountList> categoryCountList;

    public class CategoryCountList{
        public String name;
        public int quantity;
    }

    public class DetailList{
        public int customerUserId;
        public int menuItemId;
        public int quantity;
        public double retailPrice;
        public String name;
        public double amount;
        public String coverPic;
        public boolean mealFlag;
        public int mealId;
        public int foodCategoryId;
        public int number;

    }

}
