package cn.com.pujing.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author : liguo
 * date : 2021/4/15 15:40
 * description :
 */
public class PictureWallBean implements Serializable {
    public String title;
    public String photo;
    public String content;
    public String createTime;
    public int id;
    public int isCollent;
    public int favoriteNumber;
    public int likeNumber;
    public int islike;
}
