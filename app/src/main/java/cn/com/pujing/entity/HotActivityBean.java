package cn.com.pujing.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * author : liguo
 * date : 2021/4/10 14:55
 * description :
 */
public class HotActivityBean implements Serializable {
    public List<Record> records;
    public int total;
    public int size;
    public int pages;
    public int current;

    public class Record implements MultiItemEntity {
        public String id;
        public String photo;
        public String activityName;
        public String content;
        public String price;
        public int stayUserNum;
        public String activityStartDate;
        public int itemType;
        public String calendarStatus;
        public String summary;
        public String calendarStatus_label;
        public int isReport;

        @Override
        public int getItemType() {
            return itemType;
        }
    }
}
