package cn.com.pujing.entity;

import java.io.Serializable;

/**
 * author : liguo
 * date : 2021/3/29 18:42
 * description :
 */
public class ChooseRightsAndInterestsBean implements Serializable {
    public String couponName;
    public String couponContent;
    public String couponDate;
    public boolean isChoose;

    public ChooseRightsAndInterestsBean(){

    }

    public ChooseRightsAndInterestsBean(String couponName, String couponContent, String couponDate, boolean isChoose) {
        this.couponName = couponName;
        this.couponContent = couponContent;
        this.couponDate = couponDate;
        this.isChoose = isChoose;
    }
}
