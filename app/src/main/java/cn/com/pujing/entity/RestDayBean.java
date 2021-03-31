package cn.com.pujing.entity;

import java.io.Serializable;

/**
 * author : liguo
 * date : 2021/3/7 13:18
 * description :
 */
public class RestDayBean implements Serializable {

    public String weekDay;
    public String dateDay;
    public String monthDay;
    /**
     * 0 为否 1为是
     */
    public String flag = "0";

}
