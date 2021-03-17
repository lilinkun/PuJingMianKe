package cn.com.pujing.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author : liguo
 * date : 2021/3/16 18:11
 * description :
 */
public class RoutineRecordBean implements Serializable {

    public List<CycleMealVoList> cycleMealVoList;

    public RestaurantCycleRecord restaurantCycleRecord;


    public class CycleMealVoList{

        public int id;
        public int type;
        public int mealIds;


    }

    public class RestaurantCycleRecord{
        //预约状态 1未提交 2已预约 3已确认
        public int status;
    }


}
