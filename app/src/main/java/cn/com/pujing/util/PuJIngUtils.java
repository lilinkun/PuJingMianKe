package cn.com.pujing.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class PuJIngUtils {

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
            if (startDate.getTimee()>endDate.getEndTime()) { //判断时间戳
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

}
