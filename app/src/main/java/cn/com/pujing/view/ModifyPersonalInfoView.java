package cn.com.pujing.view;

import cn.com.pujing.entity.MyInfoBean;

/**
 * author : liguo
 * date : 2021/3/1 10:24
 * description :
 */
public interface ModifyPersonalInfoView {
    public void modifyPersonalInfoSuccess(MyInfoBean myInfoBean);

    public void modifyFail(String msg);

}
