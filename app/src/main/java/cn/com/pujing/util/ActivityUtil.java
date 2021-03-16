package cn.com.pujing.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityUtil {

    public static List<Activity> activityList = new ArrayList<>();
    public static List<Activity> activityHomeList = new ArrayList<>();

    // 添加Activity
    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }


    // 移除所有Activity
    public static void finishAll() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    //添加除首页的所有Activity
    public static void addHomeActivity(Activity activity){
        activityHomeList.add(activity);
    }

    public static void finishHomeAll(){
        for (Activity activity : activityHomeList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

}
