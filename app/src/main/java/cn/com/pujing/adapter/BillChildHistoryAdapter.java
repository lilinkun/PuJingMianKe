package cn.com.pujing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.haibin.calendarview.BaseView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.HistoryBillsBean;
import cn.com.pujing.util.PuJingUtils;

/**
 * author : liguo
 * date : 2021/4/12 17:29
 * description :
 */
public class BillChildHistoryAdapter extends BaseQuickAdapter<HistoryBillsBean.BillList, BaseViewHolder> {

    public BillChildHistoryAdapter(int layoutResId, @Nullable List<HistoryBillsBean.BillList> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HistoryBillsBean.BillList billList) {
        baseViewHolder.setText(R.id.tv_price, "￥" + PuJingUtils.removeAmtLastZero(billList.totalAmount));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        try {
            simpleDateFormat.parse(billList.billMonth);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        baseViewHolder.setText(R.id.tv_list_item_name, billList.billMonth + "月账单");


        if (billList.arrearage == 0){
            baseViewHolder.setText(R.id.tv_status, "已还清");
        }else {
            baseViewHolder.setText(R.id.tv_status, "待还款");
        }
    }
}
