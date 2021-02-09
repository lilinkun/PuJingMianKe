package com.lzy.okgo.callback;

import com.lzy.okgo.convert.FileConvert;

import java.io.File;

import okhttp3.Response;


public abstract class FileCallback extends AbsCallback<File> {

    private final FileConvert convert;    

    public FileCallback() {
        this(null);
    }

    public FileCallback(String destFileName) {
        this(null, destFileName);
    }

    public FileCallback(String destFileDir, String destFileName) {
        convert = new FileConvert(destFileDir, destFileName);
        convert.setCallback(this);
    }

    @Override
    public File convertResponse(Response response) throws Throwable {
        File file = convert.convertResponse(response);
        response.close();
        return file;
    }
}
