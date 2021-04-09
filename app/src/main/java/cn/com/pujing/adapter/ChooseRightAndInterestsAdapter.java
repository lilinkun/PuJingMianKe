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
import cn.com.pujing.entity.MyCardBean;
import cn.com.pujing.util.PuJingUtils;

/**
 * author : liguo
 * date : 2021/3/29 18:21
 * description :
 */
public class ChooseRightAndInterestsAdapter extends BaseQuickAdapter<MyCardBean, BaseViewHolder> {

    private int pos = -1;


    public ChooseRightAndInterestsAdapter(int layoutResId, @Nullable List<MyCardBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MyCardBean myCardBean) {
        baseViewHolder.setText(R.id.tv_coupon_name,myCardBean.name);
        baseViewHolder.setText(R.id.tv_coupon_date,"有效期至：" + myCardBean.expiresTime);

        if (myCardBean.deductMethod == 1){
            baseViewHolder.setText(R.id.tv_coupon_tip,"抵扣单次服务费用");
        }else {
            if (myCardBean.fullReductionFlag == 1){
                baseViewHolder.setText(R.id.tv_coupon_tip,"满" + PuJingUtils.removeAmtLastZero(myCardBean.fullReductionThreshold) + "减" + PuJingUtils.removeAmtLastZero(myCardBean.deductAmount));
            }else {
                baseViewHolder.setText(R.id.tv_coupon_tip,"抵扣" + PuJingUtils.removeAmtLastZero(myCardBean.deductAmount));
            }
        }

        RadioButton cbCoupon = baseViewHolder.getView(R.id.cb_coupon);

        if (baseViewHolder.getAdapterPosition() == pos){
            cbCoupon.setChecked(true);
        }else {
            cbCoupon.setChecked(false);
        }


    }

    public void setPos(int pos){
        this.pos = pos;
        notifyDataSetChanged();
    }

}
