package cn.com.pujing.util;

import static cn.com.pujing.http.PujingService.PREFIX;

public class Urls {
    public static String IMG = "/basic-service/attachment/cos/down/";
//    public static String H5 = "/app/#/";
    public static String H5 = "/app/#/";

//  public static final String PREFIX = "http://172.18.9.235"; // 君
//  public static final String PREFIX = "http://172.18.9.207"; // 文
//  public static final String PREFIX = "http://172.18.19.131"; // 金
//  public static final String PREFIX = "http://172.18.19.251"; // 勇
//  public static final String PREFIX = "http://172.18.19.69"; // 鸿
//  public static final String PREFIX = "http://172.18.7.21";
//  public static final String PREFIX = "http://172.18.19.240:8080"; // 华
//    public static final String PREFIX = "http://172.18.9.94"; //曜
//    public static final String PREFIX = "http://42.49.141.68:2080";//测试


    public static String PHOTOWALL = PREFIX + "/content-service/photoWall/page";
    public static String PHOTOWALLCOLLECT = PREFIX + "/content-service/photoWall/collectStatistics/";

    public static String GETALLCATEGORY = PREFIX + "/life-service/activityCategory/getAllCategory";
    public static String ACTIVITYCALENDAR = PREFIX + "/life-service/activityCalendar/currentHotActivity";
    public static String QUERYSELECTDAY_ADD = PREFIX + "/life-service/userNotes/add";

    public static String NOTICE = PREFIX + H5 + "notice";
    public static String EVENTDETAILS = PREFIX + H5 + "eventDetails/";
    public static String SURVEYLIST = PREFIX + H5 + "surveyList";

    public static String MSG = PREFIX + "/messagePush/page";

    //字典
    public static String DIRECTORY = PREFIX + "/upms-service/dict/type/";

}
