package cn.com.pujing.view;

import cn.com.pujing.entity.OrderItemBean;

/**
 * author : liguo
 * date : 2021/3/25 17:29
 * description :
 */
public interface MyOrderView {

    public void getMyOrderSuccess(OrderItemBean orderItemBean);

    public void getDataFail(String msg);

}
