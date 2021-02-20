package cn.com.pujing.entity;

import java.util.List;

public class QuerySelectDay extends Base {
    public List<Data> data;

    public class Data {
        public String content;
        public String timeSlot;
    }
}
