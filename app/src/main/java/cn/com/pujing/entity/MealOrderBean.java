package cn.com.pujing.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author : liguo
 * date : 2021/3/17 16:52
 * description :
 */
public class MealOrderBean implements Serializable {

    private String orderDate;

    private List<RoutineRecordBean.CycleMealVoList> mealVoLists;


    public class MealVoList{

        private int id;
        private int type;
        private int mealIds;
        private String repastTime;
        private String mealName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getMealIds() {
            return mealIds;
        }

        public void setMealIds(int mealIds) {
            this.mealIds = mealIds;
        }

        public String getRepastTime() {
            return repastTime;
        }

        public void setRepastTime(String repastTime) {
            this.repastTime = repastTime;
        }

        public String getMealName() {
            return mealName;
        }

        public void setMealName(String mealName) {
            this.mealName = mealName;
        }
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public List<RoutineRecordBean.CycleMealVoList> getMealVoLists() {
        return mealVoLists;
    }

    public void setMealVoLists(List<RoutineRecordBean.CycleMealVoList> mealVoLists) {
        this.mealVoLists = mealVoLists;
    }
}
