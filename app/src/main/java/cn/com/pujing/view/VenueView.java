package cn.com.pujing.view;

import java.util.ArrayList;

import cn.com.pujing.entity.VenueBean;

/**
 * author : liguo
 * date : 2021/4/2 13:42
 * description :
 */
public interface VenueView {

    public void getVenueType(ArrayList<VenueBean> venueBeans);

    public void getVenueFail(String msg);

}
