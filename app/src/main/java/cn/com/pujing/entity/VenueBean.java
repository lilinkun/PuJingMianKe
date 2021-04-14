package cn.com.pujing.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author : liguo
 * date : 2021/4/2 14:08
 * description :
 */
public class VenueBean implements Serializable {

    public List<Records> serviceVenueManageList;
    public String topicPicture;

    public class Records implements Serializable{

        public String id;
        public String name;
        public String topic;
        public String thumbnail;
    }
}
