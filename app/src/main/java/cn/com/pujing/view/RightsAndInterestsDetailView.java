package cn.com.pujing.view;

import java.util.List;

import cn.com.pujing.entity.RightsAndInterestsBean;
import cn.com.pujing.entity.RightsVoucherVoBean;

/**
 * author : liguo
 * date : 2021/3/30 13:52
 * description :
 */
public interface RightsAndInterestsDetailView {


    public void getRightsAndInterestsListSuccess(RightsAndInterestsBean<List<RightsVoucherVoBean>> rightsAndInterestsBean);

    public void getDataFail(String msg);

}
