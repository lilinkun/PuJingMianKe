package com.lzy.okgo.cache.policy;

import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Response;

public interface CachePolicy<T> {


    void onSuccess(Response<T> success);


    void onError(Response<T> error);


    boolean onAnalysisResponse(okhttp3.Call call, okhttp3.Response response);


    CacheEntity<T> prepareCache();


    okhttp3.Call prepareRawCall() throws Throwable;


    Response<T> requestSync(CacheEntity<T> cacheEntity);


    void requestAsync(CacheEntity<T> cacheEntity, Callback<T> callback);


    boolean isExecuted();


    void cancel();


    boolean isCanceled();
}
