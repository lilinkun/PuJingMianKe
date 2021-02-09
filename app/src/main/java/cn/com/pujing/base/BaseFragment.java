package cn.com.pujing.base;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gyf.immersionbar.components.SimpleImmersionOwner;
import com.gyf.immersionbar.components.SimpleImmersionProxy;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.pujing.callback.RequestCallback;

public abstract class BaseFragment extends Fragment implements RequestCallback, SimpleImmersionOwner {
    public ZLoadingDialog zLoadingDialog;

    public Unbinder unbinder;

    @Override
    public void onSuccess(Response response) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(getlayoutId(), container, false);
        unbinder = ButterKnife.bind(this, v);
        //初始化事件和获取数据, 在此方法中获取数据不是懒加载模式
        initEventAndData();
        return v;
    }

    public abstract int getlayoutId();

    public abstract void initEventAndData();

    @Override
    public void loading(boolean b) {
        if (b) {
            if (zLoadingDialog == null) {
                zLoadingDialog = new ZLoadingDialog(getActivity());
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
    public void onDestroy() {
        super.onDestroy();
        zLoadingDialog = null;
        OkGo.getInstance().cancelTag(this);
        this.mSimpleImmersionProxy.onDestroy();
    }

    private SimpleImmersionProxy mSimpleImmersionProxy = new SimpleImmersionProxy(this);

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.mSimpleImmersionProxy.setUserVisibleHint(isVisibleToUser);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mSimpleImmersionProxy.onActivityCreated(savedInstanceState);
    }

    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.mSimpleImmersionProxy.onHiddenChanged(hidden);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mSimpleImmersionProxy.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean immersionBarEnabled() {
        return false;
    }

    @Override
    public void initImmersionBar() {

    }
}
