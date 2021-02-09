package cn.com.pujing.datastructure;

import java.util.List;

public class PhotoWall extends Base {
    public Data data;

    public class Data {
        public List<Record> records;

        public class Record {
            public String title;
            public String photo;
            public String content;
        }
    }
}
