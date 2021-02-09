package com.lzy.okgo.cache;

import android.content.ContentValues;
import android.database.Cursor;

import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.utils.IOUtils;

import java.io.Serializable;


public class CacheEntity<T> implements Serializable {
    private static final long serialVersionUID = -4337711009801627866L;

    public static final long CACHE_NEVER_EXPIRE = -1;


    public static final String KEY = "key";
    public static final String LOCAL_EXPIRE = "localExpire";
    public static final String HEAD = "head";
    public static final String DATA = "data";

    private String key;
    private long localExpire;
    private HttpHeaders responseHeaders;
    private T data;
    private boolean isExpire;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public HttpHeaders getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(HttpHeaders responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getLocalExpire() {
        return localExpire;
    }

    public void setLocalExpire(long localExpire) {
        this.localExpire = localExpire;
    }

    public boolean isExpire() {
        return isExpire;
    }

    public void setExpire(boolean expire) {
        isExpire = expire;
    }


    public boolean checkExpire(CacheMode cacheMode, long cacheTime, long baseTime) {

        if (cacheMode == CacheMode.DEFAULT) return getLocalExpire() < baseTime;
        if (cacheTime == CACHE_NEVER_EXPIRE) return false;
        return getLocalExpire() + cacheTime < baseTime;
    }

    public static <T> ContentValues getContentValues(CacheEntity<T> cacheEntity) {
        ContentValues values = new ContentValues();
        values.put(KEY, cacheEntity.getKey());
        values.put(LOCAL_EXPIRE, cacheEntity.getLocalExpire());
        values.put(HEAD, IOUtils.toByteArray(cacheEntity.getResponseHeaders()));
        values.put(DATA, IOUtils.toByteArray(cacheEntity.getData()));
        return values;
    }

    public static <T> CacheEntity<T> parseCursorToBean(Cursor cursor) {
        CacheEntity<T> cacheEntity = new CacheEntity<>();
        cacheEntity.setKey(cursor.getString(cursor.getColumnIndex(KEY)));
        cacheEntity.setLocalExpire(cursor.getLong(cursor.getColumnIndex(LOCAL_EXPIRE)));
        cacheEntity.setResponseHeaders((HttpHeaders) IOUtils.toObject(cursor.getBlob(cursor.getColumnIndex(HEAD))));

        cacheEntity.setData((T) IOUtils.toObject(cursor.getBlob(cursor.getColumnIndex(DATA))));
        return cacheEntity;
    }

    @Override
    public String toString() {
        return "CacheEntity{key='" + key + '\'' + //
                ", responseHeaders=" + responseHeaders + //
                ", data=" + data + //
                ", localExpire=" + localExpire + //
                '}';
    }
}
