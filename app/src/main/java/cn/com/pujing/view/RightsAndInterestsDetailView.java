package cn.com.pujing.view;

import cn.com.pujing.entity.RightsAndInterestsBean;

/**
 * author : liguo
 * date : 2021/3/30 13:52
 * description :
 */
public interface RightsAndInterestsDetailView {


    public void getRightsAndInterestsListSuccess(RightsAndInterestsBean rightsAndInterestsBean);

    public void getDataFail(String msg);

}
