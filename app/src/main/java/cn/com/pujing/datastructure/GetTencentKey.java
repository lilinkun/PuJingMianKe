package cn.com.pujing.datastructure;

public class GetTencentKey extends Base {
    public Data data;

    public class Data {
        public String startTime;
        public String expiredTime;
        public Credentials credentials;

        public class Credentials {
            public String tmpSecretId;
            public String tmpSecretKey;
            public String sessionToken;
        }
    }
}
