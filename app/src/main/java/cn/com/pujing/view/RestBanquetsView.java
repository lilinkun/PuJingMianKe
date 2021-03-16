package cn.com.pujing.view;

import java.util.List;

import cn.com.pujing.entity.BanquetBean;
import cn.com.pujing.entity.ChangeDataBean;
import cn.com.pujing.entity.SetMealBean;

/**
 * author : liguo
 * date : 2021/3/1 18:03
 * description :
 */
public interface RestBanquetsView {

    public void getBanquetSuccess(BanquetBean banquetBean);

    public void getDataFail(String msg);

    public void onAddSuccess(ChangeDataBean changeDataBean);

    public void clearMyShoppingCart(ChangeDataBean changeDataBean);

    public void queryShoppingCartSuccess(ChangeDataBean changeDataBean,int type);

    public void addRestSuccess(Boolean b);

}
