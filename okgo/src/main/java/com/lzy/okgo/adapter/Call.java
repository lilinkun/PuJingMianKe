package com.lzy.okgo.adapter;

import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

public interface Call<T> {

    Response<T> execute() throws Exception;


    void execute(Callback<T> callback);


    boolean isExecuted();


    void cancel();


    boolean isCanceled();

    Call<T> clone();

    Request getRequest();
}
