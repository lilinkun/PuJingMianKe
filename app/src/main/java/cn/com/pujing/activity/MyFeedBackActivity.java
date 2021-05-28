package cn.com.pujing.activity;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.MyFeedBackAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.MyFeedbackBean;
import cn.com.pujing.entity.PagesBean;
import cn.com.pujing.presenter.MyFeedBackPresenter;
import cn.com.pujing.view.MyFeedBackView;

/**
 * author : liguo
 * date : 2021/4/15 13:51
 * description :我的反馈
 */
public class MyFeedBackActivity extends BaseActivity<MyFeedBackView, MyFeedBackPresenter> implements MyFeedBackView{

    private int page = 1;

    @BindView(R.id.rv_my_feedback)
    RecyclerView rvMyFeedback;

    private MyFeedBackAdapter myFeedBackAdapter;
    private List<MyFeedbackBean> myFeedbackBeans;

    @Override
    public int getLayoutId() {
        return R.layout.activity_myfeedback;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        myFeedBackAdapter = new MyFeedBackAdapter(R.layout.adapter_myfeedback,null);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rvMyFeedback.setLayoutManager(linearLayoutManager);
        rvMyFeedback.setAdapter(myFeedBackAdapter);

        rvMyFeedback.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        mPresenter.getFeedbakList(page);

        myFeedBackAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent();
                intent.setClass(MyFeedBackActivity.this,MyFeedbackDetailActivity.class);
                intent.putExtra("id",myFeedbackBeans.get(position).id);
                startActivity(intent);
            }
        });

    }

    @Override
    protected MyFeedBackPresenter createPresenter() {
        return new MyFeedBackPresenter();
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

    @Override
    public void getFeedbakListSuccess(PagesBean<MyFeedbackBean> pagesBean) {
        this.myFeedbackBeans = pagesBean.records;
        myFeedBackAdapter.setNewInstance(pagesBean.records);
    }

    @Override
    public void getDataFail(String msg) {

    }
}
