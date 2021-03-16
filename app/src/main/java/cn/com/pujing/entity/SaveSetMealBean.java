package cn.com.pujing.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author : liguo
 * date : 2021/3/15 16:08
 * description :
 */
public class SaveSetMealBean implements Serializable {

    private List<CycleMealVoList> cycleMealVoList;


    public class CycleMealVoList{

        private String mealName;
        private String time;
        private int type;
        private String mealIds;
        private List<SetMealBean.FoodDetailVoList> categoryList;


        public String getMealName() {
            return mealName;
        }

        public void setMealName(String mealName) {
            this.mealName = mealName;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getMealIds() {
            return mealIds;
        }

        public void setMealIds(String mealIds) {
            this.mealIds = mealIds;
        }

        public List<SetMealBean.FoodDetailVoList> getCategoryList() {
            return categoryList;
        }

        public void setCategoryList(List<SetMealBean.FoodDetailVoList> categoryList) {
            this.categoryList = categoryList;
        }

        @Override
        public String toString() {
            return "{" +
                    "\"mealName\":\"" + mealName + '\"' +
                    ", time\":\"" + time + '\"' +
                    ", type:" + type +
                    ", mealIds\":\"" + mealIds + '\"' +
                    '}';
        }
    }

    public List<CycleMealVoList> getCycleMealVoList() {
        return cycleMealVoList;
    }

    public void setCycleMealVoList(List<CycleMealVoList> cycleMealVoList) {
        this.cycleMealVoList = cycleMealVoList;
    }

    @Override
    public String toString() {
        return "{" +
                "\"cycleMealVoList\":" + cycleMealVoList +
                '}';
    }


    public class CategoryList{
        private String categoryName;
        private String categoryType;
    }
}
