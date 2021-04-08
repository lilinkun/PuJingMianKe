package cn.com.pujing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.RightsVoucherVoBean;
import cn.com.pujing.util.PuJingUtils;

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

        if (rightsVoucherVoBean.deductMethod == 1){
            baseViewHolder.setText(R.id.tv_content,"抵扣单次服务费用");
        }else {
            if (rightsVoucherVoBean.fullReductionFlag == 1){
                baseViewHolder.setText(R.id.tv_content,"满" + PuJingUtils.removeAmtLastZero(rightsVoucherVoBean.fullReductionThreshold) + "减" + PuJingUtils.removeAmtLastZero(rightsVoucherVoBean.deductAmount));
            }else {
                baseViewHolder.setText(R.id.tv_content,"抵扣" + PuJingUtils.removeAmtLastZero(rightsVoucherVoBean.deductAmount));
            }
        }

        baseViewHolder.setText(R.id.tv_coupon_num,"x " + rightsVoucherVoBean.quantity);
    }
}
