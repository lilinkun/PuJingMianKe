package com.lzy.okgo.adapter;

import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.policy.CachePolicy;
import com.lzy.okgo.cache.policy.DefaultCachePolicy;
import com.lzy.okgo.cache.policy.FirstCacheRequestPolicy;
import com.lzy.okgo.cache.policy.NoCachePolicy;
import com.lzy.okgo.cache.policy.NoneCacheRequestPolicy;
import com.lzy.okgo.cache.policy.RequestFailedCachePolicy;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.lzy.okgo.utils.HttpUtils;

public class CacheCall<T> implements Call<T> {

    private CachePolicy<T> policy = null;
    private final Request<T, ? extends Request> request;

    public CacheCall(Request<T, ? extends Request> request) {
        this.request = request;
        this.policy = preparePolicy();
    }

    @Override
    public Response<T> execute() {
        CacheEntity<T> cacheEntity = policy.prepareCache();
        return policy.requestSync(cacheEntity);
    }

    @Override
    public void execute(Callback<T> callback) {
        HttpUtils.checkNotNull(callback, "callback == null");

        CacheEntity<T> cacheEntity = policy.prepareCache();
        policy.requestAsync(cacheEntity, callback);
    }

    private CachePolicy<T> preparePolicy() {
        switch (request.getCacheMode()) {
            case DEFAULT:
                policy = new DefaultCachePolicy<>(request);
                break;
            case NO_CACHE:
                policy = new NoCachePolicy<>(request);
                break;
            case IF_NONE_CACHE_REQUEST:
                policy = new NoneCacheRequestPolicy<>(request);
                break;
            case FIRST_CACHE_THEN_REQUEST:
                policy = new FirstCacheRequestPolicy<>(request);
                break;
            case REQUEST_FAILED_READ_CACHE:
                policy = new RequestFailedCachePolicy<>(request);
                break;
        }
        if (request.getCachePolicy() != null) {
            policy = request.getCachePolicy();
        }
        HttpUtils.checkNotNull(policy, "policy == null");
        return policy;
    }

    @Override
    public boolean isExecuted() {
        return policy.isExecuted();
    }

    @Override
    public void cancel() {
        policy.cancel();
    }

    @Override
    public boolean isCanceled() {
        return policy.isCanceled();
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public Call<T> clone() {
        return new CacheCall<>(request);
    }

    public Request getRequest() {
        return request;
    }
}
