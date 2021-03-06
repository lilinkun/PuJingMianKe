package cn.com.pujing.view;

import java.util.List;

import cn.com.pujing.entity.PagesBean;
import cn.com.pujing.entity.PictureWallBean;

/**
 * author : liguo
 * date : 2021/4/15 15:36
 * description :
 */
public interface PictureWallView {

    public void showPicInfoListSuccess(PagesBean<PictureWallBean> activityTypeBeans);

    public void getDataFail(String msg);

    public void addCollectSuccess(Object o);
    public void cancelCollectSuccess(Object o);
    public void doLikeSuccess(Object o);
    public void unDoLikeSuccess(Object o);
}
