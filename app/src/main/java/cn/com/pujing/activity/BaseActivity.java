package cn.com.pujing.activity;

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
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.util.ArrayList;
import java.util.List;

import cn.com.pujing.callback.RequestCallback;


public abstract class BaseActivity extends AppCompatActivity implements RequestCallback {
    public ZLoadingDialog zLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        init();
    }

    public abstract void init();

    @Override
    public void loading(boolean b) {
        if (b) {
            if (zLoadingDialog == null) {
                zLoadingDialog = new ZLoadingDialog(this);
                zLoadingDialog.setLoadingBuilder(Z_TYPE.STAR_LOADING)
                        .setLoadingColor(Color.RED)
                        .setHintText("Loading...");
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
}
