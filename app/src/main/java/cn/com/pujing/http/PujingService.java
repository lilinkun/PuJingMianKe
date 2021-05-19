package cn.com.pujing.http;

import android.os.Build;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okrx2.adapter.ObservableBody;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.pujing.entity.ActivityBean;
import cn.com.pujing.entity.ActivityTypeBean;
import cn.com.pujing.entity.AttachmentBean;
import cn.com.pujing.entity.BannerBean;
import cn.com.pujing.entity.BanquetBean;
import cn.com.pujing.entity.BillsBean;
import cn.com.pujing.entity.ChangeDataBean;
import cn.com.pujing.entity.CollectBean;
import cn.com.pujing.entity.CommemorationDayBean;
import cn.com.pujing.entity.DeviceBean;
import cn.com.pujing.entity.FeedbackBean;
import cn.com.pujing.entity.HistoryActivitiesBean;
import cn.com.pujing.entity.HistoryBillsBean;
import cn.com.pujing.entity.HotActivityBean;
import cn.com.pujing.entity.LifeTypeBean;
import cn.com.pujing.entity.LoginToken;
import cn.com.pujing.entity.MessageBean;
import cn.com.pujing.entity.MyBillBean;
import cn.com.pujing.entity.MyCardBean;
import cn.com.pujing.entity.MyFeedbackBean;
import cn.com.pujing.entity.MyInfoBean;
import cn.com.pujing.entity.NotifyInfoBean;
import cn.com.pujing.entity.OrderDetailBean;
import cn.com.pujing.entity.OrderItemBean;
import cn.com.pujing.entity.PagesBean;
import cn.com.pujing.entity.PhotoBean;
import cn.com.pujing.entity.PictureWallBean;
import cn.com.pujing.entity.PublicKey;
import cn.com.pujing.entity.QuerySelectDayBean;
import cn.com.pujing.entity.ReserveDeviceBean;
import cn.com.pujing.entity.ResponseData;
import cn.com.pujing.entity.RestBanquetsBean;
import cn.com.pujing.entity.RestDetailBean;
import cn.com.pujing.entity.RestMealBean;
import cn.com.pujing.entity.RestMealTypeBean;
import cn.com.pujing.entity.RestTypeBean;
import cn.com.pujing.entity.RightsAndInterestsBean;
import cn.com.pujing.entity.RightsVoucherVoBean;
import cn.com.pujing.entity.RoutineRecordBean;
import cn.com.pujing.entity.ServiceBean;
import cn.com.pujing.entity.ServicePutawayManageTimeBean;
import cn.com.pujing.entity.SetMealBean;
import cn.com.pujing.entity.UpdateBean;
import cn.com.pujing.entity.VenueBean;
import cn.com.pujing.entity.VenueDetailBean;
import cn.com.pujing.entity.VipBean;
import cn.com.pujing.http.convert.JsonConvert;
import cn.com.pujing.util.Constants;
import io.reactivex.Observable;

/**
 * author : liguo
 * date : 2021/2/26 11:33
 * description :
 */
public class PujingService {

      public static final String PREFIX = "http://121.37.234.112:80"; //测试
//    public static final String PREFIX = "http://81.69.128.107:80"; //生产
//    public static final String PREFIX = "http://42.49.141.68:2080"; //测试
//    public static final String PREFIX = "http://172.18.9.94"; //曜
//    public static final String PREFIX = "http://172.18.9.235"; // 君
//    public static final String PREFIX = "http://172.18.9.207"; // 文
//    public static final String PREFIX = "http://172.18.9.168:8120"; // 鹏
//    public static final String PREFIX = "http://172.18.19.131"; // 金
//      public static final String PREFIX = "http://172.18.9.214"; // 勇
//    public static final String PREFIX = "http://172.18.19.219:8340"; // 周涛
//    public static final String PREFIX = "http://172.18.19.240"; // 鸿
//    public static final String PREFIX = "http://172.18.7.21";
//    public static final String PREFIX = "http://172.18.19.240:8080"; // 华
    public static String GETPUBLICKEY = PREFIX + "/upms-service/rsa/getPublicKey";

    public static String GETTENCENTKEY = PREFIX + "/basic-service/attachment/cos/getTencentKey";
    public static String GETFILEPATHKEY = PREFIX + "/basic-service/attachment/cos/getFilePathKey";

    public static String TOKEN = PREFIX + "/upms-service/oauth/token";
    public static String BANNER = PREFIX + "/content-service/banner/getAppBanner/";
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

    public static String PHOTOWALL = PREFIX + "/content-service/photoWall/page";
    //添加收藏
    public static String PHOTOWALLCOLLECT = PREFIX + "/content-service/photoWall/collectStatistics/";
    //取消收藏
    public static String CANCELCOLLECT = PREFIX + "/content-service/photoWall/cancelCollect/";
    //点赞
    public static String DOLIKE = PREFIX + "/content-service/photoWall/like/";
    //取消点赞
    public static String UNDOLIKE = PREFIX + "/content-service/photoWall/cancellike/";

    //社区日历
    public static String ACTIVITYDATE = PREFIX + "/life-service/activityCalendar/queryActivityDateList";
    //我的日历
    public static String ACTIVITYDATE_ANOTHER = PREFIX + "/life-service/userNotes/queryActivityDateList";
    public static String QUERYSELECTDAY = PREFIX + "/life-service/activityCalendar/querySelectDay";
    public static String QUERYSELECTDAY_ANOTHER = PREFIX + "/life-service/userNotes/querySelectDay";
    public static String GETALLCATEGORY = PREFIX + "/life-service/activityCategory/getAllCategory";
    public static String ACTIVITYCALENDAR = PREFIX + "/life-service/activityCalendar/currentHotActivity";
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

    public static String MSG = PREFIX + "/messagePush/page";

    //权益包
    public static String RIGHTSANDINTERESTS = PREFIX + "/life-service/serviceRightsPackage/list";
    //权益包详情
    public static String RIGHTSANDINTERESTSDETAIL = PREFIX + "/life-service/serviceRightsPackage/";

    //小标识
    public static String IDENTIFICATION = PREFIX + "/restaurant-service/restaurantCycleRecord/appGetFlag";
    //获取所有的场馆名
    public static String VENUETYPE = PREFIX + "/life-service/serviceVenueManage/findAllManageInfo";
    //获取所有的设备名
    public static String DEVICELIST = PREFIX + "/life-service/serviceVenueManage/selectAllDeviceName";
    //预约设备
    public static String RESERVEDEVICE = PREFIX + "/life-service/serviceVenueOrder/queryReserveTime";
    //通过id查询预约场馆
    public static String SERVICEVENUEORDER = PREFIX + "/life-service/serviceVenueOrder/";
    //服务 1
    public static String SERVICE = PREFIX + "/life-service/serviceBasicService/APPGetResidentPage/";
    //建管 2
    public static String CENTER = PREFIX + "/life-service/serviceBasicService/APPGetHealthPage/";
    //服务详情
    public static String SERVICEDETAIL = PREFIX + "/life-service/serviceBasicService/APPGetResidentById";
    //健管中心详情
    public static String CENTERDETAIL = PREFIX + "/life-service/serviceBasicService/APPGetHealthById";
    //服务时间
    public static String SERVICETIME = PREFIX + "/life-service/serviceBasicService/APPGetTimeList";
    //服务预约
    public static String SERVICERESERVE = PREFIX + "/life-service/serviceOrderManage";
    //服务预约订单查询
    public static String SERVICERESERVEID = PREFIX + "/life-service/serviceOrderManage/";
    //取消服务预约订单
    public static String CANCELSERVICERESERVE = PREFIX + "/life-service/serviceOrderManage/cancel/";
    //活动订单查询
    public static String ACTIVITYORDER = PREFIX + "/life-service/activityOrder/";
    //我的卡包
    public static String MYCARD = PREFIX + "/life-service/serviceCustomerVoucher/getMyVoucherList";
    //失效券
    public static String INVALIDCOUPON = PREFIX + "/life-service/serviceCustomerVoucher/getMyInvalidVoucherList";
    //使用优惠券
    public static String USECOUPON = PREFIX + "/life-service/serviceCustomerVoucher/getVoucherListByServiceId";
    //我的订单
    public static String MYORDER = PREFIX + "/life-service/myOrder/page";
    //是否vip
    public static String VIPEXPIREANDDISCOUNT = PREFIX + "/life-service/vipRightsSetting/getHealthVipExpireAndDiscount";
    //获取我的当期账单(上个月和这个月的)
    public static String MYCURRENTBILLS = PREFIX + "/restaurant-service/bill/myCurrentBills";
    //获取我的历史账单
    public static String HISTORYBILLS = PREFIX + "/restaurant-service/bill/myHistoryBills";
    //根据账单id分页查订单
    public static String QUERYBILLS = PREFIX + "/restaurant-service/bill/orderPage/";
    //分页查询用户纪念日
    public static String COMMEMORATIONDAY = PREFIX + "/life-service/userAnniversary/page";
    //增加纪念日
    public static String ADDCOMMEMORATIONDAY = PREFIX + "/life-service/userAnniversary/saverOrUpdate";
    //删除纪念日
    public static String DELETECOMMEMORATIONDAY = PREFIX + "/life-service/userAnniversary/";
    //获取活动分类
    public static String ACTIVITYCATEGORY = PREFIX + "/life-service/activityCategory/categoryTree";
    //获取意见反馈列表
    public static String GETFEEDBACKLIST = PREFIX + "/content-service/feedback/page";
    //根据id查询意见反馈
    public static String GETFEEDBACKDETAIL = PREFIX + "/content-service/feedback/";
    //获取用户收藏
    public static String GETUSECOLLECT = PREFIX + "/content-service/userCollect/page";
    //系统消息
    public static String MESSAGELIST = PREFIX + "/content-service/appMessage/myUnread/page";
    //读取消息
    public static String READMESSAGE = PREFIX + "/content-service/appMessage/read/";
    //推送
    public static String PUSHMESSAGE = PREFIX + "/content-service/appClient";
    //修改密码
    public static String MODIFYPSD = PREFIX + "/upms-service/user/changePassword";

    //更新apk https://www.pgyer.com/apiv2/app/check?_api_key=d767e80d0afa4ad06a9765bb97334f35&appKey=8a202cf85e94216f5b152739ae337de6&buildVersion=1.0&buildBuildVersion=1.0
    public static String UPDATEURL = "https://www.pgyer.com/apiv2/app/check";

    //场馆取消订单
    public static String EXITVENUEORDER = PREFIX + "/life-service/serviceVenueOrder/modifyOrderStatus";



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
    public static Observable<ResponseData<List<BannerBean>>> getBannerData(int type) {
            return OkGo.<ResponseData<List<BannerBean>>>get(BANNER + type)
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
    public static Observable<ResponseData<Boolean>> register(String userName,String phone,String pwd,String captcha,String name,String sex) {

        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.USERNAME, userName);
        params.put(Constants.PHONE, phone);
        params.put(Constants.NEWPASSWORD, pwd);
        params.put(Constants.SMSVALIDCODE, captcha);
        params.put(Constants.SEX, sex);
        params.put(Constants.ACCOUNT, name);
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
    public static Observable<ResponseData<PagesBean<HistoryActivitiesBean>>> getHistoryActivitiy(int page) {
        return OkGo.<ResponseData<PagesBean<HistoryActivitiesBean>>>get(QUERY_HISTORY_ACTIVITY)
                .params("page",page+"")
                .converter(new JsonConvert<ResponseData<PagesBean<HistoryActivitiesBean>>>() {
                })
                .adapt(new ObservableBody<ResponseData<PagesBean<HistoryActivitiesBean>>>());
    }

    /**
     * 获取我的活动
     */
    public static Observable<ResponseData<PagesBean<HistoryActivitiesBean>>> getMyActivitiy(int page) {
        return OkGo.<ResponseData<PagesBean<HistoryActivitiesBean>>>get(QUERY_MYACTIVITY)
                .params("page",page+"")
                .converter(new JsonConvert<ResponseData<PagesBean<HistoryActivitiesBean>>>() {
                })
                .adapt(new ObservableBody<ResponseData<PagesBean<HistoryActivitiesBean>>>());
    }


    /**
     * 获取热门活动
     */
    public static Observable<ResponseData<HotActivityBean>> getHotActivitiy(int page,String endTime,String startTime,String status,String type) {

        return OkGo.<ResponseData<HotActivityBean>>get(ACTIVITYCALENDAR)
                .params("page", page+"")
                .params("startTime",startTime)
                .params("endTime",endTime)
                .params("status",status)
                .params("type",type)
                .converter(new JsonConvert<ResponseData<HotActivityBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<HotActivityBean>>());
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
    public static Observable<ResponseData<RoutineRecordBean>> getRoutineData(int status){

        return OkGo.<ResponseData<RoutineRecordBean>>get(GETROUTINEDATA)
                .params("flag",status == 3 ? "1" : "")
                .converter(new JsonConvert<ResponseData<RoutineRecordBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<RoutineRecordBean>>());

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

    /**
     * 获取权益包列表
     */
    public static Observable<ResponseData<List<RightsAndInterestsBean>>> getRightsAndInterestsList(){

            return OkGo.<ResponseData<List<RightsAndInterestsBean>>>get(RIGHTSANDINTERESTS)
                    .converter(new JsonConvert<ResponseData<List<RightsAndInterestsBean>>>() {
                    })
                    .adapt(new ObservableBody<ResponseData<List<RightsAndInterestsBean>>>());

    }

    /**
     * 获取权益包详情
     */
    public static Observable<ResponseData<RightsAndInterestsBean<List<RightsVoucherVoBean>>>> getRightsAndInterests(String id){

            return OkGo.<ResponseData<RightsAndInterestsBean<List<RightsVoucherVoBean>>>>get(RIGHTSANDINTERESTSDETAIL+id)
                    .converter(new JsonConvert<ResponseData<RightsAndInterestsBean<List<RightsVoucherVoBean>>>>() {
                    })
                    .adapt(new ObservableBody<ResponseData<RightsAndInterestsBean<List<RightsVoucherVoBean>>>>());

    }

    /**
     * 小标识
     */
    public static Observable<ResponseData<ArrayList<String>>> identification(){

            return OkGo.<ResponseData<ArrayList<String>>>post(IDENTIFICATION)
                    .converter(new JsonConvert<ResponseData<ArrayList<String>>>() {
                    })
                    .adapt(new ObservableBody<ResponseData<ArrayList<String>>>());

    }


    /**
     * 所有的场馆名
     */
    public static Observable<ResponseData<VenueBean>> getVenueType(){

            return OkGo.<ResponseData<VenueBean>>get(VENUETYPE)
                    .converter(new JsonConvert<ResponseData<VenueBean>>() {
                    })
                    .adapt(new ObservableBody<ResponseData<VenueBean>>());

    }

    /**
     * 所有的场馆名
     */
    public static Observable<ResponseData<ArrayList<DeviceBean>>> getDevice(String venueId){

            return OkGo.<ResponseData<ArrayList<DeviceBean>>>get(DEVICELIST)
                    .params("venueId",venueId)
                    .converter(new JsonConvert<ResponseData<ArrayList<DeviceBean>>>() {
                    })
                    .adapt(new ObservableBody<ResponseData<ArrayList<DeviceBean>>>());

    }

    /**
     * 预约设备
     */
    public static Observable<ResponseData<ReserveDeviceBean>> reserveDevice(String venueId, String deviceId, String reserveDate){

            return OkGo.<ResponseData<ReserveDeviceBean>>get(RESERVEDEVICE)
                    .params("venueId",venueId)
                    .params("deviceId",deviceId)
                    .params("reserveDate",reserveDate)
                    .converter(new JsonConvert<ResponseData<ReserveDeviceBean>>() {
                    })
                    .adapt(new ObservableBody<ResponseData<ReserveDeviceBean>>());

    }

    /**
     * 预约场馆
     */
    public static Observable<ResponseData<Object>> reserveSure(String venueId, String deviceId, String reserveDate,String reserveTime){

        HashMap<String, String> params = new HashMap<>();

        params.put("deviceId", deviceId+"");
        params.put("reserveDate", reserveDate+"");
        params.put("reserveTime",reserveTime+"");
        params.put("venueId",venueId+"");

        JSONObject jsonObject = new JSONObject(params);

        return OkGo.<ResponseData<Object>>post(SERVICEVENUEORDER)
                .upJson(jsonObject)
                .converter(new JsonConvert<ResponseData<Object>>() {
                })
                .adapt(new ObservableBody<ResponseData<Object>>());
    }

    /**
     * 预约服务
     */
    public static Observable<ResponseData<Object>> reserveService(String orderingDate, String orderingTime, String basicServiceItemsId,String basicServiceItemsName
            , String serviceBasicId,String category,String customerVoucherId,String customerVoucherName){

        HashMap<String, String> params = new HashMap<>();

        params.put("orderingDate", orderingDate);
        params.put("orderingTime", orderingTime);
        params.put("basicServiceItemsId", basicServiceItemsId);
        params.put("basicServiceItemsName",basicServiceItemsName);
        params.put("serviceBasicId",serviceBasicId);
        params.put("customerVoucherName",customerVoucherName);
        params.put("category",category);
        if(!customerVoucherId.equals("0")) {
            params.put("customerVoucherId", customerVoucherId);
        }

        JSONObject jsonObject = new JSONObject(params);

        return OkGo.<ResponseData<Object>>post(SERVICERESERVE)
                .upJson(jsonObject)
                .converter(new JsonConvert<ResponseData<Object>>() {
                })
                .adapt(new ObservableBody<ResponseData<Object>>());
    }

    /**
     * 通过id查询预约场馆
     */
    public static Observable<ResponseData<VenueDetailBean>> searchVenueDetail(String id){


        return OkGo.<ResponseData<VenueDetailBean>>get(SERVICEVENUEORDER + id)
                .converter(new JsonConvert<ResponseData<VenueDetailBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<VenueDetailBean>>());
    }

    /**
     * 预约服务订单查询
     */
    public static Observable<ResponseData<OrderDetailBean>> queryReserveServiceOrder(String orderNumber){


        return OkGo.<ResponseData<OrderDetailBean>>get(SERVICERESERVEID + orderNumber)
                .converter(new JsonConvert<ResponseData<OrderDetailBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<OrderDetailBean>>());
    }

    /**
     * 取消预约服务订单
     */
    public static Observable<ResponseData<Object>> cancelReserveServiceOrder(String orderNumber){


        return OkGo.<ResponseData<Object>>put(CANCELSERVICERESERVE + orderNumber)
                .converter(new JsonConvert<ResponseData<Object>>() {
                })
                .adapt(new ObservableBody<ResponseData<Object>>());
    }

    /**
     * 活动订单查询
     */
    public static Observable<ResponseData<ActivityBean>> queryActivityOrder(String orderNumber){

        return OkGo.<ResponseData<ActivityBean>>get(ACTIVITYORDER + orderNumber)
                .converter(new JsonConvert<ResponseData<ActivityBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<ActivityBean>>());
    }

    /**
     * 服务
     */
    public static Observable<ResponseData<List<ServiceBean>>> getService(int id){

        if (id == 1){

            return OkGo.<ResponseData<List<ServiceBean>>>get(SERVICE+id)
                    .converter(new JsonConvert<ResponseData<List<ServiceBean>>>() {
                    })
                    .adapt(new ObservableBody<ResponseData<List<ServiceBean>>>());
        }else {

            return OkGo.<ResponseData<List<ServiceBean>>>get(CENTER+id)
                    .converter(new JsonConvert<ResponseData<List<ServiceBean>>>() {
                    })
                    .adapt(new ObservableBody<ResponseData<List<ServiceBean>>>());
        }

    }

    /**
     * 服务个数
     */
    public static Observable<ResponseData<LifeTypeBean>> getLifeType(int id,String date,int category){

        String url = "";

        if (category == 2){
            url = CENTERDETAIL;
        }else {
            url = SERVICEDETAIL;
        }

        return OkGo.<ResponseData<LifeTypeBean>>get(url)
                .params("id",id+"")
                .params("date",date)
                .converter(new JsonConvert<ResponseData<LifeTypeBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<LifeTypeBean>>());
    }

    /**
     * 服务时间
     */
    public static Observable<ResponseData<List<ServicePutawayManageTimeBean>>> getLifeTime(String id,String itemsId,String date){

        return OkGo.<ResponseData<List<ServicePutawayManageTimeBean>>>get(SERVICETIME)
                .params("id",id)
                .params("itemsId",itemsId)
                .params("date",date)
                .converter(new JsonConvert<ResponseData<List<ServicePutawayManageTimeBean>>>() {
                })
                .adapt(new ObservableBody<ResponseData<List<ServicePutawayManageTimeBean>>>());
    }

    /**
     * 我的卡包
     */
    public static Observable<ResponseData<List<MyCardBean>>> getMycard(int type){

        String url = "";
        if(type == 0){
            url = MYCARD;
        }else {
            url = INVALIDCOUPON;
        }

        return OkGo.<ResponseData<List<MyCardBean>>>get(url)
                .converter(new JsonConvert<ResponseData<List<MyCardBean>>>() {
                })
                .adapt(new ObservableBody<ResponseData<List<MyCardBean>>>());
    }

    /**
     * 使用优惠券
     */
    public static Observable<ResponseData<List<MyCardBean>>> useCoupon(String date,String serviceItemId){

        return OkGo.<ResponseData<List<MyCardBean>>>get(USECOUPON)
                .params("date",date)
                .params("serviceItemId",serviceItemId)
                .converter(new JsonConvert<ResponseData<List<MyCardBean>>>() {
                })
                .adapt(new ObservableBody<ResponseData<List<MyCardBean>>>());
    }

    /**
     * 是否vip
     */
    public static Observable<ResponseData<VipBean>> vipExpireandDiscount(){

        return OkGo.<ResponseData<VipBean>>get(VIPEXPIREANDDISCOUNT)
                .converter(new JsonConvert<ResponseData<VipBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<VipBean>>());
    }


    /**
     * 我的账单
     */
    public static Observable<ResponseData<List<BillsBean>>> myCurrentBills(){

        return OkGo.<ResponseData<List<BillsBean>>>get(MYCURRENTBILLS)
                .converter(new JsonConvert<ResponseData<List<BillsBean>>>() {
                })
                .adapt(new ObservableBody<ResponseData<List<BillsBean>>>());
    }

    /**
     * 我的账单
     */
    public static Observable<ResponseData<List<HistoryBillsBean>>> getBillData(){

        return OkGo.<ResponseData<List<HistoryBillsBean>>>get(HISTORYBILLS)
                .converter(new JsonConvert<ResponseData<List<HistoryBillsBean>>>() {
                })
                .adapt(new ObservableBody<ResponseData<List<HistoryBillsBean>>>());
    }

    /**
     * 我的账单详情
     */
    public static Observable<ResponseData<PagesBean<MyBillBean>>> queryBills(String billId,int page){

        return OkGo.<ResponseData<PagesBean<MyBillBean>>>get(QUERYBILLS + billId)
                .params("page",page+"")
                .converter(new JsonConvert<ResponseData<PagesBean<MyBillBean>>>() {
                })
                .adapt(new ObservableBody<ResponseData<PagesBean<MyBillBean>>>());
    }


    /**
     * 照片墙
     */
    public static Observable<ResponseData<PagesBean<PictureWallBean>>> showPicInfoList(int page){

        return OkGo.<ResponseData<PagesBean<PictureWallBean>>>get(PHOTOWALL)
                .params("page",page+"")
                .converter(new JsonConvert<ResponseData<PagesBean<PictureWallBean>>>() {
                })
                .adapt(new ObservableBody<ResponseData<PagesBean<PictureWallBean>>>());
    }


    /**
     * 添加收藏
     */
    public static Observable<ResponseData<Object>> addCollect(int id){

        return OkGo.<ResponseData<Object>>put(PHOTOWALLCOLLECT + id)
                .converter(new JsonConvert<ResponseData<Object>>() {
                })
                .adapt(new ObservableBody<ResponseData<Object>>());
    }

    /**
     * 取消收藏
     */
    public static Observable<ResponseData<Object>> cancelCollect(int id){

        return OkGo.<ResponseData<Object>>put(CANCELCOLLECT + id)
                .converter(new JsonConvert<ResponseData<Object>>() {
                })
                .adapt(new ObservableBody<ResponseData<Object>>());
    }

    /**
     * 添加收藏
     */
    public static Observable<ResponseData<Object>> doLike(int id){

        return OkGo.<ResponseData<Object>>put(DOLIKE + id)
                .converter(new JsonConvert<ResponseData<Object>>() {
                })
                .adapt(new ObservableBody<ResponseData<Object>>());
    }

    /**
     * 取消收藏
     */
    public static Observable<ResponseData<Object>> unDoLike(int id){

        return OkGo.<ResponseData<Object>>put(UNDOLIKE + id)
                .converter(new JsonConvert<ResponseData<Object>>() {
                })
                .adapt(new ObservableBody<ResponseData<Object>>());
    }



    /**
     * 我的纪念日
     */
    public static Observable<ResponseData<CommemorationDayBean>> getCommemorationDay(int page){

        return OkGo.<ResponseData<CommemorationDayBean>>get(COMMEMORATIONDAY)
                .params("page",page+"")
                .converter(new JsonConvert<ResponseData<CommemorationDayBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<CommemorationDayBean>>());
    }

    /**
     * 增加纪念日
     */
    public static Observable<ResponseData<Object>> addCommemorationDay(String commemorationDay,String commemorationName,int type){

        HashMap<String, String> params = new HashMap<>();

        params.put("commemorationDay", commemorationDay);
        params.put("commemorationName", commemorationName);

        if (type != 0){
            params.put("id",type+"");
        }


        JSONObject jsonObject = new JSONObject(params);

        return OkGo.<ResponseData<Object>>post(ADDCOMMEMORATIONDAY)
                .upJson(jsonObject)
                .converter(new JsonConvert<ResponseData<Object>>() {
                })
                .adapt(new ObservableBody<ResponseData<Object>>());
    }


    /**
     * 删除纪念日
     */
    public static Observable<ResponseData<Object>> deleteCommemorationDay(String commemorationDayId){

        return OkGo.<ResponseData<Object>>delete(DELETECOMMEMORATIONDAY + commemorationDayId)
                .converter(new JsonConvert<ResponseData<Object>>() {
                })
                .adapt(new ObservableBody<ResponseData<Object>>());
    }


    /**
     * 我的订单
     */
    public static Observable<ResponseData<OrderItemBean>> getMyOrder(int page,int ordertypeId, String startDate, String endDate){
        String orderCategory = "";
        if (ordertypeId != 0){
            orderCategory = ordertypeId+"";
        }else {
            orderCategory = null;
        }

        if (startDate.trim().length() == 0){
            startDate = null;
        }
        if (endDate.trim().length() == 0){
            endDate = null;
        }

        return OkGo.<ResponseData<OrderItemBean>>get(MYORDER)
                .params("page",page)
                .params("orderCategory",orderCategory)
                .params("createTimeBegin",startDate)
                .params("createTimeEnd",endDate)
                .converter(new JsonConvert<ResponseData<OrderItemBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<OrderItemBean>>());
    }


    /**
     * 获取活动分类
     */
    public static Observable<ResponseData<List<ActivityTypeBean>>> getActivityType(){

        return OkGo.<ResponseData<List<ActivityTypeBean>>>get(ACTIVITYCATEGORY)
                .converter(new JsonConvert<ResponseData<List<ActivityTypeBean>>>() {
                })
                .adapt(new ObservableBody<ResponseData<List<ActivityTypeBean>>>());
    }


    /**
     * 获取意见反馈列表
     */
    public static Observable<ResponseData<PagesBean<MyFeedbackBean>>> getFeedbakList(int page){

        return OkGo.<ResponseData<PagesBean<MyFeedbackBean>>>get(GETFEEDBACKLIST)
                .params("page",page)
                .converter(new JsonConvert<ResponseData<PagesBean<MyFeedbackBean>>>() {
                })
                .adapt(new ObservableBody<ResponseData<PagesBean<MyFeedbackBean>>>());
    }


    /**
     * 根据id查询意见反馈
     */
    public static Observable<ResponseData<MyFeedbackBean>> getFeedbakDetail(int id){

        return OkGo.<ResponseData<MyFeedbackBean>>get(GETFEEDBACKDETAIL+id)
                .converter(new JsonConvert<ResponseData<MyFeedbackBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<MyFeedbackBean>>());
    }

    /**
     * 获取用户收藏
     */
    public static Observable<ResponseData<PagesBean<CollectBean>>> getMyCollect(int page,int ordertypeId, String startDate, String endDate){
        String type = "";
        if (ordertypeId != 0){
            type = ordertypeId+"";
        }else {
            type = null;
        }

        if (startDate.trim().length() == 0){
            startDate = null;
        }
        if (endDate.trim().length() == 0){
            endDate = null;
        }

        return OkGo.<ResponseData<PagesBean<CollectBean>>>get(GETUSECOLLECT)
                .params("page",page)
                .params("type",type)
                .params("startTime",startDate)
                .params("endTime",endDate)
                .converter(new JsonConvert<ResponseData<PagesBean<CollectBean>>>() {
                })
                .adapt(new ObservableBody<ResponseData<PagesBean<CollectBean>>>());
    }



    /**
     * 消息
     */
    public static Observable<ResponseData<PagesBean<MessageBean>>> getMessageList(int page){

        return OkGo.<ResponseData<PagesBean<MessageBean>>>get(MESSAGELIST)
                .params("page",page)
                .converter(new JsonConvert<ResponseData<PagesBean<MessageBean>>>() {
                })
                .adapt(new ObservableBody<ResponseData<PagesBean<MessageBean>>>());
    }

    /**
     * 读取消息
     */
    public static Observable<ResponseData<Object>> getReadMessage(String ids){

        HashMap<String, String> params = new HashMap<>();
        params.put("ids", ids);
        JSONObject jsonObject = new JSONObject(params);


        return OkGo.<ResponseData<Object>>get(READMESSAGE + ids)
                .converter(new JsonConvert<ResponseData<Object>>() {
                })
                .adapt(new ObservableBody<ResponseData<Object>>());
    }

    /**
     * 推送消息
     */
    public static Observable<ResponseData<Object>> sendPushDevice(String pushId){

        HashMap<String, String> params = new HashMap<>();
        params.put("pushId", pushId);
        params.put("clientType", "1");
        params.put("clientBrand", Build.MODEL);
        JSONObject jsonObject = new JSONObject(params);


        return OkGo.<ResponseData<Object>>post(PUSHMESSAGE)
                .upJson(jsonObject)
                .converter(new JsonConvert<ResponseData<Object>>() {
                })
                .adapt(new ObservableBody<ResponseData<Object>>());
    }

    /**
     * 修改密码
     */
    public static Observable<ResponseData<Object>> modifyPsd(String oldPsd,String newPsd){

        HashMap<String, String> params = new HashMap<>();
        params.put("originPassword", oldPsd);
        params.put("newPassword", newPsd);
        JSONObject jsonObject = new JSONObject(params);


        return OkGo.<ResponseData<Object>>post(MODIFYPSD)
                .upJson(jsonObject)
                .converter(new JsonConvert<ResponseData<Object>>() {
                })
                .adapt(new ObservableBody<ResponseData<Object>>());
    }

    /**
     * 更新apk
     */
    public static Observable<ResponseData<UpdateBean>> updateApk(){

        return OkGo.<ResponseData<UpdateBean>>get(UPDATEURL)
                .params("_api_key", "d767e80d0afa4ad06a9765bb97334f35")
                .params("appKey", "8a202cf85e94216f5b152739ae337de6")
                .converter(new JsonConvert<ResponseData<UpdateBean>>() {
                })
                .adapt(new ObservableBody<ResponseData<UpdateBean>>());
    }

    /**
     * 取消场馆预约
     * status 3 已取消
     */
    public static Observable<ResponseData<Object>> exitVenueOrder(String id,String status){

        return OkGo.<ResponseData<Object>>get(EXITVENUEORDER)
                .params("id", id)
                .params("status", status)
                .converter(new JsonConvert<ResponseData<Object>>() {
                })
                .adapt(new ObservableBody<ResponseData<Object>>());
    }

}
