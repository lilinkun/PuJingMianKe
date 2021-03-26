package cn.com.pujing.base;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.pujing.callback.RequestCallback;
import cn.com.pujing.entity.Base;


public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity implements RequestCallback {
    public ZLoadingDialog zLoadingDialog;
    Unbinder unbinder = null;

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        //判断是否使用MVP模式
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);//因为之后所有的子类都要实现对应的View接口
        }


        unbinder = ButterKnife.bind(this);

        initView();

    }

    public abstract int getLayoutId();

    public abstract void initView();

    @Override
    public void onSuccess(Response response) {

    }

    @Override
    public void onFail(Base base) {

    }

    @Override
    public void loading(boolean b) {
        if (b) {
            if (zLoadingDialog == null) {
                zLoadingDialog = new ZLoadingDialog(this);
                zLoadingDialog.setLoadingBuilder(Z_TYPE.STAR_LOADING)
                        .setLoadingColor(Color.RED)
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .setHintText("Loading");
            }
            zLoadingDialog.show();
        } else {
            if (zLoadingDialog != null) {
                zLoadingDialog.dismiss();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        zLoadingDialog = null;
        OkGo.cancelTag(OkGo.getInstance().getOkHttpClient(), this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissions() {
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        List<String> list = null;

        for (String permission : permissions) {

            if (checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {

                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(permission);
            }
        }

        if (list != null && list.size() > 0) {
            requestPermissions((String[]) list.toArray(new String[list.size()]), 100);
        }
    }


    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract T createPresenter();

}
