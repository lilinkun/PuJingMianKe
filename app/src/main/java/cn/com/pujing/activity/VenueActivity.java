package cn.com.pujing.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;

import butterknife.BindView;
import cn.com.pujing.R;
import cn.com.pujing.adapter.VenueAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.VenueBean;
import cn.com.pujing.presenter.VenuePresenter;
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

    private VenueAdapter venueAdapter;
    private ArrayList<VenueBean> venueBeans;

    @Override
    public int getLayoutId() {
        return R.layout.activity_venue;
    }

    @Override
    public void initView() {
        mPresenter.getVenueType();


        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        venueAdapter = new VenueAdapter(R.layout.adapter_venue,null);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rvVenue.setLayoutManager(linearLayoutManager);
        rvVenue.setAdapter(venueAdapter);

        venueAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                Intent intent = new Intent();
                intent.setClass(VenueActivity.this,VenueReserveActivity.class);
                intent.putExtra("venue",venueBeans.get(position));
                startActivity(intent);


            }
        });

    }

    @Override
    protected VenuePresenter createPresenter() {
        return new VenuePresenter();
    }

    @Override
    public void getVenueType(ArrayList<VenueBean> venueBeans) {
        this.venueBeans = venueBeans;
        venueAdapter.setNewInstance(venueBeans);
    }

    @Override
    public void getVenueFail(String msg) {
        UToast.show(this,msg);
    }
}
