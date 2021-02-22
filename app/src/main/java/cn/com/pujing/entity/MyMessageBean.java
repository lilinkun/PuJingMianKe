package cn.com.pujing.entity;

import java.io.Serializable;

public class MyMessageBean extends Base {
    public Data data;

    public class Data implements Serializable {
        public String content;
        public String eventTime;
        public String title;
        public String messageType;
    }

}
