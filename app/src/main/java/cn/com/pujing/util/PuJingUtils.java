package cn.com.pujing.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import cn.com.pujing.entity.RestDayBean;
import cn.com.pujing.widget.GlideRoundTransform;

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

        for (int i = 0;i< dateList.size();i++){
            RestDayBean restDayBean = new RestDayBean();
            restDayBean.dateDay = dateList.get(i);
            restDayBean.weekDay = weekList[i];
            restDayBean.monthDay = monthDayList.get(i);
            restDayBeans.add(restDayBean);
        }

        return restDayBeans;
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

}
