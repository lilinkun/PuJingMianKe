package cn.com.pujing.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.ImgViewAdapter;
import cn.com.pujing.adapter.ShowPhotoAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.util.FileUtils;
import cn.com.pujing.util.Urls;

public class ShowPhotoActivity extends BaseActivity {

    @BindView(R.id.rv_show_photo)
    RecyclerView rvShowPhoto;
    @BindView(R.id.vp_show_photo)
    ViewPager vpShowPhoto;

    String currentPic = "";
    String[] showPhoto;
    int pos = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_show_photo;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this)
                .statusBarColor(R.color.blue_bg)
                .fitsSystemWindows(true)
                .init();

        showPhoto = getIntent().getStringArrayExtra("showphoto");
        pos = getIntent().getIntExtra("pos",0);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        ShowPhotoAdapter showPhotoAdapter = new ShowPhotoAdapter(R.layout.adapter_show_photo, Arrays.asList(showPhoto),this);

        rvShowPhoto.addItemDecoration(new DividerItemDecoration(getBaseContext(), DividerItemDecoration.HORIZONTAL));
        rvShowPhoto.setLayoutManager(linearLayoutManager);
        rvShowPhoto.setAdapter(showPhotoAdapter);

        ImgViewAdapter imgViewAdapter = new ImgViewAdapter(showPhoto, this);
        vpShowPhoto.setAdapter(imgViewAdapter);
        currentPic = showPhoto[pos];
        vpShowPhoto.setCurrentItem(pos);
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


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();
        requestPermissions();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
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
                     .load(PujingService.PREFIX + Urls.IMG + currentPic).into(new SimpleTarget<Bitmap>() {
                      @Override
                      public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                              FileUtils.saveImageToGallery2(ShowPhotoActivity.this, resource);
                          }else {
                              FileUtils.saveImage(resource,ShowPhotoActivity.this);
                          }
                          }
                     });

                break;
        }
    }



}
