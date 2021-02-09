package cn.com.pujing.datastructure;

import java.util.ArrayList;
import java.util.List;

import cn.com.pujing.R;

public class MsgItem {
    public int imgRes;
    public String imgUrl;
    public String type;
    public String content;
    public String time;

    public static List<MsgItem> getTestData() {
        List<MsgItem> list = new ArrayList<>();

        MsgItem msgItem = new MsgItem();
        msgItem.imgRes = R.mipmap.ic_msg_another;
        msgItem.type = "系统通知";
        msgItem.content = "有新的服务上线，预约服务请查看详情";
        msgItem.time = "2021年1月11日";

        MsgItem msgItem1 = new MsgItem();
        msgItem1.imgRes = R.mipmap.ic_msg_another;
        msgItem1.type = "系统通知";
        msgItem1.content = "有最新活动上架，报名查看详情";
        msgItem1.time = "2021年1月11日";

        list.add(msgItem);
        list.add(msgItem1);
        return list;
    }
}
