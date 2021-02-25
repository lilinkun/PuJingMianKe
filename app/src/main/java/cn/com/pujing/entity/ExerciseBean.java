package cn.com.pujing.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

public class ExerciseBean extends Base {
    public List<Data> data;

    public class Data {
        public String id;
        public String photo;
        public String activityName;
        public String content;
        public String price;
        public String calendarStatus;

    }
}
