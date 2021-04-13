package cn.com.pujing.view;

import java.util.List;

import cn.com.pujing.entity.ActivityTypeBean;

/**
 * author : liguo
 * date : 2021/4/13 16:36
 * description :
 */
public interface ActivitiesView {

    public void getActivityTypeSuccess(List<ActivityTypeBean> activityTypeBeans);

    public void getDataFail(String msg);

}
