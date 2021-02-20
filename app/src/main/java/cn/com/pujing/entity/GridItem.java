package cn.com.pujing.entity;

import org.greenrobot.greendao.annotation.Entity;

import java.util.ArrayList;
import java.util.List;

import cn.com.pujing.R;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class GridItem implements Comparable<GridItem>{
    public Integer imageRes;
    public String imageUrl;
    public String title;
    @Unique
    public int position;

    public GridItem(Integer imageRes, String title,int position) {
        this.imageRes = imageRes;
        this.title = title;
        this.position = position;
    }

    public GridItem(String imageUrl, String title) {
        this.imageUrl = imageUrl;
        this.title = title;
    }

    @Generated(hash = 1172937718)
    public GridItem(Integer imageRes, String imageUrl, String title, int position) {
        this.imageRes = imageRes;
        this.imageUrl = imageUrl;
        this.title = title;
        this.position = position;
    }

    @Generated(hash = 1267517261)
    public GridItem() {
    }

    public static List<GridItem> getTestData() {
        List<GridItem> list = new ArrayList<>();
        list.add(new GridItem(R.mipmap.ic_life_service, "生活服务",1));
        list.add(new GridItem(R.mipmap.ic_exercise_another, "活动",2));
        list.add(new GridItem(R.mipmap.ic_community_calendar, "社区日历",3));
        list.add(new GridItem(R.mipmap.ic_restaurant_another, "餐饮",4));
        list.add(new GridItem(R.mipmap.ic_photo_wall, "照片墙",5));
        list.add(new GridItem(R.mipmap.ic_home_court_booking, "场馆预约",6));
        list.add(new GridItem(R.mipmap.ic_home_questionnaire_investigation, "问卷调查",7));
        list.add(new GridItem(R.mipmap.ic_feedback, "意见反馈",8));
        list.add(new GridItem(R.mipmap.ic_more_services, "更多服务",9));
        return list;
    }

    public static List<GridItem> getTestData1() {
        List<GridItem> list = new ArrayList<>();
        list.add(new GridItem(R.mipmap.ic_life_service, "生活服务",1));
        list.add(new GridItem(R.mipmap.ic_exercise_another, "活动",2));
        list.add(new GridItem(R.mipmap.ic_community_calendar, "社区日历",3));
        list.add(new GridItem(R.mipmap.ic_restaurant_another, "餐饮",4));
        list.add(new GridItem(R.mipmap.ic_photo_wall, "照片墙",5));
        list.add(new GridItem(R.mipmap.ic_home_court_booking, "场馆预约",7));
        list.add(new GridItem(R.mipmap.ic_home_questionnaire_investigation, "问卷调查",8));
        list.add(new GridItem(R.mipmap.ic_feedback, "意见反馈",9));
        return list;
    }

    public Integer getImageRes() {
        return this.imageRes;
    }

    public void setImageRes(Integer imageRes) {
        this.imageRes = imageRes;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int compareTo(GridItem o) {
        int i = this.getPosition() - o.getPosition();//先按照顺序排序

        return i;
    }
}
