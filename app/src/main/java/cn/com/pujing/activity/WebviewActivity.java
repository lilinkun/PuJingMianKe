package cn.com.pujing.activity;

import android.telecom.DisconnectCause;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.model.Response;

import cn.com.pujing.R;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.util.AndroidJavascriptInterface;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.Methods;
import cn.com.pujing.util.UToast;

public class WebviewActivity extends BaseActivity {
    private WebView webView;


    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();
        String url = getIntent().getStringExtra(Constants.URL);

        Log.i("OkGo", "" + url);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookies(null);
        cookieManager.flush();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(url, Constants.AUTHORIZATION + "=" + Methods.getValueByKey(Constants.AUTHORIZATION, this));

        webView = findViewById(R.id.wv);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.addJavascriptInterface(new AndroidJavascriptInterface(this), "Android");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                view.setVisibility(View.GONE);
                UToast.show(WebviewActivity.this,R.string.net_error_tip);
            }
        });
        webView.loadUrl(url);

    }

    public WebView getWebView() {
        return webView;
    }

    @Override
    public void onBackPressed() {

        if (webView != null) {

            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                setResult(RESULT_OK);
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onSuccess(Response response) {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
