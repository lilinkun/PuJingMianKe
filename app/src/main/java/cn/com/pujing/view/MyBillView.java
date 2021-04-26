package cn.com.pujing.view;

import java.util.List;

import cn.com.pujing.entity.BillsBean;
import cn.com.pujing.entity.BillsItemBean;
import cn.com.pujing.entity.MyBillBean;
import cn.com.pujing.entity.PagesBean;

/**
 * author : liguo
 * date : 2021/4/6 20:36
 * description :
 */
public interface MyBillView {
    public void getMyBillsSuccess(PagesBean<MyBillBean> billsItemBean);
    public void getMyCurrentBillsSuccess(List<BillsBean> billsBeans);
    public void getDataFail(String msg);
}
