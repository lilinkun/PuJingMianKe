package cn.com.pujing.adapter;

import android.widget.BaseAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.MyBillBean;
import cn.com.pujing.util.PuJingUtils;

/**
 * author : liguo
 * date : 2021/4/12 10:36
 * description :
 */
public class BillsAdapter extends BaseQuickAdapter<MyBillBean, BaseViewHolder> implements LoadMoreModule {

    public BillsAdapter(int layoutResId, @Nullable List<MyBillBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MyBillBean myBill) {

        baseViewHolder.setText(R.id.tv_create_time,myBill.createTime);
        baseViewHolder.setText(R.id.tv_bills_name,myBill.orderName);
        baseViewHolder.setText(R.id.tv_bills_content,myBill.orderCategory_label);
        baseViewHolder.setText(R.id.tv_bills_price, PuJingUtils.removeAmtLastZero(myBill.realMoney)+"");

    }
}
