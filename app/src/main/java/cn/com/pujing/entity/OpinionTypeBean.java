package cn.com.pujing.entity;

import java.util.List;

/**
 *  author : liguo
 *  date : 2021/2/24 19:13
 *  description : 反馈实体
 */
public class OpinionTypeBean extends Base{
    public List<Data> data;

    public class Data{
        public int id;
        public String label;
        public String value;
    }

}
