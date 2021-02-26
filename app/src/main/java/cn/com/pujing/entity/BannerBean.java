package cn.com.pujing.entity;

import java.io.Serializable;

/**
 * author : liguo
 * date : 2021/2/25 14:18
 * description :首页轮播图
 */
public class BannerBean implements Serializable {
    private String picture;
    private String linkAddress;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }
}
