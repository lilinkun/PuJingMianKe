package cn.com.pujing.entity;

public class GetFilePathKey extends Base {
    public Data data;

    public class Data {
        public String bucket;
        public String prefixurl;
        public String region;
        public String key;
    }
}
