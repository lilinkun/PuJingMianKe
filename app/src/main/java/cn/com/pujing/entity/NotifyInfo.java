package cn.com.pujing.entity;

import java.util.List;

public class NotifyInfo extends Base {
    public Data data;

    public class Data {
        public List<Record> records;

        public class Record {
            public String title;
        }
    }
}
