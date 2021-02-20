package cn.com.pujing.activity;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lzy.okgo.model.Response;

import java.util.Arrays;

import butterknife.BindView;
import cn.com.pujing.R;
import cn.com.pujing.adapter.ImgViewAdapter;
import cn.com.pujing.adapter.ShowPhotoAdapter;
import cn.com.pujing.base.BaseActivity;

public class ShowPhotoActivity extends BaseActivity {

    @BindView(R.id.rv_show_photo)
    RecyclerView rvShowPhoto;
    @BindView(R.id.vp_show_photo)
    ViewPager vpShowPhoto;


    @Override
    public int getLayoutId() {
        return R.layout.activity_show_photo;
    }

    @Override
    public void init() {

        String[] showPhoto = getIntent().getStringArrayExtra("showphoto");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        ShowPhotoAdapter showPhotoAdapter = new ShowPhotoAdapter(R.layout.adapter_show_photo, Arrays.asList(showPhoto),this);

        rvShowPhoto.addItemDecoration(new DividerItemDecoration(getBaseContext(), DividerItemDecoration.HORIZONTAL));
        rvShowPhoto.setLayoutManager(linearLayoutManager);
        rvShowPhoto.setAdapter(showPhotoAdapter);

        ImgViewAdapter imgViewAdapter = new ImgViewAdapter(showPhoto, this);
        vpShowPhoto.setAdapter(imgViewAdapter);

        showPhotoAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                vpShowPhoto.setCurrentItem(position,false);
            }
        });
    }

    @Override
    public void onSuccess(Response response) {

    }
}
