package cn.com.pujing.view;

import java.util.List;

import cn.com.pujing.entity.RightsAndInterestsBean;

/**
 * author : liguo
 * date : 2021/3/29 16:52
 * description :
 */
public interface RightsAndInterestsView {

    public void getRightsAndInterestsListSuccess(List<RightsAndInterestsBean> rightsAndInterestsBeans);

    public void getDataFail(String msg);
}
