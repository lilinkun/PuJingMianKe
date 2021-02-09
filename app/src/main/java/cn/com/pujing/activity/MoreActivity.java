package cn.com.pujing.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.util.Constants;
import cn.com.pujing.R;
import cn.com.pujing.util.Urls;
import cn.com.pujing.adapter.GridAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.datastructure.GridItem;

public class MoreActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_more;
    }

    public void init() {

        ImmersionBar.with(this).statusBarColor("#ED6D0F").fitsSystemWindows(true).init();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getBaseContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        GridAdapter gridAdapter = new GridAdapter(R.layout.item_grid_another, GridItem.getTestData1());
        gridAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                GridItem gridItem = (GridItem) adapter.getItem(position);

                if (gridItem != null) {
                    if (getString(R.string.life_service).equals(gridItem.title)) {
                        Toast.makeText(MoreActivity.this, getString(R.string.comming_soon), Toast.LENGTH_SHORT).show();
                    } else if (getString(R.string.community_calendar).equals(gridItem.title)) {
                        startActivity(new Intent(MoreActivity.this, CommunityCalendarActivity.class));
                    } else if ("场馆预约".equals(gridItem.title)) {
                        Toast.makeText(MoreActivity.this, getString(R.string.comming_soon), Toast.LENGTH_SHORT).show();
                    } else if (getString(R.string.photo_wall).equals(gridItem.title)) {
                        startActivity(new Intent(MoreActivity.this, PhotoWallActivity.class));
                    } else if ("问卷调查".equals(gridItem.title)) {
                        Intent intent = new Intent(MoreActivity.this, WebviewActivity.class);
                        intent.putExtra(Constants.URL, Urls.SURVEYLIST);
                        startActivity(intent);
                    } else if (getString(R.string.feedback).equals(gridItem.title)) {
                        startActivity(new Intent(getBaseContext(), FeedbackActivity.class));
                    }
                }
            }
        });
        recyclerView.setAdapter(gridAdapter);
    }

    @Override
    @OnClick({R.id.iv_back})
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.iv_back) {
            finish();
        }
    }

    @Override
    public void onSuccess(Response response) {

    }


}
