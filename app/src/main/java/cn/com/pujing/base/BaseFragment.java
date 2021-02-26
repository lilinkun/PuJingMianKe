package cn.com.pujing.base;

import android.app.ProgressDialog;
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
import cn.com.pujing.entity.Base;

public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment implements RequestCallback, SimpleImmersionOwner {
    public ZLoadingDialog zLoadingDialog;

    public Unbinder unbinder;

    protected T mPresenter;

    @Override
    public void onSuccess(Response response) {

    }

    @Override
    public void onFail(Base base) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(getlayoutId(), container, false);
        unbinder = ButterKnife.bind(this, v);

        //判断是否使用MVP模式
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);//因为之后所有的子类都要实现对应的View接口
        }
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

    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract T createPresenter();
}
