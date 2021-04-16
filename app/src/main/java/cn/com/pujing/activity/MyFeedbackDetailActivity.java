package cn.com.pujing.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.MyFeedbackBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.presenter.MyFeedbackDetailPresenter;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.MyFeedbackDetailView;

/**
 * author : liguo
 * date : 2021/4/15 18:42
 * description :
 */
public class MyFeedbackDetailActivity extends BaseActivity<MyFeedbackDetailView, MyFeedbackDetailPresenter> implements MyFeedbackDetailView{

    @BindView(R.id.tv_status_name)
    TextView tvStatusName;
    @BindView(R.id.tv_feedback_time)
    TextView tvFeedbackTime;
    @BindView(R.id.tv_feedback_status)
    TextView tvFeedbackStatus;
    @BindView(R.id.tv_details)
    TextView tvDetails;
    @BindView(R.id.tv_reply_time)
    TextView tvReplyTime;
    @BindView(R.id.tv_reply_content)
    TextView tvReplyContent;
    @BindView(R.id.tv_reply_name)
    TextView tvReplyName;
    @BindView(R.id.ll_myfeedback_reply)
    LinearLayout llMyfeedbackReply;
    @BindView(R.id.iv_feedback)
    ImageView ivFeedback;

    @Override
    public int getLayoutId() {
        return R.layout.activity_myfeedback_detail;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        int id = getIntent().getIntExtra("id",0);

        mPresenter.getFeedbakDetail(id);
    }

    @Override
    protected MyFeedbackDetailPresenter createPresenter() {
        return new MyFeedbackDetailPresenter();
    }

    @Override
    public void getMyFeedbackDetailSuccess(MyFeedbackBean myFeedbackBean) {
        tvStatusName.setText(myFeedbackBean.type_label);
        tvFeedbackTime.setText(myFeedbackBean.createTime);
        tvFeedbackStatus.setText(myFeedbackBean.acceptanceStatus_label);
        tvDetails.setText(myFeedbackBean.content);

        if (myFeedbackBean.acceptanceStatus == 1) {
            tvReplyTime.setText(myFeedbackBean.updateTime);
            tvReplyContent.setText(myFeedbackBean.replyContent);
            tvReplyName.setText(myFeedbackBean.accountName);
            llMyfeedbackReply.setVisibility(View.VISIBLE);
        }

        Glide.with(this).load(PujingService.PREFIX + PujingService.IMG + myFeedbackBean.photo)
                .placeholder(R.drawable.loading).error(R.drawable.ic_no_pic).into(ivFeedback);
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(this,msg);
    }

    @OnClick({R.id.iv_my_feedback_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_my_feedback_back:
                finish();
                break;

            default:
                break;
        }
    }

}
