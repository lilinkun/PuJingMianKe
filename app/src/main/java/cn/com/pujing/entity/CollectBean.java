package cn.com.pujing.entity;

/**
 * author : liguo
 * date : 2021/4/16 18:09
 * description :
 */
public class CollectBean {

    /**
     *收藏对象id
     */
    public int collectId;
    /**
     *
     */
    public int id;
    /**
     * 活动或者照片墙的图片
     */
    public String photo;
    /**
     *活动的名称，照片墙的标题
     */
    public String title;
    /**
     *0-活动 1-照片墙
     */
    public int type;
    /**
     *人员id
     */
    public int userId;
}
