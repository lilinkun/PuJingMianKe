package cn.com.pujing.callback;

import com.lzy.okgo.model.Response;

import cn.com.pujing.entity.Base;

public interface RequestCallback {
    void onSuccess(Response response);
    void onFail(Base base);
    void loading(boolean b);
}
