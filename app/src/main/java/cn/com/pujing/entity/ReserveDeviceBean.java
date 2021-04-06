package cn.com.pujing.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author : liguo
 * date : 2021/4/2 18:53
 * description :
 */
public class ReserveDeviceBean implements Serializable {

    public List<TimesReserveNumList> timesReserveNumList;
    public VenueManage venueManage;

    public class TimesReserveNumList implements Serializable{
        public String reserveNum;
        public String startEndTime;
    }

    public class VenueManage implements Serializable{
        public String topic;
        public String address;
        public String area;
        public String peopleNum;
        public String description;
    }
}
