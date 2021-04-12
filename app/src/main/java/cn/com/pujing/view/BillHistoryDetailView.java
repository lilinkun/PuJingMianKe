package cn.com.pujing.view;

import cn.com.pujing.entity.MyBillBean;

/**
 * author : liguo
 * date : 2021/4/12 18:41
 * description :
 */
public interface BillHistoryDetailView {

    public void getMyBillsSuccess(MyBillBean billsItemBean);

    public void getDataFail(String msg);

}
