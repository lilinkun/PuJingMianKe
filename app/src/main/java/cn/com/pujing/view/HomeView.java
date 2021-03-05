package cn.com.pujing.view;

import java.util.List;

import cn.com.pujing.entity.BannerBean;
import cn.com.pujing.entity.NotifyInfoBean;
import cn.com.pujing.entity.PhotoBean;

/**
 * author : liguo
 * date : 2021/2/26 17:04
 * description : 首页
 */
public interface HomeView {

    void getBannerDataSuccess(List<BannerBean> data);

    void getBannerDataFail(String msg);

    void getNoticeDataSuccess(NotifyInfoBean notifyInfoBean);

    void getPhotoDataSuccess(PhotoBean photoBean);

    void getDataError(String message);

}
