package cn.com.pujing.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author : liguo
 * date : 2021/4/13 9:47
 * description :
 */
public class CommemorationDayBean implements Serializable {
    public int current;
    public int pages;
    public int size;
    public int total;
    public List<CommemorationDay> records;

    public class CommemorationDay implements Serializable {
        /**
         *纪念日期
         */
        public String commemorationDay;
        /**
         *纪念名称
         */
        public String commemorationName;


        public int id;
        /**
         *用户ID
         */
        public String userId;
    }
}
