package cn.com.pujing.view;

import cn.com.pujing.entity.PhotoBean;

/**
 * author : liguo
 * date : 2021/4/19 17:42
 * description :
 */
public interface ShowPhotoView {

    void queryPhotoWall(PhotoBean photoBean);
    void getDataError(String message);
}
