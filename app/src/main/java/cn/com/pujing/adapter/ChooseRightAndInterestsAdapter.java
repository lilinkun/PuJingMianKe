package cn.com.pujing.adapter;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.ChooseRightsAndInterestsBean;

/**
 * author : liguo
 * date : 2021/3/29 18:21
 * description :
 */
public class ChooseRightAndInterestsAdapter extends BaseQuickAdapter<ChooseRightsAndInterestsBean, BaseViewHolder> {

    public ChooseRightAndInterestsAdapter(int layoutResId, @Nullable List<ChooseRightsAndInterestsBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ChooseRightsAndInterestsBean chooseRightsAndInterestsBean) {
        baseViewHolder.setText(R.id.tv_coupon_name,chooseRightsAndInterestsBean.couponName);
        baseViewHolder.setText(R.id.tv_coupon_tip,chooseRightsAndInterestsBean.couponContent);
        baseViewHolder.setText(R.id.tv_coupon_date,"有效期至：" + chooseRightsAndInterestsBean.couponDate);

        RadioButton cbCoupon = baseViewHolder.getView(R.id.cb_coupon);

        cbCoupon.setChecked(chooseRightsAndInterestsBean.isChoose);

    }

}
