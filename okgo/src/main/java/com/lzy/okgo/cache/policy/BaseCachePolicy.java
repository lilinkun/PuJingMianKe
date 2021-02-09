package com.lzy.okgo.cache.policy;

import android.graphics.Bitmap;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.db.CacheManager;
import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.lzy.okgo.utils.HeaderParser;
import com.lzy.okgo.utils.HttpUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Headers;

public abstract class BaseCachePolicy<T> implements CachePolicy<T> {

    protected Request<T, ? extends Request> request;
    protected volatile boolean canceled;
    protected volatile int currentRetryCount = 0;
    protected boolean executed;
    protected okhttp3.Call rawCall;
    protected Callback<T> mCallback;
    protected CacheEntity<T> cacheEntity;

    public BaseCachePolicy(Request<T, ? extends Request> request) {
        this.request = request;
    }

    @Override
    public boolean onAnalysisResponse(Call call, okhttp3.Response response) {
        return false;
    }

    @Override
    public CacheEntity<T> prepareCache() {
        
        if (request.getCacheKey() == null) {
            request.cacheKey(HttpUtils.createUrlFromParams(request.getBaseUrl(), request.getParams().urlParamsMap));
        }
        if (request.getCacheMode() == null) {
            request.cacheMode(CacheMode.NO_CACHE);
        }

        CacheMode cacheMode = request.getCacheMode();
        if (cacheMode != CacheMode.NO_CACHE) {
            
            cacheEntity = (CacheEntity<T>) CacheManager.getInstance().get(request.getCacheKey());
            HeaderParser.addCacheHeaders(request, cacheEntity, cacheMode);
            if (cacheEntity != null && cacheEntity.checkExpire(cacheMode, request.getCacheTime(), System.currentTimeMillis())) {
                cacheEntity.setExpire(true);
            }
        }

        if (cacheEntity == null || cacheEntity.isExpire() || cacheEntity.getData() == null || cacheEntity.getResponseHeaders() == null) {
            cacheEntity = null;
        }
        return cacheEntity;
    }

    @Override
    public synchronized okhttp3.Call prepareRawCall() throws Throwable {
        if (executed) throw HttpException.COMMON("Already executed!");
        executed = true;
        rawCall = request.getRawCall();
        if (canceled) rawCall.cancel();
        return rawCall;
    }

    protected Response<T> requestNetworkSync() {
        try {
            okhttp3.Response response = rawCall.execute();
            int responseCode = response.code();

            
            if (responseCode == 404 || responseCode >= 500) {
                return Response.error(false, rawCall, response, HttpException.NET_ERROR());
            }

            T body = request.getConverter().convertResponse(response);
            
            saveCache(response.headers(), body);
            return Response.success(false, body, rawCall, response);
        } catch (Throwable throwable) {
            if (throwable instanceof SocketTimeoutException && currentRetryCount < request.getRetryCount()) {
                currentRetryCount++;
                rawCall = request.getRawCall();
                if (canceled) {
                    rawCall.cancel();
                } else {
                    requestNetworkSync();
                }
            }
            return Response.error(false, rawCall, null, throwable);
        }
    }

    protected void requestNetworkAsync() {
        rawCall.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                if (e instanceof SocketTimeoutException && currentRetryCount < request.getRetryCount()) {
                    
                    currentRetryCount++;
                    rawCall = request.getRawCall();
                    if (canceled) {
                        rawCall.cancel();
                    } else {
                        rawCall.enqueue(this);
                    }
                } else {
                    if (!call.isCanceled()) {
                        Response<T> error = Response.error(false, call, null, e);
                        onError(error);
                    }
                }
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                int responseCode = response.code();

                
                if (responseCode == 404 || responseCode >= 500) {
                    Response<T> error = Response.error(false, call, response, HttpException.NET_ERROR());
                    onError(error);
                    return;
                }

                if (onAnalysisResponse(call, response)) return;

                try {
                    T body = request.getConverter().convertResponse(response);
                    
                    saveCache(response.headers(), body);
                    Response<T> success = Response.success(false, body, call, response);
                    onSuccess(success);
                } catch (Throwable throwable) {
                    Response<T> error = Response.error(false, call, response, throwable);
                    onError(error);
                }
            }
        });
    }


    private void saveCache(Headers headers, T data) {
        if (request.getCacheMode() == CacheMode.NO_CACHE) return;    
        if (data instanceof Bitmap) return;             

        CacheEntity<T> cache = HeaderParser.createCacheEntity(headers, data, request.getCacheMode(), request.getCacheKey());
        if (cache == null) {
            
            CacheManager.getInstance().remove(request.getCacheKey());
        } else {
            
            CacheManager.getInstance().replace(request.getCacheKey(), cache);
        }
    }

    protected void runOnUiThread(Runnable run) {
        OkGo.getInstance().getDelivery().post(run);
    }

    @Override
    public boolean isExecuted() {
        return executed;
    }

    @Override
    public void cancel() {
        canceled = true;
        if (rawCall != null) {
            rawCall.cancel();
        }
    }

    @Override
    public boolean isCanceled() {
        if (canceled) return true;
        synchronized (this) {
            return rawCall != null && rawCall.isCanceled();
        }
    }
}
