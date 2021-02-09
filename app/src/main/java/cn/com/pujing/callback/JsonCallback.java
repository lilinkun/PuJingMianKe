package cn.com.pujing.callback;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import cn.com.pujing.datastructure.Base;
import okhttp3.ResponseBody;

public class JsonCallback<T> extends AbsCallback {
    private Class<T> clazz;
    private RequestCallback requestCallback;

    public JsonCallback(Class<T> clazz) {
        this.clazz = clazz;
    }

    public JsonCallback(Class<T> clazz, RequestCallback requestCallback) {
        this.clazz = clazz;
        this.requestCallback = requestCallback;
    }

    @Override
    public void onStart(Request request) {
        super.onStart(request);

        if (OkGo.getInstance().getOkHttpClient().dispatcher().runningCallsCount() == 0 && requestCallback != null) {
            requestCallback.loading(true);
        }
    }

    @Override
    public Object convertResponse(okhttp3.Response response) throws Throwable {

        if (response != null) {
            ResponseBody responseBody = response.body();

            if (responseBody == null) {
                return null;
            }
            JsonReader jsonReader = new JsonReader(responseBody.charStream());
            Gson gson = new Gson();
            T t = gson.fromJson(jsonReader, clazz);
            Base base = (Base) t;

            if (base != null) {
                if (base.code == 0) {
                    return t;
                } else{

                }
            }
        }
        return null;
    }

    @Override
    public void onSuccess(Response response) {
        if (requestCallback != null) {
            requestCallback.onSuccess(response);
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
        if (OkGo.getInstance().getOkHttpClient().dispatcher().runningCallsCount() == 0 && requestCallback != null) {
            requestCallback.loading(false);
        }
    }

    @Override
    public void onError(Response response) {
        super.onError(response);
        if (requestCallback != null) {
            requestCallback.loading(false);
        }
    }
}
