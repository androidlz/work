package com.seventeenok.test.webview;

import android.os.Build;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.seventeenok.test.MyApplication;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CookieUtils {
    /**
     * 将cookie同步到WebView
     *
     * @param url WebView要加载的url
     * @return true 同步cookie成功，false同步cookie失败
     * @Author JPH
     */
    public static boolean syncCookie(String url) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(MyApplication.getContext());
        }
        CookieManager cookieManager = CookieManager.getInstance();

        Map<String, String> cookieMap = getCookieMap();
        for (Map.Entry<String, String> entry : cookieMap.entrySet()) {
            String cookieStr = makeCookie(entry.getKey(), entry.getValue());
            cookieManager.setCookie(url, cookieStr);
        }
        String newCookie = cookieManager.getCookie(url);
        return TextUtils.isEmpty(newCookie) ? false : true;
    }

    /**
     * 组装 Cookie 里需要的值
     *
     * @return
     */
    public static Map<String, String> getCookieMap() {
        Map<String, String> headerMap = new HashMap<>();
//        headerMap.put("userid", PhoneLiveApplication.USER_ID);
//        headerMap.put("token", PhoneLiveApplication.TOKEN);
        return headerMap;
    }

    /**
     * 拼接 Cookie 字符串
     *
     * @param key
     * @param value
     * @return
     */
    private static String makeCookie(String key, String value) {
        Date date = new Date();
        date.setTime(date.getTime() + 3 * 24 * 60 * 60 * 1000);  //3天过期
        return key + "=" + value + ";expires=" + date + ";path=/";
    }
}
