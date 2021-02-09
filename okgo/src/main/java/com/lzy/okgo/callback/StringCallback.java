package com.lzy.okgo.callback;

import com.lzy.okgo.convert.StringConvert;

import okhttp3.Response;


public abstract class StringCallback extends AbsCallback<String> {

    private final StringConvert convert;

    public StringCallback() {
        convert = new StringConvert();
    }

    @Override
    public String convertResponse(Response response) throws Throwable {
        String s = convert.convertResponse(response);
        response.close();
        return s;
    }
}
