package cn.com.pujing.entity;

import java.io.Serializable;

public class SendSms implements Serializable {
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
