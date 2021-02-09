package cn.com.pujing.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.ZoomOutPageTransformer;

import cn.com.pujing.Constants;
import cn.com.pujing.R;
import cn.com.pujing.Urls;
import cn.com.pujing.activity.CommunityCalendarActivity;
import cn.com.pujing.activity.MainActivity;
import cn.com.pujing.activity.MoreActivity;
import cn.com.pujing.activity.MyMsgActivity;
import cn.com.pujing.activity.PhotoWallActivity;
import cn.com.pujing.activity.WebviewActivity;
import cn.com.pujing.adapter.GridAdapter;
import cn.com.pujing.adapter.ImageNetAdapter;
import cn.com.pujing.adapter.TopLineAdapter;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.callback.RequestCallback;
import cn.com.pujing.datastructure.BannerInfo;
import cn.com.pujing.datastructure.GetPhoto;
import cn.com.pujing.datastructure.GridItem;
import cn.com.pujing.datastructure.NotifyInfo;

public class HomeFragment extends BaseFragment implements RequestCallback, View.OnClickListener {
    private View view;
    private ImageNetAdapter imageNetAdapter;
    private TopLineAdapter topLineAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String[] strings;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, null);
            init(view);
        }
        return view;
    }

    private void init(View view) {
        swipeRefreshLayout = view.findViewById(R.id.sl);
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

        view.findViewById(R.id.iv_search).setOnClickListener(this);
        view.findViewById(R.id.iv_msg).setOnClickListener(this);

        Banner banner = view.findViewById(R.id.banner);
        imageNetAdapter = new ImageNetAdapter(null);
        banner.setAdapter(imageNetAdapter);
        banner.setIndicator(new CircleIndicator(getContext()));
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {
                BannerInfo.Data data1 = (BannerInfo.Data) data;
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra(Constants.URL, Urls.EVENTDETAILS + data1.linkAddress);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.rv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        GridAdapter gridAdapter = new GridAdapter(R.layout.item_grid, GridItem.getTestData());
        gridAdapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                GridItem gridItem = (GridItem) adapter.getItem(position);

                if (gridItem != null) {
                    if (getString(R.string.life_service).equals(gridItem.title)) {
                        Toast.makeText(getActivity(), getString(R.string.comming_soon), Toast.LENGTH_SHORT).show();
                    } else if (getString(R.string.exercise).equals(gridItem.title)) {
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.setCurPos(2);
                    } else if (getString(R.string.community_calendar).equals(gridItem.title)) {
                        startActivity(new Intent(getContext(), CommunityCalendarActivity.class));
                    } else if (getString(R.string.restaurant).equals(gridItem.title)) {
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.setCurPos(1);
                    } else if (getString(R.string.photo_wall).equals(gridItem.title)) {
                        startActivity(new Intent(getContext(), PhotoWallActivity.class));
                    } else if (getString(R.string.more_services).equals(gridItem.title)) {
                        startActivity(new Intent(getContext(), MoreActivity.class));
                    }
                }
            }
        });
        recyclerView.setAdapter(gridAdapter);

        Banner anotherBanner = view.findViewById(R.id.banner_another);
        topLineAdapter = new TopLineAdapter(null);
        anotherBanner.setAdapter(topLineAdapter);
        anotherBanner.setOrientation(Banner.VERTICAL);
        anotherBanner.setPageTransformer(new ZoomOutPageTransformer());
        anotherBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra(Constants.URL, Urls.NOTICE);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.tv_more).setOnClickListener(this);
        view.findViewById(R.id.iv_photo_wall_1).setOnClickListener(this);
        view.findViewById(R.id.iv_photo_wall_2).setOnClickListener(this);
        view.findViewById(R.id.iv_photo_wall_3).setOnClickListener(this);

        requesData();
    }

    private void requesData() {
        OkGo.get(Urls.BANNER)
                .tag(this)
                .execute(new JsonCallback<>(BannerInfo.class, this));

        OkGo.get(Urls.NOTIFY)
                .tag(this)
                .params(Constants.QUERYIDENTIFY, Constants.RELEASED)
                .execute(new JsonCallback<>(NotifyInfo.class, this));

        OkGo.get(Urls.GETPHOTO)
                .tag(this)
                .execute(new JsonCallback<>(GetPhoto.class, this));
    }

    @Override
    public void onSuccess(Response response) {
        if (response != null) {

            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }

            if (response.body() instanceof BannerInfo) {
                BannerInfo bannerInfo = (BannerInfo) response.body();
                imageNetAdapter.setDatas(bannerInfo.data);
                imageNetAdapter.notifyDataSetChanged();
            } else if (response.body() instanceof NotifyInfo) {
                NotifyInfo notifyInfo = (NotifyInfo) response.body();
                NotifyInfo.Data data = notifyInfo.data;
                topLineAdapter.setDatas(data.records);
                topLineAdapter.notifyDataSetChanged();
            } else if (response.body() instanceof GetPhoto) {
                GetPhoto getPhoto = (GetPhoto) response.body();
                String photo = getPhoto.data.photo;
                String[] strings = photo.split(",");
                this.strings = strings;
                if (strings != null) {
                    for (int i = 0; i < strings.length; i++) {
                        switch (i) {
                            case 0:
                                ImageView imageView1 = view.findViewById(R.id.iv_photo_wall_1);
                                Glide.with(getContext())
                                        .load(Urls.PREFIX + Urls.IMG + strings[i])
                                        .into(imageView1);
                                break;
                            case 1:
                                ImageView imageView2 = view.findViewById(R.id.iv_photo_wall_2);
                                Glide.with(getContext())
                                        .load(Urls.PREFIX + Urls.IMG + strings[i])
                                        .into(imageView2);
                                break;
                            case 2:
                                ImageView imageView3 = view.findViewById(R.id.iv_photo_wall_3);
                                Glide.with(getContext())
                                        .load(Urls.PREFIX + Urls.IMG + strings[i])
                                        .into(imageView3);
                                break;
                        }
                    }
                }
            }
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

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.iv_search) {
            Toast.makeText(getActivity(), getString(R.string.comming_soon), Toast.LENGTH_SHORT).show();
        } else if (id == R.id.iv_msg) {
//            startActivity(new Intent(getActivity(), MyMsgActivity.class));
            Toast.makeText(getActivity(), getString(R.string.comming_soon), Toast.LENGTH_SHORT).show();
        } else if (id == R.id.tv_more) {
            startActivity(new Intent(getContext(), PhotoWallActivity.class));
        } else if (id == R.id.iv_photo_wall_1) {
            if (strings != null) {
                ImgViewDialogFragment imgViewDialogFragment = new ImgViewDialogFragment(0, strings);
                imgViewDialogFragment.show(getFragmentManager(), "");
            }
        } else if (id == R.id.iv_photo_wall_2) {
            if (strings != null) {
                ImgViewDialogFragment imgViewDialogFragment = new ImgViewDialogFragment(1, strings);
                imgViewDialogFragment.show(getFragmentManager(), "");
            }
        } else if (id == R.id.iv_photo_wall_3) {
            if (strings != null) {
                ImgViewDialogFragment imgViewDialogFragment = new ImgViewDialogFragment(2, strings);
                imgViewDialogFragment.show(getFragmentManager(), "");
            }
        }
    }
}
