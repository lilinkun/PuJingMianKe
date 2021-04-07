package cn.com.pujing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.MyCardBean;

/**
 * author : liguo
 * date : 2021/4/7 18:48
 * description :
 */
public class MyCardAdapter extends BaseQuickAdapter<MyCardBean, BaseViewHolder> {

    public MyCardAdapter(int layoutResId, @Nullable List<MyCardBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MyCardBean myCardBean) {
        baseViewHolder.setText(R.id.tv_coupon_name,myCardBean.name);
        baseViewHolder.setText(R.id.tv_expires_time,"有效期至：" + myCardBean.expiresTime);
        baseViewHolder.setText(R.id.tv_usedflag,myCardBean.usedFlag_label+"");
    }
}
