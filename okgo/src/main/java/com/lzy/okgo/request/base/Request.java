
package com.lzy.okgo.request.base;

import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.adapter.AdapterParam;
import com.lzy.okgo.adapter.CacheCall;
import com.lzy.okgo.adapter.Call;
import com.lzy.okgo.adapter.CallAdapter;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cache.policy.CachePolicy;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.convert.Converter;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.utils.HttpUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;


public abstract class Request<T, R extends Request> implements Serializable {
    private static final long serialVersionUID = -7174118653689916252L;

    protected String url;
    protected String baseUrl;
    protected transient OkHttpClient client;
    protected transient Object tag;
    protected int retryCount;
    protected CacheMode cacheMode;
    protected String cacheKey;
    protected long cacheTime;                           
    protected HttpParams params = new HttpParams();     
    protected HttpHeaders headers = new HttpHeaders();  

    protected transient okhttp3.Request mRequest;
    protected transient Call<T> call;
    protected transient Callback<T> callback;
    protected transient Converter<T> converter;
    protected transient CachePolicy<T> cachePolicy;
    protected transient ProgressRequestBody.UploadInterceptor uploadInterceptor;

    public Request(String url) {
        this.url = url;
        baseUrl = url;
        OkGo go = OkGo.getInstance();
        
        String acceptLanguage = HttpHeaders.getAcceptLanguage();
        if (!TextUtils.isEmpty(acceptLanguage)) headers(HttpHeaders.HEAD_KEY_ACCEPT_LANGUAGE, acceptLanguage);
        
        String userAgent = HttpHeaders.getUserAgent();
        if (!TextUtils.isEmpty(userAgent)) headers(HttpHeaders.HEAD_KEY_USER_AGENT, userAgent);
        
        if (go.getCommonParams() != null) params(go.getCommonParams());
        if (go.getCommonHeaders() != null) headers(go.getCommonHeaders());
        
        retryCount = go.getRetryCount();
        cacheMode = go.getCacheMode();
        cacheTime = go.getCacheTime();
    }

    @SuppressWarnings("unchecked")
    public R tag(Object tag) {
        this.tag = tag;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R retryCount(int retryCount) {
        if (retryCount < 0) throw new IllegalArgumentException("retryCount must > 0");
        this.retryCount = retryCount;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R client(OkHttpClient client) {
        HttpUtils.checkNotNull(client, "OkHttpClient == null");

        this.client = client;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R call(Call<T> call) {
        HttpUtils.checkNotNull(call, "call == null");

        this.call = call;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R converter(Converter<T> converter) {
        HttpUtils.checkNotNull(converter, "converter == null");

        this.converter = converter;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R cacheMode(CacheMode cacheMode) {
        this.cacheMode = cacheMode;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R cachePolicy(CachePolicy<T> cachePolicy) {
        HttpUtils.checkNotNull(cachePolicy, "cachePolicy == null");

        this.cachePolicy = cachePolicy;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R cacheKey(String cacheKey) {
        HttpUtils.checkNotNull(cacheKey, "cacheKey == null");

        this.cacheKey = cacheKey;
        return (R) this;
    }


    @SuppressWarnings("unchecked")
    public R cacheTime(long cacheTime) {
        if (cacheTime <= -1) cacheTime = CacheEntity.CACHE_NEVER_EXPIRE;
        this.cacheTime = cacheTime;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R headers(HttpHeaders headers) {
        this.headers.put(headers);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R headers(String key, String value) {
        headers.put(key, value);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R removeHeader(String key) {
        headers.remove(key);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R removeAllHeaders() {
        headers.clear();
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(HttpParams params) {
        this.params.put(params);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(Map<String, String> params, boolean... isReplace) {
        this.params.put(params, isReplace);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(String key, String value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(String key, int value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(String key, float value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(String key, double value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(String key, long value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(String key, char value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(String key, boolean value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R addUrlParams(String key, List<String> values) {
        params.putUrlParams(key, values);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R removeParam(String key) {
        params.remove(key);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R removeAllParams() {
        params.clear();
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R uploadInterceptor(ProgressRequestBody.UploadInterceptor uploadInterceptor) {
        this.uploadInterceptor = uploadInterceptor;
        return (R) this;
    }


    public String getUrlParam(String key) {
        List<String> values = params.urlParamsMap.get(key);
        if (values != null && values.size() > 0) return values.get(0);
        return null;
    }


    public HttpParams.FileWrapper getFileParam(String key) {
        List<HttpParams.FileWrapper> values = params.fileParamsMap.get(key);
        if (values != null && values.size() > 0) return values.get(0);
        return null;
    }

    public HttpParams getParams() {
        return params;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public String getUrl() {
        return url;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public Object getTag() {
        return tag;
    }

    public CacheMode getCacheMode() {
        return cacheMode;
    }

    public CachePolicy<T> getCachePolicy() {
        return cachePolicy;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public long getCacheTime() {
        return cacheTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public okhttp3.Request getRequest() {
        return mRequest;
    }

    public void setCallback(Callback<T> callback) {
        this.callback = callback;
    }

    public Converter<T> getConverter() {
        
        if (converter == null) converter = callback;
        HttpUtils.checkNotNull(converter, "converter == null, do you forget to call Request#converter(Converter<T>) ?");
        return converter;
    }

    public abstract HttpMethod getMethod();


    protected abstract RequestBody generateRequestBody();


    public abstract okhttp3.Request generateRequest(RequestBody requestBody);


    public okhttp3.Call getRawCall() {
        
        RequestBody requestBody = generateRequestBody();
        if (requestBody != null) {
            ProgressRequestBody<T> progressRequestBody = new ProgressRequestBody<>(requestBody, callback);
            progressRequestBody.setInterceptor(uploadInterceptor);
            mRequest = generateRequest(progressRequestBody);
        } else {
            mRequest = generateRequest(null);
        }
        if (client == null) client = OkGo.getInstance().getOkHttpClient();
        return client.newCall(mRequest);
    }


    public Call<T> adapt() {
        if (call == null) {
            return new CacheCall<>(this);
        } else {
            return call;
        }
    }


    public <E> E adapt(CallAdapter<T, E> adapter) {
        Call<T> innerCall = call;
        if (innerCall == null) {
            innerCall = new CacheCall<>(this);
        }
        return adapter.adapt(innerCall, null);
    }


    public <E> E adapt(AdapterParam param, CallAdapter<T, E> adapter) {
        Call<T> innerCall = call;
        if (innerCall == null) {
            innerCall = new CacheCall<>(this);
        }
        return adapter.adapt(innerCall, param);
    }


    public Response execute() throws IOException {
        return getRawCall().execute();
    }


    public void execute(Callback<T> callback) {
        HttpUtils.checkNotNull(callback, "callback == null");

        this.callback = callback;
        Call<T> call = adapt();
        call.execute(callback);
    }
}
