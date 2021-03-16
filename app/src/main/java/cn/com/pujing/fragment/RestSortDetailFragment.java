package cn.com.pujing.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.pujing.R;
import cn.com.pujing.activity.RestDetailActivity;
import cn.com.pujing.activity.SetMealDetailActivity;
import cn.com.pujing.adapter.RestDetailAdapter;
import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.callback.CheckListener;
import cn.com.pujing.entity.BanquetBean;
import cn.com.pujing.entity.ChangeDataBean;
import cn.com.pujing.entity.RestSortDetailBean;
import cn.com.pujing.presenter.RestSortDetailPresenter;
import cn.com.pujing.view.RestSortDetailView;
import cn.com.pujing.widget.ItemHeaderDecoration;

import static android.app.Activity.RESULT_OK;

/**
 * author : liguo
 * date : 2021/3/5 14:59
 * description :
 */
public class RestSortDetailFragment extends BaseFragment<RestSortDetailView, RestSortDetailPresenter> implements RestSortDetailView, RestDetailAdapter.OnRestLisener {

    @BindView(R.id.rv_rest_sort_detail)
    RecyclerView rvRestSortDetail;

    RestDetailAdapter restDetailAdapter;
    private CheckListener checkListener;
    private ItemHeaderDecoration mDecoration;
    private int mIndex = 0;
    private int rest_type;
    private boolean move = false;
    private LinearLayoutManager mManager;
    private List<RestSortDetailBean> restSortDetailBeans = new ArrayList<>();

    private static int REQUESTCODE =0x111;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_rest_sort_detail;
    }

    @Override
    public void initEventAndData() {


        mManager = new LinearLayoutManager(getActivity());

        mManager.setOrientation(RecyclerView.VERTICAL);

        rvRestSortDetail.setLayoutManager(mManager);
        restDetailAdapter = new RestDetailAdapter(getActivity(),restSortDetailBeans,this);

        rvRestSortDetail.setAdapter(restDetailAdapter);

        mDecoration = new ItemHeaderDecoration(getActivity(), restSortDetailBeans);
        rvRestSortDetail.addItemDecoration(mDecoration);
        mDecoration.setCheckListener(checkListener);


        initData();
        initListener();
    }

    private void initData(){

        BanquetBean banquetBean = (BanquetBean) getArguments().getSerializable("right");
        rest_type =  getArguments().getInt("rest_type");

        for (int i = 0;i < banquetBean.getCategoryList().size();i++){
            RestSortDetailBean sortDetailBean = new RestSortDetailBean();
            sortDetailBean.setTitle(true);
            sortDetailBean.setName(banquetBean.getCategoryList().get(i).getGroupName());
            sortDetailBean.setTitleName(banquetBean.getCategoryList().get(i).getGroupName());
            sortDetailBean.setTag(String.valueOf(i));
            sortDetailBean.setFoodId(0);
            restSortDetailBeans.add(sortDetailBean);

            List<BanquetBean.CategoryList.Categorys> categorys = banquetBean.getCategoryList().get(i).categorys;

            for (int j = 0; j < categorys.size();j++){
                RestSortDetailBean sortDetailBean1 = new RestSortDetailBean();
                sortDetailBean1.setTitleName(banquetBean.getCategoryList().get(i).getGroupName());
                sortDetailBean1.setName(categorys.get(j).name);
                sortDetailBean1.setTitle(false);
                sortDetailBean1.setImgsrc(categorys.get(j).picId);
                sortDetailBean1.setPrice(categorys.get(j).price);
                sortDetailBean1.setDetail(categorys.get(j).remark);
                sortDetailBean1.setTag(String.valueOf(i));
                sortDetailBean1.setmId(categorys.get(j).id);
                sortDetailBean1.setFoodId(categorys.get(j).foodId);
                sortDetailBean1.setType(categorys.get(j).type);
                restSortDetailBeans.add(sortDetailBean1);
            }
        }

        restDetailAdapter.setNewInstance(restSortDetailBeans);
        mDecoration.setData(restSortDetailBeans);

        restDetailAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                if (restSortDetailBeans.get(position) != null && restSortDetailBeans.get(position).getmId() != 0) {
                    if (restSortDetailBeans.get(position).getType().equals("1")) {
                        intentActivity(RestDetailActivity.class,position);
                    }else {
                        intentActivity(SetMealDetailActivity.class,position);
                    }
                }
            }
        });
    }

    private void intentActivity(Class aClass,int position){
        Intent intent = new Intent(getActivity(), aClass);
        intent.putExtra("type",rest_type);
        intent.putExtra("restSortDetailBean", restSortDetailBeans.get(position));
        startActivityForResult(intent,REQUESTCODE);
    }

    @Override
    protected RestSortDetailPresenter createPresenter() {
        return new RestSortDetailPresenter();
    }


    public void setListener(CheckListener listener) {
        this.checkListener = listener;
    }


    public void setData(int n) {
        mIndex = n;
        rvRestSortDetail.stopScroll();
        smoothMoveToPosition(n);
    }

    public void setNum(ChangeDataBean changeDataBean,int type){
        for (RestSortDetailBean sortDetailBean : restSortDetailBeans){
            sortDetailBean.setGoodsNum(0);
        }

        for (int i = 0;i<restSortDetailBeans.size();i++){
            if (changeDataBean == null || changeDataBean.detailList.size() == 0){
                restSortDetailBeans.get(i).setGoodsNum(0);
            }else {
                for (int j = 0; j < changeDataBean.detailList.size(); j++) {
                    if (changeDataBean.detailList.get(j).menuItemId == restSortDetailBeans.get(i).getmId()) {
                        restSortDetailBeans.get(i).setGoodsNum(changeDataBean.detailList.get(j).quantity);
                    }
                }
            }
        }
        if (type == 1){
            restDetailAdapter.notifyDataSetChanged();
        }else {
            restDetailAdapter.setNewInstance(restSortDetailBeans);
        }

//        restDetailAdapter.notifyItemRangeChanged(0,restSortDetailBeans.size());
    }


    private void smoothMoveToPosition(int n) {
        int firstItem = mManager.findFirstVisibleItemPosition();
        int lastItem = mManager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            rvRestSortDetail.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = rvRestSortDetail.getChildAt(n - firstItem).getTop();
            rvRestSortDetail.scrollBy(0, top);
        } else {
            rvRestSortDetail.scrollToPosition(n);
            move = true;
        }
    }

    @Override
    public void onAdd(int menuItemId, int quantity) {
        checkListener.onAdd(menuItemId,quantity);
    }


    private class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
                move = false;
                int n = mIndex - mManager.findFirstVisibleItemPosition();
                if (0 <= n && n < rvRestSortDetail.getChildCount()) {
                    int top = rvRestSortDetail.getChildAt(n).getTop();
                    rvRestSortDetail.smoothScrollBy(0, top);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (move) {
                move = false;
                int n = mIndex - mManager.findFirstVisibleItemPosition();
                if (0 <= n && n < rvRestSortDetail.getChildCount()) {
                    int top = rvRestSortDetail.getChildAt(n).getTop();
                    rvRestSortDetail.scrollBy(0, top);
                }
            }
        }
    }


    public void initListener() {
        rvRestSortDetail.addOnScrollListener(new RecyclerViewListener());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == REQUESTCODE){
                checkListener.query();
            }
        }
    }
}
