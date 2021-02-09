package com.lzy.okgo.callback;

import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.lzy.okgo.utils.OkLogger;


public abstract class AbsCallback<T> implements Callback<T> {

    @Override
    public void onStart(Request<T, ? extends Request> request) {
    }

    @Override
    public void onCacheSuccess(Response<T> response) {
    }

    @Override
    public void onError(Response<T> response) {
        OkLogger.printStackTrace(response.getException());
    }

    @Override
    public void onFinish() {
    }

    @Override
    public void uploadProgress(Progress progress) {
    }

    @Override
    public void downloadProgress(Progress progress) {
    }
}
