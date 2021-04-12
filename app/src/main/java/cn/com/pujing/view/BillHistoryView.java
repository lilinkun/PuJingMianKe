package cn.com.pujing.view;

import java.util.List;

import cn.com.pujing.entity.HistoryBillsBean;

/**
 * author : liguo
 * date : 2021/4/12 16:08
 * description :
 */
public interface BillHistoryView {

    public void getDataSuccess(List<HistoryBillsBean> historyBillsBeans);
    public void getDataFail(String msg);

}
