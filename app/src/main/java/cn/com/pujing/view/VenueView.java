package cn.com.pujing.view;

import java.util.ArrayList;
import java.util.List;

import cn.com.pujing.entity.BannerBean;
import cn.com.pujing.entity.VenueBean;

/**
 * author : liguo
 * date : 2021/4/2 13:42
 * description :
 */
public interface VenueView {

    public void getVenueType(VenueBean venueBeans);

    public void getVenueFail(String msg);


    void getBannerDataSuccess(List<BannerBean> data);

    void getBannerDataFail(String msg);

}
