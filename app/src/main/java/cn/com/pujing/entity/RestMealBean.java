package cn.com.pujing.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * author : liguo
 * date : 2021/3/9 19:39
 * description :
 */
public class RestMealBean {

    /**
     * 菜品
     */
    public List<FoodGruops> foodGruops;
    /**
     * 菜单
     */
    public List<CestaurantFoodCategory> cestaurantFoodCategory;

    /**
     * 套餐名
     */
    public String mealName;

    /**
     * 图片
     */
    public String coverPic;

    public class CestaurantFoodCategory{
        public String coverPic;
    }


    public class FoodGruops {
        public String groupName;
        public List<FoodCategorys> foodCategorys;

        public class FoodCategorys{
            public String coverPic;
            public String foodCategoryName;
            public String number;
            public String calculateUnit;
        }
    }
}
