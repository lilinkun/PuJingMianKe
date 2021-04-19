package cn.com.pujing.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import cn.com.pujing.activity.LifeTypeActivity;
import cn.com.pujing.activity.ShowPhotoActivity;
import cn.com.pujing.activity.VenueReserveActivity;
import cn.com.pujing.activity.WebviewActivity;
import cn.com.pujing.entity.BannerBean;
import cn.com.pujing.entity.RestDayBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.widget.GlideRoundTransform;

/**
 * 璞境工具类
 */
public class PuJingUtils {

    /**
     * 校验开始时间是否大于结束时间
     *
     * @param startTime 开始时间字符串
     * @param endTime 结束时间字符串
     * @param format 格式
     * @return
     */
    public static boolean checkTimeRange(String startTime, String endTime, String format) {

        try {

            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date startDate = null;
            Date endDate = null;

            startDate = sdf.parse(startTime);
            endDate = sdf.parse(endTime);

            if (endDate.after(startDate)) { //startDate是否在endTime之后，为true 表示  startTime>endTime
                return true;
            } else {
                return false;
            }
            /*
            if (startDate.getTimee()>endDate.getEndTime())ho { //判断时间戳
                return true;
            } else {
               return false;
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    public static void saveImage(Context context,Bitmap image) {
                 String saveImagePath = null;
                 Random random = new Random();
                 String imageFileName = "JPEG_" + "down" + random.nextInt(10) + ".jpg";
                 File storageDir = new File(Environment.getExternalStoragePublicDirectory
                                 (Environment.DIRECTORY_PICTURES) + "test");

                 boolean success = true;
                 if(!storageDir.exists()){
                         success = storageDir.mkdirs();
                     }
                 if(success){
                         File imageFile = new File(storageDir, imageFileName);
                         saveImagePath = imageFile.getAbsolutePath();
                         try {
                                 OutputStream fout = new FileOutputStream(imageFile);
                                 image.compress(Bitmap.CompressFormat.JPEG, 100, fout);
                                 fout.close();
                             } catch (Exception e) {
                                 e.printStackTrace();
                             }

                         // Add the image to the system gallery
                         galleryAddPic(saveImagePath,context);
                     }
         //        return saveImagePath;
             }

             public static void galleryAddPic(String imagePath,Context context) {
                 Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                 File f = new File(imagePath);
                 Uri contentUri = Uri.fromFile(f);
                 mediaScanIntent.setData(contentUri);
                 context.sendBroadcast(mediaScanIntent);
             }


    private static final int TIME = 500;
    private static long lastClickTime = 0;
    /**
     * 处理快速双击，多击事件，在TIME时间内只执行一次事件
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        long currentTime = System.currentTimeMillis();
        long timeInterval = currentTime - lastClickTime;
        if (0 < timeInterval && timeInterval < TIME) {
            return true;
        }
        lastClickTime = currentTime;
        return false;
    }

    /**
     * 获取当前日期的下周一到下周日的所有日期集合
     * @return
     */
    public static List<RestDayBean> getNextWeekDateList(){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal1.setTime(new Date());

        cal2.setTime(new Date());
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal1.get(Calendar.DAY_OF_WEEK);

        if(dayWeek == 1){
            cal1.add(Calendar.DAY_OF_MONTH, 1);
            cal2.add(Calendar.DAY_OF_MONTH, 7);
        } else {
            cal1.add(Calendar.DAY_OF_MONTH, 1-dayWeek+8);
            cal2.add(Calendar.DAY_OF_MONTH, 1-dayWeek+14);
        }
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(cal1.getTime());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM月dd日");

        List<String> dateList = new ArrayList();
        List<String> monthDayList = new ArrayList();

        String[] weekList = {"周一","周二","周三","周四","周五","周六","周天"};

        List<RestDayBean> restDayBeans = new ArrayList<>();

        //别忘了，把起始日期加上
        dateList.add(simpleDateFormat.format(cal1.getTime()));
        monthDayList.add(simpleDateFormat1.format(cal1.getTime()));
        // 此日期是否在指定日期之后
        while (cal2.getTime().after(cStart.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cStart.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(simpleDateFormat.format(cStart.getTime()));
            monthDayList.add(simpleDateFormat1.format(cStart.getTime()));
        }

        for (int i = 0;i< weekList.length;i++){
            RestDayBean restDayBean = new RestDayBean();
            restDayBean.dateDay = dateList.get(i);
            restDayBean.weekDay = weekList[i];
            restDayBean.monthDay = monthDayList.get(i);
            restDayBeans.add(restDayBean);
        }

        return restDayBeans;
    }

    /**
     * 获取下周集合
     * @return
     */
    public static List<String> nextWeek(){
        List<RestDayBean> restDayBeans = getNextWeekDateList();
        List<String> strings = new ArrayList<>();
        for(RestDayBean restDayBean : restDayBeans){
            strings.add(restDayBean.dateDay);
        }

        return strings;
    }

    //数字转字母 1-26 ： A-Z
    public static String numberToLetter(int num) {
        if (num <= 0) {
            return null;
        }
        String letter = "";
        num--;
        do {
            if (letter.length() > 0) {
                num--;
            }
            letter = ((char) (num % 26 + (int) 'A')) + letter;
            num = (int) ((num - num % 26) / 26);
        } while (num > 0);

        return letter;
    }



    //glide设置圆角
    public static RequestOptions setGlideCircle(int degrees){

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .transform(new GlideRoundTransform(degrees)); //圆角
        return options;
    }

    /**
     * 复制内容到剪贴板
     *
     * @param content
     * @param context
     */
    public static void copyContentToClipboard(String content, Context context) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", content);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }


    /**
     * 金额去后面0
     *
     * @param money
     * @return
     */
    public static BigDecimal removeAmtLastZero(double money) {
        String strMoney = money + "";
        if (strMoney.indexOf('.') != -1) {
            String[] arr = strMoney.split("\\.");
            String strDecimals = arr[1];
            List<String> list = new ArrayList<String>();
            boolean isCanAdd = false;
            for (int i = strDecimals.length() - 1; i > -1; i--) {
                String ss = String.valueOf(strDecimals.charAt(i));
                if (!ss.equals("0")) {
                    isCanAdd = true;//从最后的字符开始算起，遇到第一个不是0的字符开始都是需要保留的字符
                }
                if (!ss.equals("0") || isCanAdd) {
                    list.add(ss);
                }
            }
            StringBuffer strZero = new StringBuffer();
            for (int i = list.size() - 1; i > -1; i--) {
                strZero.append(list.get(i));
            }
            strMoney = String.format("%s.%s", arr[0], strZero.toString());
        }

        return new BigDecimal(strMoney);
    }


    /**
     * 获取上个月日期 首日-末日
     */
    public static String getLastMonthDay() {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd");
        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, -1);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String firstDay = format.format(cal_1.getTime());

        //获取前月的最后一天
        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天
        String lastDay = format.format(cale.getTime());
        System.out.println("-----2------lastDay:"+lastDay);


        return "当期账单\n"+"("+ firstDay + "-" + lastDay+")";
    }

    /**
     * 获取当月日期 首日-末日
     */
    public static String getMonthDay() {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd");
        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, 0);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String firstDay = format.format(cal_1.getTime());

        //获取前月的最后一天
        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天
        String lastDay = format.format(cale.getTime());
        System.out.println("-----2------lastDay:"+lastDay);


        return "当期账单\n"+"("+ firstDay + "-至今)";
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "无法获取到版本号";
        }
    }

    public static void bannerClick(Context context, BannerBean bannerBean){

        switch (bannerBean.getType()){
            case 1:
            case 2:
            case 4:
                Intent intent = new Intent(context, WebviewActivity.class);
                if (bannerBean.getLinkAddress() != null && bannerBean.getLinkAddress().trim().length() > 0) {
                    intent.putExtra(Constants.URL, PujingService.PREFIX + bannerBean.getLinkAddress());
//                    intent.putExtra(Constants.URL, Urls.EVENTDETAILS + bannerBean.getLinkAddress());
                    context.startActivity(intent);
                }
                break;

            case 3:

                String photoId = bannerBean.getLinkAddress();

                if (photoId.contains("/")){
                    photoId = photoId.substring(photoId.lastIndexOf("/")+1,photoId.length());
                }


                Intent intent3 = new Intent(context, ShowPhotoActivity.class);
                intent3.putExtra("id",Integer.valueOf(photoId));
                context.startActivity(intent3);

                break;

            case 5: //跳健管中心详情


                Intent intent1 = new Intent();
                intent1.setClass(context, LifeTypeActivity.class);
//                        intent1.putExtra("basicservicevolistbean",((List<BasicServiceVoListBean>)data).get(position));
                intent1.putExtra("category",2);
                intent1.putExtra("id",Integer.valueOf(bannerBean.getLinkAddress()));
                context.startActivity(intent1);

                break;

            case 6://跳服务

                Intent intent6 = new Intent();
                intent6.setClass(context, LifeTypeActivity.class);
                intent6.putExtra("category",1);
                intent6.putExtra("id",Integer.valueOf(bannerBean.getLinkAddress()));
                context.startActivity(intent6);

                break;

            case 7://跳场馆详情

                Intent intent7 = new Intent();
                intent7.setClass(context, VenueReserveActivity.class);
                intent7.putExtra("id",Integer.valueOf(bannerBean.getLinkAddress()));
                context.startActivity(intent7);

                break;

            default:
                break;

        }
    }


}
