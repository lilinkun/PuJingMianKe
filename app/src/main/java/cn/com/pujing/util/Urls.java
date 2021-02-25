package cn.com.pujing.util;

public class Urls {
    public static String IMG = "/basic-service/attachment/cos/down/";
//    public static String H5 = "/app/#/";
    public static String H5 = "/app/#/";

    //        public static final String PREFIX = "http://172.18.9.235"; // 君
//    public static final String PREFIX = "http://172.18.9.207"; // 文
//    public static final String PREFIX = "http://172.18.19.131"; // 金
//    public static final String PREFIX = "http://172.18.19.251"; // 勇
//    public static final String PREFIX = "http://172.18.19.69"; // 鸿
//    public static final String PREFIX = "http://172.18.7.21";
//    public static final String PREFIX = "http://172.18.19.240:8080"; // 华
    public static final String PREFIX = "http://42.49.141.68:2080";

    public static String GETPUBLICKEY = PREFIX + "/upms-service/rsa/getPublicKey";
    public static String SENDSMS = PREFIX + "/upms-service/oauth/sendSms";
    public static String REGISTER = PREFIX + "/upms-service/customerUser/register";
    public static String TOKEN = PREFIX + "/upms-service/oauth/token";
    public static String MYINFO = PREFIX + "/upms-service/customerUser/myInfo";
    public static String GETTENCENTKEY = PREFIX + "/basic-service/attachment/cos/getTencentKey";
    public static String GETFILEPATHKEY = PREFIX + "/basic-service/attachment/cos/getFilePathKey";
    public static String ATTACHMENT = PREFIX + "/basic-service/attachment";
    public static String FEEDBACKSAVE = PREFIX + "/content-service/feedback/save";
    public static String EDITMYINFO = PREFIX + "/upms-service/customerUser/editMyInfo";//编辑信息
    public static String LOGINOUT = PREFIX + "/upms-service/oauth/revokeToken"; //退出登录

    public static String BANNER = PREFIX + "/content-service/banner/getAppBanner";
    public static String NOTIFY = PREFIX + "/content-service/notify/page";

    public static String PHOTOWALL = PREFIX + "/content-service/photoWall/page";
    public static String PHOTOWALLCOLLECT = PREFIX + "/content-service/photoWall/collectStatistics/";
    public static String GETPHOTO = PREFIX + "/content-service/photoWall/getPhoto/5";

    public static String GETALLCATEGORY = PREFIX + "/life-service/activityCategory/getAllCategory";
    public static String ACTIVITYCALENDAR = PREFIX + "/life-service/activityCalendar/page";

    public static String ACTIVITYDATE = PREFIX + "/life-service/activityCalendar/queryActivityDateList";
    public static String ACTIVITYDATE_ANOTHER = PREFIX + "/life-service/userNotes/queryActivityDateList";
    public static String QUERYSELECTDAY = PREFIX + "/life-service/activityCalendar/querySelectDay";
    public static String QUERYSELECTDAY_ANOTHER = PREFIX + "/life-service/userNotes/querySelectDay";
    public static String QUERYSELECTDAY_ADD = PREFIX + "/life-service/userNotes/add";
    //我的活动
    public static String QUERY_MYACTIVITY = PREFIX + "/life-service/activityCalendar/myActivity";
    //历史活动
    public static String QUERY_HISTORY_ACTIVITY = PREFIX + "/life-service/activityCalendar/historyActivity";

    public static String NOTICE = PREFIX + H5 + "notice";
    public static String EVENTDETAILS = PREFIX + H5 + "eventDetails/";
    public static String SURVEYLIST = PREFIX + H5 + "surveyList";

    public static String MSG = PREFIX + "/messagePush/page";

    //字典
    public static String DIRECTORY = PREFIX + "/upms-service/dict/type/";
    //反馈类型
    public static String OPINION_TYPE = DIRECTORY + "opinion_type";
}
