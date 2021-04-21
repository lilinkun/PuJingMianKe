package cn.com.pujing.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author : liguo
 * date : 2021/4/12 17:19
 * description :
 */
public class HistoryBillsBean implements Serializable {

    public int year;

    public List<BillList> billList;


    public class BillList implements Serializable {
        public int id;
        public String billMonth;
        public double totalAmount;
        public String payStatus_label;
        public String roomNumber;
        public double arrearage;

    }



}
