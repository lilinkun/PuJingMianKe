package cn.com.pujing.entity;

import java.io.Serializable;

/**
 * author : liguo
 * date : 2021/2/26 17:48
 * description :
 */
public class PhotoBean implements Serializable {
    private String photo;
    private String title;
    private String content;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
