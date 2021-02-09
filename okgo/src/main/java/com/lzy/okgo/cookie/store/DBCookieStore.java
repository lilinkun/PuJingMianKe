package com.lzy.okgo.cookie.store;

import android.content.Context;

import com.lzy.okgo.cookie.SerializableCookie;
import com.lzy.okgo.db.CookieManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.HttpUrl;


public class DBCookieStore implements CookieStore {


    private final Map<String, ConcurrentHashMap<String, Cookie>> cookies;

    public DBCookieStore(Context context) {
        CookieManager.init(context);
        cookies = new HashMap<>();
        List<SerializableCookie> cookieList = CookieManager.getInstance().queryAll();
        for (SerializableCookie serializableCookie : cookieList) {
            if (!cookies.containsKey(serializableCookie.host)) {
                cookies.put(serializableCookie.host, new ConcurrentHashMap<String, Cookie>());
            }
            Cookie cookie = serializableCookie.getCookie();
            cookies.get(serializableCookie.host).put(getCookieToken(cookie), cookie);
        }
    }

    private String getCookieToken(Cookie cookie) {
        return cookie.name() + "@" + cookie.domain();
    }


    private static boolean isCookieExpired(Cookie cookie) {
        return cookie.expiresAt() < System.currentTimeMillis();
    }


    @Override
    public synchronized void saveCookie(HttpUrl url, List<Cookie> urlCookies) {
        for (Cookie cookie : urlCookies) {
            saveCookie(url, cookie);
        }
    }

    @Override
    public synchronized void saveCookie(HttpUrl url, Cookie cookie) {
        if (!cookies.containsKey(url.host())) {
            cookies.put(url.host(), new ConcurrentHashMap<String, Cookie>());
        }
        
        if (isCookieExpired(cookie)) {
            removeCookie(url, cookie);
        } else {
            
            cookies.get(url.host()).put(getCookieToken(cookie), cookie);
            
            SerializableCookie serializableCookie = new SerializableCookie(url.host(), cookie);
            CookieManager.getInstance().replace(serializableCookie);
        }
    }


    @Override
    public synchronized List<Cookie> loadCookie(HttpUrl url) {
        List<Cookie> ret = new ArrayList<>();
        if (!cookies.containsKey(url.host())) return ret;

        List<SerializableCookie> query = CookieManager.getInstance().query("host=?", new String[]{url.host()});
        for (SerializableCookie serializableCookie : query) {
            Cookie cookie = serializableCookie.getCookie();
            if (isCookieExpired(cookie)) {
                removeCookie(url, cookie);
            } else {
                ret.add(cookie);
            }
        }
        return ret;
    }


    @Override
    public synchronized boolean removeCookie(HttpUrl url, Cookie cookie) {
        if (!cookies.containsKey(url.host())) return false;
        String cookieToken = getCookieToken(cookie);
        if (!cookies.get(url.host()).containsKey(cookieToken)) return false;

        
        cookies.get(url.host()).remove(cookieToken);
        
        String whereClause = "host=? and name=? and domain=?";
        String[] whereArgs = {url.host(), cookie.name(), cookie.domain()};
        CookieManager.getInstance().delete(whereClause, whereArgs);
        return true;
    }

    @Override
    public synchronized boolean removeCookie(HttpUrl url) {
        if (!cookies.containsKey(url.host())) return false;

        
        cookies.remove(url.host());
        
        String whereClause = "host=?";
        String[] whereArgs = {url.host()};
        CookieManager.getInstance().delete(whereClause, whereArgs);
        return true;
    }

    @Override
    public synchronized boolean removeAllCookie() {
        
        cookies.clear();
        
        CookieManager.getInstance().deleteAll();
        return true;
    }


    @Override
    public synchronized List<Cookie> getAllCookie() {
        List<Cookie> ret = new ArrayList<>();
        for (String key : cookies.keySet()) {
            ret.addAll(cookies.get(key).values());
        }
        return ret;
    }

    @Override
    public synchronized List<Cookie> getCookie(HttpUrl url) {
        List<Cookie> ret = new ArrayList<>();
        Map<String, Cookie> mapCookie = cookies.get(url.host());
        if (mapCookie != null) ret.addAll(mapCookie.values());
        return ret;
    }
}
