package cn.com.pujing.activity;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.OkGo;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.PictureWallAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.entity.PagesBean;
import cn.com.pujing.entity.PhotoWall;
import cn.com.pujing.entity.PictureWallBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.presenter.PictureWallPresenter;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.PictureWallView;

/**
 * author : liguo
 * date : 2021/4/15 15:35
 * description :
 */
public class PictureWallActivity extends BaseActivity<PictureWallView, PictureWallPresenter> implements PictureWallView, PictureWallAdapter.OnClickListener {


    @BindView(R.id.rv_photo_wall)
    RecyclerView recyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    private int page = 1;
    private PictureWallAdapter pictureWallAdapter;
    private List<PictureWallBean> pictureWallBeans;

    @Override
    public int getLayoutId() {
        return R.layout.activity_photo_wall;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mPresenter.showPicInfoList(page);
            }
        });

        mPresenter.showPicInfoList(page);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        pictureWallAdapter = new PictureWallAdapter(R.layout.adapter_picture_wall,null,this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(pictureWallAdapter);

        pictureWallAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;

                mPresenter.showPicInfoList(page);
            }
        });

    }

    @Override
    protected PictureWallPresenter createPresenter() {
        return new PictureWallPresenter();
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:

                finish();

                break;

            default:
                break;
        }
    }

    @Override
    public void showPicInfoListSuccess(PagesBean<PictureWallBean> pagesBean) {


        if (pictureWallBeans == null){
            pictureWallBeans = pagesBean.records;
        }else {
            if (pagesBean.current > 1){
                if (pagesBean.records != null && pagesBean.records.size() > 0) {
                    pictureWallBeans.addAll(pagesBean.records);
                }
            }else {
                pictureWallBeans = pagesBean.records;
            }
        }

        pictureWallAdapter.setNewInstance(pictureWallBeans);

        if (pictureWallBeans.size() == pagesBean.total) {
            pictureWallAdapter.getLoadMoreModule().loadMoreEnd();
        }else {
            pictureWallAdapter.getLoadMoreModule().loadMoreComplete();
        }

        if (swipeLayout != null && swipeLayout.isRefreshing()){
            swipeLayout.setRefreshing(false);
        }
    }

    @Override
    public void getDataFail(String msg) {

        UToast.show(this,msg);

        if (swipeLayout != null && swipeLayout.isRefreshing()){
            swipeLayout.setRefreshing(false);
        }
    }

    @Override
    public void addCollectSuccess(Object o) {
        UToast.show(this,"收藏成功");
        mPresenter.showPicInfoList(page);
    }

    @Override
    public void cancelCollectSuccess(Object o) {
        UToast.show(this,"取消收藏");
        mPresenter.showPicInfoList(page);
    }

    @Override
    public void doLikeSuccess(Object o) {
        mPresenter.showPicInfoList(page);
    }

    @Override
    public void unDoLikeSuccess(Object o) {
        mPresenter.showPicInfoList(page);
    }

    @Override
    public void getCollect(int id) {
        mPresenter.addCollect(id);
    }

    @Override
    public void getUnCollect(int id) {
        mPresenter.cancelCollect(id);
    }

    @Override
    public void getLike(int id) {
        mPresenter.doLike(id);
    }

    @Override
    public void getUnLike(int id) {
        mPresenter.unDoLike(id);
    }
}
