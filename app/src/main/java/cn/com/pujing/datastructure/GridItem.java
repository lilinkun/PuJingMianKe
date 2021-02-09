package cn.com.pujing.datastructure;

import java.util.ArrayList;
import java.util.List;

import cn.com.pujing.R;

public class GridItem {
    public Integer imageRes;
    public String imageUrl;
    public String title;

    public GridItem(Integer imageRes, String title) {
        this.imageRes = imageRes;
        this.title = title;
    }

    public GridItem(String imageUrl, String title) {
        this.imageUrl = imageUrl;
        this.title = title;
    }

    public static List<GridItem> getTestData() {
        List<GridItem> list = new ArrayList<>();
        list.add(new GridItem(R.mipmap.ic_life_service, "生活服务"));
        list.add(new GridItem(R.mipmap.ic_exercise_another, "活动"));
        list.add(new GridItem(R.mipmap.ic_community_calendar, "社区日历"));
        list.add(new GridItem(R.mipmap.ic_restaurant_another, "餐饮"));
        list.add(new GridItem(R.mipmap.ic_photo_wall, "照片墙"));
        list.add(new GridItem(R.mipmap.ic_more_services, "更多服务"));
        return list;
    }

    public static List<GridItem> getTestData1() {
        List<GridItem> list = new ArrayList<>();
        list.add(new GridItem(R.mipmap.ic_life_service_another, "生活服务"));
        list.add(new GridItem(R.mipmap.ic_community_calendar_another, "社区日历"));
        list.add(new GridItem(R.mipmap.ic_venue_booking_another, "场馆预约"));
        list.add(new GridItem(R.mipmap.ic_photo_wall_another, "照片墙"));
        list.add(new GridItem(R.mipmap.ic_questionnaire_investigation_another, "问卷调查"));
        list.add(new GridItem(R.mipmap.ic_feedback_another, "意见反馈"));
        return list;
    }
}
