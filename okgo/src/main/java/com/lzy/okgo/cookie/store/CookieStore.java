package com.lzy.okgo.cookie.store;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;


public interface CookieStore {


    void saveCookie(HttpUrl url, List<Cookie> cookie);


    void saveCookie(HttpUrl url, Cookie cookie);


    List<Cookie> loadCookie(HttpUrl url);


    List<Cookie> getAllCookie();


    List<Cookie> getCookie(HttpUrl url);


    boolean removeCookie(HttpUrl url, Cookie cookie);


    boolean removeCookie(HttpUrl url);


    boolean removeAllCookie();
}
