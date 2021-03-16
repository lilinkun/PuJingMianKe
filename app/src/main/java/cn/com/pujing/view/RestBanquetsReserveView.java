package cn.com.pujing.view;

import java.util.List;

import cn.com.pujing.entity.ChangeDataBean;
import cn.com.pujing.entity.RestMealTypeBean;

/**
 * author : liguo
 * date : 2021/3/9 20:54
 * description :
 */
public interface RestBanquetsReserveView {

    public void queryShoppingCartSuccess(ChangeDataBean changeDataBean);
    public void getDataFail(String msg);

    public void clearMyShoppingCart(ChangeDataBean changeDataBean);

    public void getMealType(List<RestMealTypeBean> s);

    public void getOrderNumber(String orderNumber);

}
