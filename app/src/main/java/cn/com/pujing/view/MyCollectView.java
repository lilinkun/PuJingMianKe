package cn.com.pujing.view;

import cn.com.pujing.entity.CollectBean;
import cn.com.pujing.entity.PagesBean;

/**
 * author : liguo
 * date : 2021/4/16 10:54
 * description :
 */
public interface MyCollectView {

    public void getMyCollectSuccess(PagesBean<CollectBean> pagesBean);

    public void getDataFail(String msg);

}
