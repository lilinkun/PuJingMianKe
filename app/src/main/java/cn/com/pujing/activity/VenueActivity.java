package cn.com.pujing.activity;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gyf.immersionbar.ImmersionBar;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.ImageNetAdapter;
import cn.com.pujing.adapter.VenueAdapter;
import cn.com.pujing.adapter.VenueImageNetAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.BannerBean;
import cn.com.pujing.entity.VenueBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.presenter.VenuePresenter;
import cn.com.pujing.util.ActivityUtil;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.UToast;
import cn.com.pujing.view.VenueView;

/**
 * author : liguo
 * date : 2021/4/2 13:42
 * description :
 */
public class VenueActivity extends BaseActivity<VenueView, VenuePresenter> implements VenueView{

    @BindView(R.id.rv_venue)
    RecyclerView rvVenue;
    @BindView(R.id.banner_venue)
    Banner bannerVenue;

    private VenueAdapter venueAdapter;
    private VenueBean venueBeans;
    private VenueImageNetAdapter imageNetAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_venue;
    }

    @Override
    public void initView() {
        mPresenter.getVenueType();

        ActivityUtil.addHomeActivity(this);

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        venueAdapter = new VenueAdapter(R.layout.adapter_venue,null);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rvVenue.setLayoutManager(linearLayoutManager);
        rvVenue.setAdapter(venueAdapter);


        imageNetAdapter = new VenueImageNetAdapter(null);
        bannerVenue.setAdapter(imageNetAdapter);
        bannerVenue.setIndicator(new CircleIndicator(this));
        bannerVenue.setIndicatorSelectedColor(getResources().getColor(R.color.white));
        bannerVenue.setIndicatorSelectedColor(getResources().getColor(R.color.banner_normal));
        bannerVenue.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {

                Intent intent = new Intent();
                intent.setClass(VenueActivity.this,VenueReserveActivity.class);
                intent.putExtra("venue",venueBeans.serviceVenueManageList.get(position));
                startActivity(intent);

            }
        });


        venueAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                Intent intent = new Intent();
                intent.setClass(VenueActivity.this,VenueReserveActivity.class);
                intent.putExtra("venue",venueBeans.serviceVenueManageList.get(position));
                startActivity(intent);


            }
        });

    }

    @Override
    protected VenuePresenter createPresenter() {
        return new VenuePresenter();
    }

    @Override
    public void getVenueType(VenueBean venueBeans) {
        this.venueBeans = venueBeans;
        venueAdapter.setNewInstance(venueBeans.serviceVenueManageList);
        imageNetAdapter.setDatas(venueBeans.serviceVenueManageList);
        imageNetAdapter.notifyDataSetChanged();
    }

    @Override
    public void getVenueFail(String msg) {
        UToast.show(this,msg);
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
}
