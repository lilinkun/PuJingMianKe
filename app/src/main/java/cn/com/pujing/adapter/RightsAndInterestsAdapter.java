package cn.com.pujing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.RightsAndInterestsBean;
import cn.com.pujing.util.PuJingUtils;

/**
 * author : liguo
 * date : 2021/3/29 16:58
 * description :
 */
public class RightsAndInterestsAdapter extends BaseQuickAdapter<RightsAndInterestsBean, BaseViewHolder> {

    public RightsAndInterestsAdapter(int layoutResId, @Nullable List<RightsAndInterestsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RightsAndInterestsBean rightsAndInterestsBean) {

        baseViewHolder.setText(R.id.tv_coupon_name,rightsAndInterestsBean.name);
        baseViewHolder.setText(R.id.tv_coupon_content,"心动体验价，限购" + rightsAndInterestsBean.quotaNumber + "次");
        baseViewHolder.setText(R.id.tv_price,"￥" + PuJingUtils.removeAmtLastZero(rightsAndInterestsBean.price));

    }
}
