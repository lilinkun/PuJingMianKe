package cn.com.pujing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.RightsAndInterestsBean;
import cn.com.pujing.entity.RightsVoucherVoBean;

/**
 * author : liguo
 * date : 2021/3/30 17:46
 * description :
 */
public class RightsAndInterestsDetailAdapter extends BaseQuickAdapter<RightsVoucherVoBean, BaseViewHolder> {

    public RightsAndInterestsDetailAdapter(int layoutResId, @Nullable List<RightsVoucherVoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RightsVoucherVoBean rightsVoucherVoBean) {
        baseViewHolder.setText(R.id.tv_coupon_card,rightsVoucherVoBean.voucherName);
        baseViewHolder.setText(R.id.tv_coupon_num,"x " + rightsVoucherVoBean.quantity);
    }
}
