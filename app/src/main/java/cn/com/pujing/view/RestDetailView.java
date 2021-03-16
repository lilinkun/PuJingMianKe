package cn.com.pujing.view;

import cn.com.pujing.entity.ChangeDataBean;
import cn.com.pujing.entity.RestDetailBean;

/**
 * author : liguo
 * date : 2021/3/9 17:11
 * description :
 */
public interface RestDetailView {

    public void getRestDetail(RestDetailBean restDetailBean);

    public void onChangeSuccess(ChangeDataBean changeDataBean);

    public void getDataFail(String msg);


}
