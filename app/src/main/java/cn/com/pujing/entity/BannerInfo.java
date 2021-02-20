package cn.com.pujing.entity;

import java.util.List;

public class BannerInfo extends Base {
    public List<Data> data;

    public class Data {
        public String picture;
        public String linkAddress;
    }
}
