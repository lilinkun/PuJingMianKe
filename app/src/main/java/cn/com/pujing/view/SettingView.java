package cn.com.pujing.view;

import cn.com.pujing.entity.UpdateBean;

/**
 * author : liguo
 * date : 2021/4/22 10:19
 * description :
 */
public interface SettingView {

    public void getUpdateDataSuccess(UpdateBean updateBean);

    public void getDataFail(String msg);

}
