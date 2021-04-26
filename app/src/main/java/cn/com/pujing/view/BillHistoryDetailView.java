package cn.com.pujing.view;

import cn.com.pujing.entity.MyBillBean;
import cn.com.pujing.entity.PagesBean;

/**
 * author : liguo
 * date : 2021/4/12 18:41
 * description :
 */
public interface BillHistoryDetailView {

    public void getMyBillsSuccess(PagesBean<MyBillBean> billsItemBean);

    public void getDataFail(String msg);

}
