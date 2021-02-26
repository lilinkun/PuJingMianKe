package cn.com.pujing.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author : liguo
 * date : 2021/2/26 17:21
 * description : 首页通知
 */
public class NotifyInfoBean implements Serializable {
    private List<Record> records;

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public class Record implements Serializable {
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
