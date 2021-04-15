package cn.com.pujing.util;

import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import cn.com.pujing.activity.LoginActivity;
import cn.com.pujing.activity.WebviewActivity;

import static android.app.Activity.RESULT_OK;

public class AndroidJavascriptInterface {
    private WebviewActivity webviewActivity;

    public AndroidJavascriptInterface(WebviewActivity webviewActivity) {
        this.webviewActivity = webviewActivity;
    }

    @JavascriptInterface
    public void goToLogin() {
        if (webviewActivity != null) {
            webviewActivity.startActivity(new Intent(webviewActivity, LoginActivity.class));
            webviewActivity.finish();
        }
    }

    @JavascriptInterface
    public void goBack() {

        if (webviewActivity != null) {

            webviewActivity.runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    WebView webView = webviewActivity.getWebView();

                    if (webView != null) {
                        webviewActivity.setResult(RESULT_OK);
                        webviewActivity.finish();
                    }
                }
            });
        }
    }
}
