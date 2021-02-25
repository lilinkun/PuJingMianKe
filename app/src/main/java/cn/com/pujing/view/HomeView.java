package cn.com.pujing.view;

import java.util.List;

import cn.com.pujing.entity.BannerBean;

/**
 * author : liguo
 * date : 2021/2/25 14:12
 * description :首页
 */
public interface HomeView {

    void getBannerDataSuccess(List<BannerBean> data);
    void getDataError(String message);
}
