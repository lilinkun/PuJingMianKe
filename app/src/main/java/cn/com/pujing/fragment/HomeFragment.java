package cn.com.pujing.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gyf.immersionbar.ImmersionBar;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.activity.ActivitiesActivity;
import cn.com.pujing.activity.CommunityCalendarActivity;
import cn.com.pujing.activity.FeedbackActivity;
import cn.com.pujing.activity.HealthCenterActivity;
import cn.com.pujing.activity.LifeServiceActivity;
import cn.com.pujing.activity.LifeTypeActivity;
import cn.com.pujing.activity.LoginActivity;
import cn.com.pujing.activity.MainActivity;
import cn.com.pujing.activity.PhotoWallActivity;
import cn.com.pujing.activity.PictureWallActivity;
import cn.com.pujing.activity.ShowPhotoActivity;
import cn.com.pujing.activity.VenueActivity;
import cn.com.pujing.activity.VenueReserveActivity;
import cn.com.pujing.activity.WebviewActivity;
import cn.com.pujing.adapter.GridAdapter;
import cn.com.pujing.adapter.ImageNetAdapter;
import cn.com.pujing.adapter.TopLineAdapter;
import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.db.DBManager;
import cn.com.pujing.entity.BannerBean;
import cn.com.pujing.entity.Base;
import cn.com.pujing.entity.BasicServiceVoListBean;
import cn.com.pujing.entity.GridItem;
import cn.com.pujing.entity.NotifyInfoBean;
import cn.com.pujing.entity.PhotoBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.presenter.HomePresenter;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.Methods;
import cn.com.pujing.util.PuJingUtils;
import cn.com.pujing.util.UToast;
import cn.com.pujing.util.Urls;
import cn.com.pujing.view.HomeView;
import cn.com.pujing.widget.HomePopupWindow;
import cn.com.pujing.widget.SearchPupWindow;

public class HomeFragment extends BaseFragment<HomeView, HomePresenter> implements View.OnClickListener, HomeView, SearchPupWindow.OnHomeClick {

    private ImageNetAdapter imageNetAdapter;
    private TopLineAdapter topLineAdapter;
    @BindView(R.id.sl)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.banner_another)
    Banner anotherBanner;
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.iv_photo_wall_1)
    ImageView ivPhotoWall1;
    @BindView(R.id.iv_photo_wall_2)
    ImageView ivPhotoWall2;
    @BindView(R.id.iv_photo_wall_3)
    ImageView ivPhotoWall3;
    @BindView(R.id.iv_photo_wall_4)
    ImageView ivPhotoWall4;
    @BindView(R.id.iv_photo_wall_5)
    ImageView ivPhotoWall5;


    private String[] strings;
    private GridAdapter gridAdapter;
    private int mLoginOut = 0;
    private List<BannerBean> bannerBeans;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initEventAndData() {

        swipeRefreshLayout.setColorSchemeResources(R.color.purple_500, R.color.colorAccent, R.color.teal_200);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requesData();

                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 3000);
            }
        });

        imageNetAdapter = new ImageNetAdapter(null,0);
        banner.setAdapter(imageNetAdapter);
        banner.setIndicator(new CircleIndicator(getContext()));
        banner.setIndicatorSelectedColor(getActivity().getResources().getColor(R.color.white));
        banner.setIndicatorSelectedColor(getActivity().getResources().getColor(R.color.banner_normal));
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {

                BannerBean bannerBean = bannerBeans.get(position);

                PuJingUtils.bannerClick(getActivity(),bannerBean);

            }
        });

        if (Methods.getValueByKey("green",getActivity()) != null && Methods.getValueByKey("green",getActivity()).length() != 0) {
            if (Double.valueOf(Methods.getValueByKey("green",getActivity())) < Double.valueOf(PuJingUtils.getVersion(getActivity()))){
                DBManager.getInstance(getActivity()).deleteHomeTitle();
                Methods.saveKeyValue("green", PuJingUtils.getVersion(getActivity()), getActivity());
            }
        }else {
            Methods.saveKeyValue("green", PuJingUtils.getVersion(getActivity()), getActivity());
        }


        if (DBManager.getInstance(getActivity()).queryHomeTitle().size() == 0){
            for (GridItem gridItem : GridItem.getTestData()) {
                DBManager.getInstance(getActivity()).insertHomeTitle(gridItem);
            }
        }else {
            if (DBManager.getInstance(getActivity()).queryHomeTitle().size() != GridItem.getTestData().size()){
                DBManager.getInstance(getActivity()).deleteHomeTitle();
                for (GridItem gridItem : GridItem.getTestData()) {
                    DBManager.getInstance(getActivity()).insertHomeTitle(gridItem);
                }
            }
        }

        List<GridItem> gridItems = DBManager.getInstance(getActivity()).queryHomeTitle();
        List<GridItem> gridItems1 = new ArrayList<>();
        for (int i = 0 ; i<gridItems.size();i++){
            if (i<5 || i == gridItems.size()-1) {
                gridItems1.add(gridItems.get(i));
            }
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rvHome.setLayoutManager(gridLayoutManager);
        gridAdapter = new GridAdapter(R.layout.item_grid, gridItems1);
        gridAdapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                GridItem gridItem = (GridItem) adapter.getItem(position);

                onClickItem(gridItem);
            }
        });
        rvHome.setAdapter(gridAdapter);

        topLineAdapter = new TopLineAdapter(null);
        anotherBanner.setAdapter(topLineAdapter);
        anotherBanner.setOrientation(Banner.VERTICAL);
        anotherBanner.setPageTransformer(new ZoomOutPageTransformer());
        anotherBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra(Constants.URL, PujingService.NOTICE);
                startActivity(intent);
            }
        });

        requesData();
    }

    private void requesData() {

        mPresenter.getBannerData();

        mPresenter.getNoticeData();

        mPresenter.getHomePhoto();

    }

    @Override
    public boolean immersionBarEnabled() {
        return true;
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).titleBar((R.id.v_title_bar)).init();
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    @OnClick({R.id.tv_more,R.id.iv_photo_wall_1,R.id.iv_photo_wall_2,R.id.iv_photo_wall_3,R.id.iv_photo_wall_4,R.id.iv_photo_wall_5})
    public void onClick(View v) {
        int id = v.getId();

        switch (v.getId()){

            case R.id.tv_more:
            case R.id.iv_photo_wall_1:
            case R.id.iv_photo_wall_2:
            case R.id.iv_photo_wall_3:
            case R.id.iv_photo_wall_4:
            case R.id.iv_photo_wall_5:

                startActivity(new Intent(getContext(), PictureWallActivity.class));

                break;
            default:
                break;

        }

    }

    @Override
    public void onFail(Base base) {
        super.onFail(base);
        if (base.code == 5){
            mLoginOut++;
            if (mLoginOut == 1) {
                Methods.saveKeyValue(Constants.AUTHORIZATION, "", getActivity());
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        }
    }

    @Override
    public void getBannerDataSuccess(List<BannerBean> data) {
        bannerBeans = data;
        if (data != null && data.size() > 0) {
            banner.setVisibility(View.VISIBLE);
            imageNetAdapter.setDatas(bannerBeans);
            imageNetAdapter.notifyDataSetChanged();
        }else {
//            banner.setVisibility(View.GONE);
        }
    }

    @Override
    public void getBannerDataFail(String msg) {
        if (banner != null) {
            banner.setVisibility(View.INVISIBLE);
        }
        if (msg.contains("无效用户") || msg.contains("令牌失效")){
            mLoginOut++;
            if (mLoginOut == 1) {
                Methods.saveKeyValue(Constants.AUTHORIZATION, "", getActivity());
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        }
    }

    @Override
    public void getNoticeDataSuccess(NotifyInfoBean notifyInfoBean) {
        topLineAdapter.setDatas(notifyInfoBean.getRecords());
        topLineAdapter.notifyDataSetChanged();
    }

    @Override
    public void getPhotoDataSuccess(PhotoBean photoBean) {
        String photo = photoBean.getPhoto();
        String[] strings = photo.split(",");
        this.strings = strings;
        if (strings != null) {
            for (int i = 0; i < strings.length; i++) {
                switch (i) {
                    case 0:
                        Glide.with(getContext())
                                .load(PujingService.PREFIX + PujingService.IMG + strings[i])
                                .into(ivPhotoWall1);
                        break;
                    case 1:
                        Glide.with(getContext())
                                .load(PujingService.PREFIX + PujingService.IMG + strings[i])
                                .into(ivPhotoWall2);
                        break;
                    case 2:
                        Glide.with(getContext())
                                .load(PujingService.PREFIX + PujingService.IMG + strings[i])
                                .into(ivPhotoWall3);
                        break;
                    case 3:
                        Glide.with(getContext())
                                .load(PujingService.PREFIX + PujingService.IMG + strings[i])
                                .into(ivPhotoWall4);
                        break;
                    case 4:
                        Glide.with(getContext())
                                .load(PujingService.PREFIX + PujingService.IMG + strings[i])
                                .into(ivPhotoWall5);
                        break;
                    default:
                        break;
                }
            }
        }

        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void getDataError(String message) {
        if (message.contains("无效用户")|| message.contains("令牌失效")){
            mLoginOut++;
            if (mLoginOut == 1) {
                Methods.saveKeyValue(Constants.AUTHORIZATION, "", getActivity());
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        }else {
            UToast.show(getActivity(),message);
        }

        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onHomeClick(GridItem gridItem) {
        onClickItem(gridItem);
    }

    private void onClickItem(GridItem gridItem){
        if (gridItem != null) {
            if (getString(R.string.life_service).equals(gridItem.title)) {
                Intent intent = new Intent(getContext(), LifeServiceActivity.class);
                startActivity(intent);
            } else if (getString(R.string.exercise).equals(gridItem.title)) {
                /*MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.setCurPos(2);*/
                startActivity(new Intent(getContext(), ActivitiesActivity.class));
            } else if (getString(R.string.community_calendar).equals(gridItem.title)) {
                startActivity(new Intent(getContext(), CommunityCalendarActivity.class));
            } else if (getString(R.string.restaurant).equals(gridItem.title)) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.setCurPos(1);
            } else if ("场馆预约".equals(gridItem.title)) {
                startActivity(new Intent(getContext(), VenueActivity.class));
            } else if (getString(R.string.photo_wall).equals(gridItem.title)) {
                startActivity(new Intent(getContext(), PictureWallActivity.class));
            }else if (getString(R.string.feedback).equals(gridItem.title)) {
                startActivity(new Intent(getContext(), FeedbackActivity.class));
            }else if (getString(R.string.question_investigation).equals(gridItem.title)) {
                Intent intent = new Intent(getContext(), WebviewActivity.class);
                intent.putExtra(Constants.URL, PujingService.SURVEYLIST);
                startActivity(intent);
            }else if (getString(R.string.health_manager).equals(gridItem.title)) {
                Intent intent = new Intent(getContext(), HealthCenterActivity.class);
                startActivity(intent);
            } else if (getString(R.string.more_services).equals(gridItem.title)) {
//                        startActivity(new Intent(getContext(), MoreActivity.class));
                HomePopupWindow homePopupWindow = new HomePopupWindow(getContext());
                homePopupWindow.showPopupWindow(banner);
                homePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {

                        List<GridItem> gridItems = DBManager.getInstance(getActivity()).queryHomeTitle();
                        List<GridItem> gridItems1 = new ArrayList<>();
                        for (int i = 0 ; i<gridItems.size();i++){
                            if (i<5 || i == 9) {
                                gridItems1.add(gridItems.get(i));
                            }
                        }

                        gridAdapter.setNewInstance(gridItems1);
                    }
                });
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


}
