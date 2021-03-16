package cn.com.pujing.view;

import cn.com.pujing.entity.RestBanquetsBean;
import cn.com.pujing.entity.RestOrderBean;

/**
 * author : liguo
 * date : 2021/3/10 18:34
 * description :
 */
public interface RanquetsOrderView {

    public void RanquetsOrderDataSuccess(RestBanquetsBean restOrderBean);

    public void getDataFail(String msg);

    public void exitOrderSuccess(boolean isTrue);

}
