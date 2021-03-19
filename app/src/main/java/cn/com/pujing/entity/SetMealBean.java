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
    private boolean isShow;
    private boolean isVisibel;
    private boolean isCheck;
    private int checkId;
    private String mealNikeName;


    public class FoodDetailVoList implements Serializable{
        private String categoryName; //菜品分类名称
        private String foodCategoryName;  //名称
        private String categoryType; //菜品类别
        public String getCategoryType() {
            return categoryType;
        }

        public void setCategoryType(String categoryType) {
            this.categoryType = categoryType;
        }

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

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public boolean isVisibel() {
        return isVisibel;
    }

    public void setVisibel(boolean visibel) {
        isVisibel = visibel;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getCheckId() {
        return checkId;
    }

    public void setCheckId(int checkId) {
        this.checkId = checkId;
    }

    public String getMealNikeName() {
        return mealNikeName;
    }

    public void setMealNikeName(String mealNikeName) {
        this.mealNikeName = mealNikeName;
    }
}
