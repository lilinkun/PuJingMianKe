package cn.com.pujing.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author : liguo
 * date : 2021/3/2 13:58
 * description :
 */
public class SetMealBean implements Serializable {

    private List<FoodDetailVoList> foodDetailVoList;
    private String mealName;
    private String coverPic;
    private int id;


    public class FoodDetailVoList implements Serializable{
        private String categoryName; //菜品分类名称
        private String foodCategoryName;  //名称


        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getFoodCategoryName() {
            return foodCategoryName;
        }

        public void setFoodCategoryName(String foodCategoryName) {
            this.foodCategoryName = foodCategoryName;
        }
    }

    public List<FoodDetailVoList> getFoodDetailVoList() {
        return foodDetailVoList;
    }

    public void setFoodDetailVoList(List<FoodDetailVoList> foodDetailVoList) {
        this.foodDetailVoList = foodDetailVoList;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
