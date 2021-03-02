package cn.com.pujing.entity;

import java.io.Serializable;

/**
 * author : liguo
 * date : 2021/3/1 11:27
 * description :
 */
public class MyInfoBean implements Serializable {
    private String avatar;
    private String username;
    private String phone;
    private String auditStatus_label;
    private String nikeName;
    private String signature;
    private String roomNumber;
    private String birthday;
    private static final long serialVersionUID = 4360389409792822304L;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAuditStatus_label() {
        return auditStatus_label;
    }

    public void setAuditStatus_label(String auditStatus_label) {
        this.auditStatus_label = auditStatus_label;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
