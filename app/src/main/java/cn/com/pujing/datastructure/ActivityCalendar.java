package cn.com.pujing.datastructure;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class ActivityCalendar extends Base {
    public Data data;

    public class Data {
        public List<Record> records;

        public class Record implements MultiItemEntity {
            public String id;
            public String photo;
            public String activityName;
            public String content;
            public String price;
            public int stayUserNum;
            public String activityStartDate;

            public int itemType;

            @Override
            public int getItemType() {
                return itemType;
            }
        }
    }
}
