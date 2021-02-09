package cn.com.pujing.datastructure;

import java.io.Serializable;

public class MyInfo extends Base {
    public Data data;

    public class Data implements Serializable {
        public String avatar;
        public String username;
        public String phone;
        public String auditStatus_label;
    }
}
