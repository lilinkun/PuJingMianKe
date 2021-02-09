package cn.com.pujing.callback;

import com.lzy.okgo.model.Response;

public interface RequestCallback {
    void onSuccess(Response response);
    void loading(boolean b);
}
