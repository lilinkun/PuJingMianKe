package cn.com.pujing.entity;

import java.util.List;

/**
 * author : liguo
 * date : 2021/4/2 18:53
 * description :
 */
public class ReserveDeviceBean {

    public List<TimesReserveNumList> timesReserveNumList;
    public VenueManage venueManage;

    public class TimesReserveNumList{
        public String reserveNum;
        public String startEndTime;
    }

    public class VenueManage{
        public String topic;
    }
}
