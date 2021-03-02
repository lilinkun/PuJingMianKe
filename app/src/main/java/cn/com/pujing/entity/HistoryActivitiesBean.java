package cn.com.pujing.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author : liguo
 * date : 2021/3/1 14:48
 * description :
 */
public class HistoryActivitiesBean implements Serializable {

    public List<Records> records;

    public class Records implements Serializable {
        public String id;
        public String photo;
        public String activityName;
        public String content;
        public String price;
        public String calendarStatus_label;
        public String summary;
    }

    public List<Records> getRecords() {
        return records;
    }

    public void setRecords(List<Records> records) {
        this.records = records;
    }
}
