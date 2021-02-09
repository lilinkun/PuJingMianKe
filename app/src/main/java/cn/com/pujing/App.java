package cn.com.pujing;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;

import okhttp3.OkHttpClient;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        OkGo.getInstance().init(this);
    }

    private void init() {
        HttpHeaders headers = new HttpHeaders();
        headers.put(Constants.AUTHORIZATION, Methods.getValueByKey(Constants.AUTHORIZATION, this));

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(java.util.logging.Level.INFO);
        builder.addInterceptor(loggingInterceptor);

        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);

        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())
                .setRetryCount(0)
                .addCommonHeaders(headers);
    }
}
