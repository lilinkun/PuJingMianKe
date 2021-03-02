package cn.com.pujing.http;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okrx2.adapter.ObservableBody;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import cn.com.pujing.entity.BannerBean;
import cn.com.pujing.entity.HistoryActivitiesBean;
import cn.com.pujing.entity.LoginToken;
import cn.com.pujing.entity.MyInfoBean;
import cn.com.pujing.entity.NotifyInfoBean;
import cn.com.pujing.entity.PhotoBean;
import cn.com.pujing.entity.PublicKey;
import cn.com.pujing.entity.ResponseData;
import cn.com.pujing.entity.SetMealBean;
import cn.com.pujing.http.convert.JsonConvert;
import cn.com.pujing.util.Constants;
import io.reactivex.Observable;

/**
 * author : liguo
 * date : 2021/2/26 11:33
 * description :
 */
public class PujingService {

    public static final String PREFIX = "http://42.49.141.68:2080";
    //            public static final String PREFIX = "http://172.18.9.235"; // 君
//    public static final String PREFIX = "http://172.18.9.207"; // 文
//    public static final String PREFIX = "http://172.18.19.131"; // 金
//    public static final String PREFIX = "http://172.18.19.251"; // 勇
//    public static final String PREFIX = "http://172.18.19.69:8120"; // 鸿
//    public static final String PREFIX = "http://172.18.7.21";
//    public static final String PREFIX = "http://172.18.19.240:8080"; // 华
    public static String GETPUBLICKEY = PREFIX + "/upms-service/rsa/getPublicKey";
    public static String TOKEN = PREFIX + "/upms-service/oauth/token";
    public static String BANNER = PREFIX + "/content-service/banner/getAppBanner";
    public static String NOTIFY = PREFIX + "/content-service/notify/page";
    public static String GETPHOTO = PREFIX + "/content-service/photoWall/getPhoto/5";
    public static String SENDSMS = PREFIX + "/upms-service/oauth/sendSms";
    public static String REGISTER = PREFIX + "/upms-service/customerUser/register";
    public static String MODIFYPWD = PREFIX + "/upms-service/user/restMyPassword";
    public static String MODIFY_PERSONINFO = PREFIX + "/upms-service/user/restMyPassword";
    //编辑信息
    public static String EDITMYINFO = PREFIX + "/upms-service/customerUser/editMyInfo";
    //我的信息
    public static String MYINFO = PREFIX + "/upms-service/customerUser/myInfo";
    //我的活动
    public static String QUERY_MYACTIVITY = PREFIX + "/life-service/activityCalendar/myActivity";
    //历史活动
    public static String QUERY_HISTORY_ACTIVITY = PREFIX + "/life-service/activityCalendar/historyActivity";
    //常规套餐数据
    public static String GETSETMEALDATA = PREFIX + "/restaurant-service/restaurantCycleMealMenu/queryOne";

    //图片链接
    public static String IMG = "/basic-service/attachment/cos/down/";
    public static String H5 = "/app/#/";
    public static String h5_myinfo = PREFIX + H5 + "myEventDetails/";

    /**
     * 获取公钥
     * @return
     */
    public static Observable<ResponseData<PublicKey>> getGetPublickey() {
        return OkGo.<ResponseData<PublicKey>>get(GETPUBLICKEY)
                .converter(new JsonConvert<ResponseData<PublicKey>>() {
                })
                .adapt(new ObservableBody<ResponseData<PublicKey>>());
    }

    /**
     * 登录接口
     * @return
     */
    public static Observable<ResponseData<LoginToken>> getLogin(String userName,String password,String rsaKey) {
        return OkGo.<ResponseData<LoginToken>>post(TOKEN)
                .params(Constants.USERNAME, userName)
                .params(Constants.PASSWORD, password)
                .params(Constants.RSAKEY, rsaKey)
                .params(Constants.APPLICATIONCODE, Constants.ANDROID)
                .converter(new JsonConvert<ResponseData<LoginToken>>() {
                })
                .adapt(new ObservableBody<ResponseData<LoginToken>>());
    }

    /**
     * 首页Banner
     */
    public static Observable<ResponseData<List<BannerBean>>> getBannerData() {
        return OkGo.<ResponseData<List<BannerBean>>>get(BANNER)
                .converter(new JsonConvert<ResponseData<List<BannerBean>>>() {
                })
                .adapt(new ObservableBody<ResponseData<List<BannerBean>>>());
    }

    /**
     * 首页通知
     */
    public static Observable<ResponseData<NotifyInfoBean>> getHomeNotice() {
        return OkGo.<ResponseData<NotifyInfoBean>>get(NOTIFY)
                .params(Constants.QUERYIDENTIFY, Constants.RELEASED)
                .converter(new JsonConvert<ResponseData<NotifyInfoBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<NotifyInfoBean>>());
    }

    /**
     * 首页图片
     */
    public static Observable<ResponseData<PhotoBean>> getHomePhoto() {
        return OkGo.<ResponseData<PhotoBean>>get(GETPHOTO)
                .params(Constants.QUERYIDENTIFY, Constants.RELEASED)
                .converter(new JsonConvert<ResponseData<PhotoBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<PhotoBean>>());
    }

    /**
     * 发送验证码
     */
    public static Observable<ResponseData<String>> sendSms(String phoneNo,boolean isChangePwd) {
        return OkGo.<ResponseData<String>>get(SENDSMS)
                .params(Constants.PHONE, phoneNo)
                .params("checkUser", isChangePwd)
                .converter(new JsonConvert<ResponseData<String>>() {
                })
                .adapt(new ObservableBody<ResponseData<String>>());
    }

    /**
     * 注册
     */
    public static Observable<ResponseData<Boolean>> register(String userName,String phone,String pwd,String captcha) {

        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.USERNAME, userName);
        params.put(Constants.PHONE, phone);
        params.put(Constants.NEWPASSWORD, pwd);
        params.put(Constants.SMSVALIDCODE, captcha);
        JSONObject jsonObject = new JSONObject(params);

        return OkGo.<ResponseData<Boolean>>post(REGISTER)
                .upJson(jsonObject)
                .converter(new JsonConvert<ResponseData<Boolean>>() {})
                .adapt(new ObservableBody<ResponseData<Boolean>>());
    }

    /**
     * 修改密码
     */
    public static Observable<ResponseData<Boolean>> modifyPwd(String phone,String pwd,String captcha) {

        return OkGo.<ResponseData<Boolean>>post(MODIFYPWD)
                .params(Constants.PHONE, phone)
                .params(Constants.NEWPASSWORD, pwd)
                .params(Constants.VALIDCODE, captcha)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .converter(new JsonConvert<ResponseData<Boolean>>() {})
                .adapt(new ObservableBody<ResponseData<Boolean>>());
    }

    /**
     * 编辑信息
     */
    public static Observable<ResponseData<MyInfoBean>> editMyInfo(String nickName,String personal_signature,String personal_birthday,String personal_room_number) {
        HashMap<String, String> params = new HashMap<>();
        if (nickName != null && nickName.trim().length() > 0) {
            params.put(Constants.NICKNAME, nickName);
        }
        if (personal_signature != null && personal_signature.trim().length() > 0) {
            params.put(Constants.SIGNATURE, personal_signature);
        }
        if (personal_room_number != null && personal_room_number.trim().length() > 0) {
            params.put(Constants.ROOMNUMBER, personal_room_number);
        }
        if (personal_birthday != null && personal_birthday.trim().length() > 0) {
            params.put(Constants.BIRTHDAY, personal_birthday);
        }
        JSONObject jsonObject = new JSONObject(params);

        return OkGo.<ResponseData<MyInfoBean>>post(EDITMYINFO)
                .upJson(jsonObject)
                .converter(new JsonConvert<ResponseData<MyInfoBean>>() {})
                .adapt(new ObservableBody<ResponseData<MyInfoBean>>());
    }



    /**
     * 获取个人信息
     */
    public static Observable<ResponseData<MyInfoBean>> getMyInfo() {
        return OkGo.<ResponseData<MyInfoBean>>get(MYINFO)
                .converter(new JsonConvert<ResponseData<MyInfoBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<MyInfoBean>>());
    }

    /**
     * 获取历史活动
     */
    public static Observable<ResponseData<HistoryActivitiesBean>> getHistoryActivitiy() {
        return OkGo.<ResponseData<HistoryActivitiesBean>>get(QUERY_HISTORY_ACTIVITY)
                .converter(new JsonConvert<ResponseData<HistoryActivitiesBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<HistoryActivitiesBean>>());
    }

    /**
     * 获取我的活动
     */
    public static Observable<ResponseData<HistoryActivitiesBean>> getMyActivitiy() {
        return OkGo.<ResponseData<HistoryActivitiesBean>>get(QUERY_MYACTIVITY)
                .converter(new JsonConvert<ResponseData<HistoryActivitiesBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<HistoryActivitiesBean>>());
    }


    /**
     * 获取套餐数据
     */
    public static Observable<ResponseData<List<SetMealBean>>> getSetMealData(String dateStr,int type) {
        return OkGo.<ResponseData<List<SetMealBean>>>get(GETSETMEALDATA)
                .params("time","2021-02-25")
//                .params("time",dateStr)
                .params("type",3)
//                .params("type",type)
                .converter(new JsonConvert<ResponseData<List<SetMealBean>>>() {
                })
                .adapt(new ObservableBody<ResponseData<List<SetMealBean>>>());
    }



}
