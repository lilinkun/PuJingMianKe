package com.lzy.okgo.cookie.store;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.lzy.okgo.cookie.SerializableCookie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.HttpUrl;


public class SPCookieStore implements CookieStore {

    private static final String COOKIE_PREFS = "okgo_cookie";           
    private static final String COOKIE_NAME_PREFIX = "cookie_";         


    private final Map<String, ConcurrentHashMap<String, Cookie>> cookies;
    private final SharedPreferences cookiePrefs;

    public SPCookieStore(Context context) {
        cookiePrefs = context.getSharedPreferences(COOKIE_PREFS, Context.MODE_PRIVATE);
        cookies = new HashMap<>();

        
        Map<String, ?> prefsMap = cookiePrefs.getAll();
        for (Map.Entry<String, ?> entry : prefsMap.entrySet()) {
            if ((entry.getValue()) != null && !entry.getKey().startsWith(COOKIE_NAME_PREFIX)) {
                
                String[] cookieNames = TextUtils.split((String) entry.getValue(), ",");
                for (String name : cookieNames) {
                    
                    String encodedCookie = cookiePrefs.getString(COOKIE_NAME_PREFIX + name, null);
                    if (encodedCookie != null) {
                        Cookie decodedCookie = SerializableCookie.decodeCookie(encodedCookie);
                        if (decodedCookie != null) {
                            if (!cookies.containsKey(entry.getKey())) {
                                cookies.put(entry.getKey(), new ConcurrentHashMap<String, Cookie>());
                            }
                            cookies.get(entry.getKey()).put(name, decodedCookie);
                        }
                    }
                }
            }
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
            saveCookie(url, cookie, getCookieToken(cookie));
        }
    }


    private void saveCookie(HttpUrl url, Cookie cookie, String cookieToken) {
        
        cookies.get(url.host()).put(cookieToken, cookie);
        
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        prefsWriter.putString(url.host(), TextUtils.join(",", cookies.get(url.host()).keySet()));
        prefsWriter.putString(COOKIE_NAME_PREFIX + cookieToken, SerializableCookie.encodeCookie(url.host(), cookie));
        prefsWriter.apply();
    }


    @Override
    public synchronized List<Cookie> loadCookie(HttpUrl url) {
        List<Cookie> ret = new ArrayList<>();
        if (!cookies.containsKey(url.host())) return ret;

        Collection<Cookie> urlCookies = cookies.get(url.host()).values();
        for (Cookie cookie : urlCookies) {
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
        
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        if (cookiePrefs.contains(COOKIE_NAME_PREFIX + cookieToken)) {
            prefsWriter.remove(COOKIE_NAME_PREFIX + cookieToken);
        }
        prefsWriter.putString(url.host(), TextUtils.join(",", cookies.get(url.host()).keySet()));
        prefsWriter.apply();
        return true;
    }

    @Override
    public synchronized boolean removeCookie(HttpUrl url) {
        if (!cookies.containsKey(url.host())) return false;

        
        ConcurrentHashMap<String, Cookie> urlCookie = cookies.remove(url.host());
        
        Set<String> cookieTokens = urlCookie.keySet();
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        for (String cookieToken : cookieTokens) {
            if (cookiePrefs.contains(COOKIE_NAME_PREFIX + cookieToken)) {
                prefsWriter.remove(COOKIE_NAME_PREFIX + cookieToken);
            }
        }
        prefsWriter.remove(url.host());
        prefsWriter.apply();

        return true;
    }

    @Override
    public synchronized boolean removeAllCookie() {
        
        cookies.clear();
        
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        prefsWriter.clear();
        prefsWriter.apply();
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
