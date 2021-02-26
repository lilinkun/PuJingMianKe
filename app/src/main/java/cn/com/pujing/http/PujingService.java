package cn.com.pujing.http;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okrx2.adapter.ObservableBody;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.entity.BannerBean;
import cn.com.pujing.entity.LoginToken;
import cn.com.pujing.entity.NotifyInfoBean;
import cn.com.pujing.entity.PhotoBean;
import cn.com.pujing.entity.PublicKey;
import cn.com.pujing.entity.ResponseData;
import cn.com.pujing.entity.SendSms;
import cn.com.pujing.http.convert.JsonConvert;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.Urls;
import io.reactivex.Observable;

/**
 * author : liguo
 * date : 2021/2/26 11:33
 * description :
 */
public class PujingService {

    public static final String PREFIX = "http://42.49.141.68:2080";
//    public static final String PREFIX = "http://172.18.9.235"; // 君
    public static String getpublickey = PREFIX + "/upms-service/rsa/getPublicKey";
    public static String token = PREFIX + "/upms-service/oauth/token";
    public static String banner = PREFIX + "/content-service/banner/getAppBanner";
    public static String notify = PREFIX + "/content-service/notify/page";
    public static String getphoto = PREFIX + "/content-service/photoWall/getPhoto/5";
    public static String sendsms = PREFIX + "/upms-service/oauth/sendSms";
    public static String register = PREFIX + "/upms-service/customerUser/register";
    public static String modifyPwd = PREFIX + "/upms-service/user/restMyPassword";

    /**
     * 获取公钥
     * @return
     */
    public static Observable<ResponseData<PublicKey>> getGetPublickey() {
        return OkGo.<ResponseData<PublicKey>>get(getpublickey)
                .converter(new JsonConvert<ResponseData<PublicKey>>() {
                })
                .adapt(new ObservableBody<ResponseData<PublicKey>>());
    }

    /**
     * 登录接口
     * @return
     */
    public static Observable<ResponseData<LoginToken>> getLogin(String userName,String password,String rsaKey) {
        return OkGo.<ResponseData<LoginToken>>post(token)
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
        return OkGo.<ResponseData<List<BannerBean>>>get(banner)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .converter(new JsonConvert<ResponseData<List<BannerBean>>>() {
                })
                .adapt(new ObservableBody<ResponseData<List<BannerBean>>>());
    }

    /**
     * 首页通知
     */
    public static Observable<ResponseData<NotifyInfoBean>> getHomeNotice() {
        return OkGo.<ResponseData<NotifyInfoBean>>get(notify)
                .params(Constants.QUERYIDENTIFY, Constants.RELEASED)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .converter(new JsonConvert<ResponseData<NotifyInfoBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<NotifyInfoBean>>());
    }

    /**
     * 首页图片
     */
    public static Observable<ResponseData<PhotoBean>> getHomePhoto() {
        return OkGo.<ResponseData<PhotoBean>>get(getphoto)
                .params(Constants.QUERYIDENTIFY, Constants.RELEASED)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .converter(new JsonConvert<ResponseData<PhotoBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<PhotoBean>>());
    }

    /**
     * 发送验证码
     */
    public static Observable<ResponseData<String>> sendSms(String phoneNo,boolean isChangePwd) {
        return OkGo.<ResponseData<String>>get(sendsms)
                .params(Constants.PHONE, phoneNo)
                .params("checkUser", isChangePwd)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
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

        return OkGo.<ResponseData<Boolean>>post(register)
                .upJson(jsonObject)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .converter(new JsonConvert<ResponseData<Boolean>>() {})
                .adapt(new ObservableBody<ResponseData<Boolean>>());
    }

    /**
     * 修改密码
     */
    public static Observable<ResponseData<Boolean>> modifyPwd(String phone,String pwd,String captcha) {

        /*HashMap<String, String> params = new HashMap<>();
        params.put(Constants.PHONE, phone);
        params.put(Constants.NEWPASSWORD, pwd);
        params.put(Constants.VALIDCODE, captcha);
        JSONObject jsonObject = new JSONObject(params);*/

        return OkGo.<ResponseData<Boolean>>post(modifyPwd)
                .params(Constants.PHONE, phone)
                .params(Constants.NEWPASSWORD, pwd)
                .params(Constants.VALIDCODE, captcha)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .converter(new JsonConvert<ResponseData<Boolean>>() {})
                .adapt(new ObservableBody<ResponseData<Boolean>>());
    }



}
