package cn.com.pujing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.MyFeedbackBean;

/**
 * author : liguo
 * date : 2021/4/15 14:10
 * description :
 */
public class MyFeedBackAdapter extends BaseQuickAdapter<MyFeedbackBean, BaseViewHolder> {

    public MyFeedBackAdapter(int layoutResId, @Nullable List<MyFeedbackBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MyFeedbackBean myFeedbackBean) {
        baseViewHolder.setText(R.id.tv_feedback_time,myFeedbackBean.createTime);
        baseViewHolder.setText(R.id.tv_feedback_content,myFeedbackBean.content);
        baseViewHolder.setText(R.id.tv_acceptanceStatus_label,myFeedbackBean.acceptanceStatus_label);
        baseViewHolder.setText(R.id.tv_type_label,myFeedbackBean.type_label);

        switch (myFeedbackBean.type){
            case 1:
            case 2:
                baseViewHolder.setTextColorRes(R.id.tv_type_label,R.color.blue);
                baseViewHolder.setBackgroundResource(R.id.tv_type_label,R.drawable.shape_feedback_opinion);
                break;
            case 3:
                baseViewHolder.setTextColorRes(R.id.tv_type_label,R.color.red_1d);
                baseViewHolder.setBackgroundResource(R.id.tv_type_label,R.drawable.shape_feedback_complaint);
                break;
            case 4:
                baseViewHolder.setTextColorRes(R.id.tv_type_label,R.color.green);
                baseViewHolder.setBackgroundResource(R.id.tv_type_label,R.drawable.shape_feedback_praise);
                break;

            default:
                break;

        }

    }
}
