package cn.com.pujing.http;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okrx2.adapter.ObservableBody;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import cn.com.pujing.entity.AddRestBean;
import cn.com.pujing.entity.AttachmentBean;
import cn.com.pujing.entity.BannerBean;
import cn.com.pujing.entity.BanquetBean;
import cn.com.pujing.entity.ChangeDataBean;
import cn.com.pujing.entity.FeedbackBean;
import cn.com.pujing.entity.HistoryActivitiesBean;
import cn.com.pujing.entity.LoginToken;
import cn.com.pujing.entity.MyInfoBean;
import cn.com.pujing.entity.NotifyInfoBean;
import cn.com.pujing.entity.PhotoBean;
import cn.com.pujing.entity.PublicKey;
import cn.com.pujing.entity.QuerySelectDayBean;
import cn.com.pujing.entity.ResponseData;
import cn.com.pujing.entity.RestBanquetsBean;
import cn.com.pujing.entity.RestDetailBean;
import cn.com.pujing.entity.RestMealBean;
import cn.com.pujing.entity.RestMealTypeBean;
import cn.com.pujing.entity.RestOrderBean;
import cn.com.pujing.entity.RestTypeBean;
import cn.com.pujing.entity.RoutineRecordBean;
import cn.com.pujing.entity.SetMealBean;
import cn.com.pujing.http.convert.JsonConvert;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.Methods;
import io.reactivex.Observable;

/**
 * author : liguo
 * date : 2021/2/26 11:33
 * description :
 */
public class PujingService {

    public static final String PREFIX = "http://121.37.234.112:80"; //测试
//    public static final String PREFIX = "http://42.49.141.68:2080"; //测试
//    public static final String PREFIX = "http://172.18.9.94"; //曜
//                public static final String PREFIX = "http://172.18.9.235"; // 君
//    public static final String PREFIX = "http://172.18.9.207"; // 文
//    public static final String PREFIX = "http://172.18.9.168:8120"; // 鹏
//    public static final String PREFIX = "http://172.18.19.131"; // 金
//    public static final String PREFIX = "http://172.18.19.251"; // 勇
//    public static final String PREFIX = "http://172.18.19.240"; // 鸿
//    public static final String PREFIX = "http://172.18.7.21";
//    public static final String PREFIX = "http://172.18.19.240:8080"; // 华
    public static String GETPUBLICKEY = PREFIX + "/upms-service/rsa/getPublicKey";

    public static String GETTENCENTKEY = PREFIX + "/basic-service/attachment/cos/getTencentKey";
    public static String GETFILEPATHKEY = PREFIX + "/basic-service/attachment/cos/getFilePathKey";

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
    public static String GETSETMEALDATA = PREFIX + "/restaurant-service/restaurantCycleMealMenu/queryOneByApp";
//    public static String GETSETMEALDATA = PREFIX + "/restaurant-service/restaurantCycleMealMenu/queryOne";
    //常规套餐数据
    public static String SAVESETMEALDATA = PREFIX + "/restaurant-service/restaurantCycleRecord/appSave";
    //宴会餐和零点餐数据
    public static String GETBANQUETSDATA = PREFIX + "/restaurant-service/restaurantMenuItem/getBanquetInfo";
    //常规餐数据
    public static String GETROUTINEDATA = PREFIX + "/restaurant-service/restaurantCycleRecord/appGetCycleCategory";
    //通过id查询照片墙
    public static String QUERYPHOTOWALL = PREFIX + "/content-service/photoWall/";

    //图片链接
    public static String IMG = "/basic-service/attachment/cos/down/";
    public static String H5 = "/app/#/";
    public static String h5_myinfo = PREFIX + H5 + "myEventDetails/";

    //字典
    public static String DIRECTORY = PREFIX + "/upms-service/dict/type/";
    //常规餐类型
    public static String REST_TYPE = DIRECTORY + "app_meal_type";
//    public static String REST_TYPE = DIRECTORY + "meal_type";
    //反馈类型
    public static String OPINION_TYPE = DIRECTORY + "opinion_type";
    //餐次
    public static String MEAL_TIMES_TYPE = DIRECTORY + "meal_times_type";

    //餐次的时间段
    public static String MEAL_TIMES = PREFIX + "/restaurant-service/restaurantCycleRecord/appGetMealType";


    public static String ACTIVITYDATE = PREFIX + "/life-service/activityCalendar/queryActivityDateList";
    public static String ACTIVITYDATE_ANOTHER = PREFIX + "/life-service/userNotes/queryActivityDateList";
    public static String QUERYSELECTDAY = PREFIX + "/life-service/activityCalendar/querySelectDay";
    public static String QUERYSELECTDAY_ANOTHER = PREFIX + "/life-service/userNotes/querySelectDay";
    public static String QUERYSELECTDAY_ADD = PREFIX + "/life-service/userNotes/add";

    public static String FEEDBACKSAVE = PREFIX + "/content-service/feedback/save";

    public static String QUERYSHOPPINGCART = PREFIX + "/restaurant-service/restaurantShoppingCart/";
    public static String GETSHOPPINGCART = PREFIX + "/restaurant-service/restaurantShoppingCart/getMyShoppingCart/";
    public static String CLEARMYSHOPPINGCART = PREFIX + "/restaurant-service/restaurantShoppingCart/clearMyShoppingCart/";

    public static String QUERYRESTDETAIL= PREFIX + "/restaurant-service/restaurantFoodCategory/";
    public static String QUERYRESTMEALDETAIL= PREFIX + "/restaurant-service/restaurantFoodMeal/";
    public static String RESTORDER= PREFIX + "/restaurant-service/restaurantOrder";
    public static String RESTORDERDETAIL = PREFIX + "/restaurant-service/restaurantOrder/getOrderDetail/";
    public static String RESTORDERCLEAN = PREFIX + "/restaurant-service/restaurantOrder/cleanOrder/";
    public static String ADDFOOD = PREFIX + "/restaurant-service/restaurantOrder/addFood";
    public static String CHECKCYCLERECORD = PREFIX + "/restaurant-service/restaurantCycleRecord/checkCycleRecord";
    public static String APPUPDATE = PREFIX + "/restaurant-service/restaurantCycleRecord/appUpdate";
    /**
     *退出登录
     */
    public static String LOGINOUT = PREFIX + "/upms-service/oauth/revokeToken";


    public static String ATTACHMENT = PREFIX + "/basic-service/attachment";

    public static String NOTICE = PREFIX + H5 + "notice";
    public static String EVENTDETAILS = PREFIX + H5 + "eventDetails/";
    public static String SURVEYLIST = PREFIX + H5 + "surveyList";

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
     * 通过id查询照片墙详情
     */
    public static Observable<ResponseData<PhotoBean>> queryPhotoWall(String id) {
        return OkGo.<ResponseData<PhotoBean>>get(QUERYPHOTOWALL+id)
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
     * 修改头像
     */
    public static Observable<ResponseData<MyInfoBean>> modifyHeadImg(JSONObject jsonObject) {
        return OkGo.<ResponseData<MyInfoBean>>post(EDITMYINFO)
                .upJson(jsonObject)
                .converter(new JsonConvert<ResponseData<MyInfoBean>>() {
                })
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
    public static Observable<ResponseData<List<SetMealBean>>> getSetMealData(String dateStr,String type) {
        return OkGo.<ResponseData<List<SetMealBean>>>get(GETSETMEALDATA)
//                .params("time","2021-02-25")
                .params("time",dateStr)
//                .params("type",3)
                .params("type",type)
                .converter(new JsonConvert<ResponseData<List<SetMealBean>>>() {
                })
                .adapt(new ObservableBody<ResponseData<List<SetMealBean>>>());
    }


    /**
     * 获取零点餐和宴会餐数据
     */
    public static Observable<ResponseData<BanquetBean>> getBanquetsData(int type) {
        return OkGo.<ResponseData<BanquetBean>>get(GETBANQUETSDATA)
                .params("type",type)
                .params("menuType",type)
                .converter(new JsonConvert<ResponseData<BanquetBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<BanquetBean>>());
    }


    /**
     * 获取常规餐类别
     */
    public static Observable<ResponseData<List<RestTypeBean>>> getSetMealTypeData() {
        return OkGo.<ResponseData<List<RestTypeBean>>>get(REST_TYPE)
                .converter(new JsonConvert<ResponseData<List<RestTypeBean>>>() {
                })
                .adapt(new ObservableBody<ResponseData<List<RestTypeBean>>>());
    }


    /**
     * 保存常规餐数据
     */
    public static Observable<ResponseData<Boolean>> saveSetMealData(String json) {
        return OkGo.<ResponseData<Boolean>>post(SAVESETMEALDATA)
                .upJson(json)
                .converter(new JsonConvert<ResponseData<Boolean>>() {
                })
                .adapt(new ObservableBody<ResponseData<Boolean>>());
    }



    /**
     * 获取社区日历
     */
    public static Observable<ResponseData<List<Long>>> getCommunityData(String startTime,String endTime,int type) {
        String url;
        if (type == 1){
            url = ACTIVITYDATE;
        }else {
            url = ACTIVITYDATE_ANOTHER;
        }

        return OkGo.<ResponseData<List<Long>>>get(url)
                .params(Constants.STARTTIME, startTime)
                .params(Constants.ENDTIME, endTime)
                .converter(new JsonConvert<ResponseData<List<Long>>>() {
                })
                .adapt(new ObservableBody<ResponseData<List<Long>>>());
    }


    /**
     * 获取点击日历的事项
     */
    public static Observable<ResponseData<List<QuerySelectDayBean>>> querySelectDay(String dayTime,int type) {
        String url;
        if (type == 1){
            url = QUERYSELECTDAY;
        }else {
            url = QUERYSELECTDAY_ANOTHER;
        }
        return OkGo.<ResponseData<List<QuerySelectDayBean>>>get(url)
                .params(Constants.SELECTDAY, dayTime)
                .converter(new JsonConvert<ResponseData<List<QuerySelectDayBean>>>() {
                })
                .adapt(new ObservableBody<ResponseData<List<QuerySelectDayBean>>>());
    }


    /**
     * 反馈类型
     */
    public static Observable<ResponseData<List<FeedbackBean>>> giveFeedbackType() {

        return OkGo.<ResponseData<List<FeedbackBean>>>get(OPINION_TYPE)
                .converter(new JsonConvert<ResponseData<List<FeedbackBean>>>() {
                })
                .adapt(new ObservableBody<ResponseData<List<FeedbackBean>>>());
    }


    /**
     * 保存反馈信息
     */
    public static Observable<ResponseData<Boolean>> saveFeedback(JSONObject jsonObject) {

        return OkGo.<ResponseData<Boolean>>post(FEEDBACKSAVE)
                .upJson(jsonObject)
                .converter(new JsonConvert<ResponseData<Boolean>>() {
                })
                .adapt(new ObservableBody<ResponseData<Boolean>>());
    }

    /**
     * 保存反馈图片
     */
    public static Observable<ResponseData<AttachmentBean>> saveFeedFile(JSONObject jsonObject) {

        return OkGo.<ResponseData<AttachmentBean>>post(ATTACHMENT)
                .upJson(jsonObject)
                .converter(new JsonConvert<ResponseData<AttachmentBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<AttachmentBean>>());
    }

    /**
     * 添加购物车
     */
    public static Observable<ResponseData<ChangeDataBean>> addShoppingCart(int menuItemId, int quantity,int type){
        HashMap<String, String> params = new HashMap<>();

        params.put("menuItemId", menuItemId+"");
        params.put("quantity", quantity+"");
        params.put("type",type+"");

        JSONObject jsonObject = new JSONObject(params);

        return OkGo.<ResponseData<ChangeDataBean>>post(QUERYSHOPPINGCART)
                .upJson(jsonObject)
                .converter(new JsonConvert<ResponseData<ChangeDataBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<ChangeDataBean>>());
    }

    /**
     * 查询购物车
     * @return
     */
    public static Observable<ResponseData<ChangeDataBean>> queryShoppingCart(int type){

        return OkGo.<ResponseData<ChangeDataBean>>get(GETSHOPPINGCART+type)
                .converter(new JsonConvert<ResponseData<ChangeDataBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<ChangeDataBean>>());
    }

    /**
     * 清除购物车
     * @return
     */
    public static Observable<ResponseData<ChangeDataBean>> clearMyShoppingCart(int type){

        return OkGo.<ResponseData<ChangeDataBean>>delete(CLEARMYSHOPPINGCART+type)
                .converter(new JsonConvert<ResponseData<ChangeDataBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<ChangeDataBean>>());
    }

    /**
     * 查询单品菜单详情
     * @return
     */
    public static Observable<ResponseData<RestDetailBean>> queryRestDetail(int id){

        return OkGo.<ResponseData<RestDetailBean>>get(QUERYRESTDETAIL+id)
                .converter(new JsonConvert<ResponseData<RestDetailBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<RestDetailBean>>());
    }
    /**
     * 查询套餐菜单详情
     * @return
     */
    public static Observable<ResponseData<RestMealBean>> queryRestMealDetail(int id){

        return OkGo.<ResponseData<RestMealBean>>get(QUERYRESTMEALDETAIL+id)
                .converter(new JsonConvert<ResponseData<RestMealBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<RestMealBean>>());
    }

    /**
     * 查询餐次
     * @return
     */
    public static Observable<ResponseData<List<RestMealTypeBean>>> queryMealTimesType(){

        return OkGo.<ResponseData<List<RestMealTypeBean>>>get(MEAL_TIMES)
                .converter(new JsonConvert<ResponseData<List<RestMealTypeBean>>>() {
                })
                .adapt(new ObservableBody<ResponseData<List<RestMealTypeBean>>>());
    }

    /**
     * 下单
     * @return
     */
    public static Observable<ResponseData<String>> restOrder(String orderInfo){

        return OkGo.<ResponseData<String>>post(RESTORDER)
                .upJson(orderInfo)
                .converter(new JsonConvert<ResponseData<String>>() {
                })
                .adapt(new ObservableBody<ResponseData<String>>());
    }


    /**
     * 获得订单详情
     * @return
     */
    public static Observable<ResponseData<RestBanquetsBean>> orderDetail(String orderNumber){

        return OkGo.<ResponseData<RestBanquetsBean>>get(RESTORDERDETAIL+orderNumber)
                .converter(new JsonConvert<ResponseData<RestBanquetsBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<RestBanquetsBean>>());
    }


    /**
     * 取消订单
     * @return
     */
    public static Observable<ResponseData<Boolean>> restOrderClean(String orderNumber){

        return OkGo.<ResponseData<Boolean>>put(RESTORDERCLEAN+orderNumber)
                .converter(new JsonConvert<ResponseData<Boolean>>() {
                })
                .adapt(new ObservableBody<ResponseData<Boolean>>());
    }

    /**
     * 加菜
     * @return
     */
    public static Observable<ResponseData<Boolean>> addRest(String json){

        return OkGo.<ResponseData<Boolean>>put(ADDFOOD)
                .upJson(json)
                .converter(new JsonConvert<ResponseData<Boolean>>() {
                })
                .adapt(new ObservableBody<ResponseData<Boolean>>());
    }

    /**
     * 检查用户常规餐是否点全
     * @return
     */
    public static Observable<ResponseData<Boolean>> checkCycleRecord(){

        return OkGo.<ResponseData<Boolean>>get(CHECKCYCLERECORD)
                .converter(new JsonConvert<ResponseData<Boolean>>() {
                })
                .adapt(new ObservableBody<ResponseData<Boolean>>());
    }

    /**
     *
     * @return
     */
    public static Observable<ResponseData<RoutineRecordBean>> getRoutineData(String curDate){

        if (curDate.equals("")){

            return OkGo.<ResponseData<RoutineRecordBean>>get(GETROUTINEDATA)
                    .converter(new JsonConvert<ResponseData<RoutineRecordBean>>() {
                    })
                    .adapt(new ObservableBody<ResponseData<RoutineRecordBean>>());
        }else {
            return OkGo.<ResponseData<RoutineRecordBean>>get(GETROUTINEDATA)
                    .params("cycleTime",curDate)
                    .converter(new JsonConvert<ResponseData<RoutineRecordBean>>() {
                    })
                    .adapt(new ObservableBody<ResponseData<RoutineRecordBean>>());
        }
    }

    /**
     * 常规餐提交
     */
    public static Observable<ResponseData<Boolean>> submitRest(){

            return OkGo.<ResponseData<Boolean>>put(APPUPDATE)
                    .converter(new JsonConvert<ResponseData<Boolean>>() {
                    })
                    .adapt(new ObservableBody<ResponseData<Boolean>>());

    }

}
