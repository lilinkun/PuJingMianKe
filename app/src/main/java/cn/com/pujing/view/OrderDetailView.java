package cn.com.pujing.view;

import cn.com.pujing.entity.OrderDetailBean;
import cn.com.pujing.entity.VenueDetailBean;

/**
 * author : liguo
 * date : 2021/4/9 15:55
 * description :
 */
public interface OrderDetailView {

    public void querySuccess(OrderDetailBean orderDetailBean);

    public void queryVenueSuccess(VenueDetailBean venueDetailBean);

    public void queryFail(String msg);

}
