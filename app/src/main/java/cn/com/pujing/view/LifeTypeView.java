package cn.com.pujing.view;

import java.util.List;

import cn.com.pujing.entity.LifeTypeBean;
import cn.com.pujing.entity.ServicePutawayManageTimeBean;

/**
 * author : liguo
 * date : 2021/3/26 16:21
 * description :
 */
public interface LifeTypeView {
    public void getLifeType(LifeTypeBean lifeTypeBeans);
    public void getLifeTypeDataFail(String msg);

    public void getLifeTimeSuccess(List<ServicePutawayManageTimeBean> o);
    public void getLifeTimeFail(String msg);
}
