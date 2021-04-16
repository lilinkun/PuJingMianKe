package cn.com.pujing.activity;

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
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.MyCollectAdapter;
import cn.com.pujing.adapter.OrderAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.CollectBean;
import cn.com.pujing.entity.HotActivityBean;
import cn.com.pujing.entity.OrderTpeBean;
import cn.com.pujing.entity.PagesBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.presenter.MyCollectPresenter;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.MyCollectView;
import cn.com.pujing.widget.MyOrderPopup;

/**
 * author : liguo
 * date : 2021/4/16 10:51
 * description :
 */
public class MyCollectActivity extends BaseActivity<MyCollectView, MyCollectPresenter> implements MyCollectView, MyOrderPopup.MyOrderClickListener {

    private int page = 1;
    private int ordertypeId = 0;
    private String startDate = "";
    private String endDate = "";
    private MyCollectAdapter myCollectAdapter;
    private List<CollectBean> collectBeans;

    @BindView(R.id.rv_my_collect)
    RecyclerView rvMyCollect;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_collect;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        myCollectAdapter = new MyCollectAdapter(R.layout.adapter_my_collect,null);
        rvMyCollect.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        rvMyCollect.addItemDecoration(new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL));
        rvMyCollect.setAdapter(myCollectAdapter);

        mPresenter.getMyCollect(page,ordertypeId,startDate,endDate);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;

                mPresenter.getMyCollect(page,ordertypeId,startDate,endDate);
            }
        });

        myCollectAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;

                mPresenter.getMyCollect(page,ordertypeId,startDate,endDate);
            }
        });

        myCollectAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (collectBeans.get(position).type == 0){
                    Intent intent = new Intent(MyCollectActivity.this, WebviewActivity.class);
                    intent.putExtra(Constants.URL, PujingService.EVENTDETAILS + collectBeans.get(position).collectId);
                    startActivity(intent);
                }else if (collectBeans.get(position).type == 1){

                    Intent intent = new Intent(MyCollectActivity.this, ShowPhotoActivity.class);
                    intent.putExtra("showphoto", collectBeans.get(position).photo.split(","));
                    intent.putExtra("pos", 0);
                    startActivity(intent);
                }
            }
        });


    }

    @Override
    protected MyCollectPresenter createPresenter() {
        return new MyCollectPresenter();
    }

    @Override
    public void getMyCollectSuccess(PagesBean<CollectBean> pagesBean) {


        if (collectBeans == null){
            collectBeans = pagesBean.records;
        }else {
            if (page > 1){
                collectBeans.addAll(pagesBean.records);
            }else {
                collectBeans = pagesBean.records;
            }
        }

        myCollectAdapter.setNewInstance(pagesBean.records);

        if (pagesBean.size == pagesBean.total) {
            myCollectAdapter.getLoadMoreModule().loadMoreComplete();
        }else {
            myCollectAdapter.getLoadMoreModule().loadMoreEnd();
        }

        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(this,msg);

        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @OnClick({R.id.iv_my_album_filter,R.id.iv_my_album})
    public void onClick(View view){
        if (view.getId() == R.id.iv_my_album_filter){

            MyOrderPopup myOrderPopup = new MyOrderPopup(this,this,1);
            myOrderPopup.showAsDropDown(findViewById(R.id.v_title_bar));
        }else if (view.getId() == R.id.iv_my_album){
            finish();
        }
    }

    @Override
    public void setItemValue(OrderTpeBean orderTpeBean, String startDate, String endDate) {

        page = 1;
        ordertypeId = orderTpeBean.typeId;
        this.startDate = startDate;
        this.endDate = endDate;
        mPresenter.getMyCollect(page,ordertypeId,startDate,endDate);
    }
}
