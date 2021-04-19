package cn.com.pujing.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

import butterknife.BindView;
import cn.com.pujing.R;
import cn.com.pujing.activity.LifeTypeActivity;
import cn.com.pujing.activity.MyOrderActivity;
import cn.com.pujing.activity.OrderDetailActivity;
import cn.com.pujing.activity.RanquetsOrderActivity;
import cn.com.pujing.activity.WebviewActivity;
import cn.com.pujing.adapter.MessageAdapter;
import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.entity.BannerBean;
import cn.com.pujing.entity.BasicServiceVoListBean;
import cn.com.pujing.entity.MessageBean;
import cn.com.pujing.entity.PagesBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.presenter.MessagePresenter;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.MessageView;

/**
 * author : liguo
 * date : 2021/4/18 12:36
 * description :
 */
public class MessageFragment extends BaseFragment<MessageView, MessagePresenter> implements MessageView {

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rv_my_message)
    RecyclerView rvMyMessage;


    private int page = 1;
    private MessageAdapter messageAdapter;

    private List<MessageBean> messageBeans;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    public void initEventAndData() {
        mPresenter.getMessageList(page);

        rvMyMessage.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMyMessage.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        messageAdapter = new MessageAdapter(R.layout.adapter_message,null);

        rvMyMessage.setAdapter(messageAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mPresenter.getMessageList(page);
            }
        });

        messageAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;

                mPresenter.getMessageList(page);
            }
        });

        messageAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                mPresenter.getMessageList(messageBeans.get(position).id+"");

                new AlertDialog.Builder(getActivity()).setTitle(messageBeans.get(position).messageContent).setPositiveButton("已读", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();

            }
        });

    }

    @Override
    protected MessagePresenter createPresenter() {
        return new MessagePresenter();
    }

    @Override
    public void getMessageSuccess(PagesBean<MessageBean> messageBeans) {

        if (this.messageBeans == null){
            this.messageBeans = messageBeans.records;
        }else {
            if (page > 1){
                this.messageBeans.addAll(messageBeans.records);
            }else {
                this.messageBeans = messageBeans.records;
            }
        }

        messageAdapter.setNewInstance(this.messageBeans);

        if (messageBeans.size == messageBeans.total) {
            messageAdapter.getLoadMoreModule().loadMoreComplete();
        }else {
            messageAdapter.getLoadMoreModule().loadMoreEnd();
        }

        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    @Override
    public void getReadMessageSuccess(Object o) {
        page = 1;
        mPresenter.getMessageList(page);
    }

    @Override
    public void getReadMessageFail(String str) {
        if (!str.contains("The item is null")){
            UToast.show(getActivity(), str);
        }else {
            page = 1;
            mPresenter.getMessageList(page);
        }
    }

    @Override
    public void getDataFail(String msg) {
        if (!msg.contains("The item is null")){
            UToast.show(getActivity(), msg);
        }

        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }


    @Override
    public boolean immersionBarEnabled() {
        return true;
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).titleBar((R.id.v_title_bar)).init();
    }


}
