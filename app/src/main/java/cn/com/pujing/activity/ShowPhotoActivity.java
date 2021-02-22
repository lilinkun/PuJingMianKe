package cn.com.pujing.activity;

import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.model.Response;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.ImgViewAdapter;
import cn.com.pujing.adapter.ShowPhotoAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.util.PuJIngUtils;
import cn.com.pujing.util.Urls;

public class ShowPhotoActivity extends BaseActivity {

    @BindView(R.id.rv_show_photo)
    RecyclerView rvShowPhoto;
    @BindView(R.id.vp_show_photo)
    ViewPager vpShowPhoto;

    String currentPic = "";
    String[] showPhoto;

    @Override
    public int getLayoutId() {
        return R.layout.activity_show_photo;
    }

    @Override
    public void init() {

        ImmersionBar.with(this)
                .statusBarColor(R.color.blue_bg)
                .fitsSystemWindows(true)
                .init();

        showPhoto = getIntent().getStringArrayExtra("showphoto");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        ShowPhotoAdapter showPhotoAdapter = new ShowPhotoAdapter(R.layout.adapter_show_photo, Arrays.asList(showPhoto),this);

        rvShowPhoto.addItemDecoration(new DividerItemDecoration(getBaseContext(), DividerItemDecoration.HORIZONTAL));
        rvShowPhoto.setLayoutManager(linearLayoutManager);
        rvShowPhoto.setAdapter(showPhotoAdapter);

        ImgViewAdapter imgViewAdapter = new ImgViewAdapter(showPhoto, this);
        vpShowPhoto.setAdapter(imgViewAdapter);
        currentPic = showPhoto[0];

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

    @OnClick({R.id.iv_back,R.id.tv_show_photo_save})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:

                finish();

                break;

            case R.id.tv_show_photo_save:
                currentPic = showPhoto[vpShowPhoto.getCurrentItem()];
                Glide.with(this)
                                             .asBitmap()
                                             .load(Urls.PREFIX + Urls.IMG + currentPic)
                                             .into(new SimpleTarget<Bitmap>() {

                                                 @Override
                                  public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                                         }
                             });


                break;
        }
    }

}
