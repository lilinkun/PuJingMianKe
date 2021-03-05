package cn.com.pujing.view;

import java.util.List;

import cn.com.pujing.entity.BanquetBean;
import cn.com.pujing.entity.SetMealBean;

/**
 * author : liguo
 * date : 2021/3/1 18:03
 * description :
 */
public interface RestBanquetsView {

    public void getBanquetSuccess(List<BanquetBean> banquetBeanList);

    public void getDataFail(String msg);

}
