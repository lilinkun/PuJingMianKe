package cn.com.pujing.TCloud;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.OkGo;
import com.tencent.qcloud.core.auth.BasicLifecycleCredentialProvider;
import com.tencent.qcloud.core.auth.QCloudLifecycleCredentials;
import com.tencent.qcloud.core.auth.SessionQCloudCredentials;
import com.tencent.qcloud.core.common.QCloudClientException;

import cn.com.pujing.util.Urls;
import cn.com.pujing.datastructure.GetTencentKey;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MySessionCredentialProvider extends BasicLifecycleCredentialProvider {

    @Override
    protected QCloudLifecycleCredentials fetchNewCredentials() throws QCloudClientException {
        try {
            // 首先从您的临时密钥服务器获取包含了密钥信息的响应
            Response response = OkGo.get(Urls.GETTENCENTKEY).execute();

            if (response != null) {
                // 然后解析响应，获取临时密钥信息
                ResponseBody responseBody = response.body();

                if (responseBody != null) {
                    JsonReader jsonReader = new JsonReader(responseBody.charStream());
                    Gson gson = new Gson();
                    GetTencentKey getTencentKey = gson.fromJson(jsonReader, GetTencentKey.class);
                    GetTencentKey.Data data = getTencentKey.data;
                    GetTencentKey.Data.Credentials credentials = data.credentials;
                    String tmpSecretId = credentials.tmpSecretId; // 临时密钥 SecretId
                    String tmpSecretKey = credentials.tmpSecretKey; // 临时密钥 SecretKey
                    String sessionToken = credentials.sessionToken; // 临时密钥 Token
                    long expiredTime = Long.parseLong(data.expiredTime);//临时密钥有效截止时间戳，单位是秒
                    //建议返回服务器时间作为签名的开始时间，避免由于用户手机本地时间偏差过大导致请求过期
                    // 返回服务器时间作为签名的起始时间
                    long startTime = Long.parseLong(data.startTime); //临时密钥有效起始时间，单位是秒
                    // 最后返回临时密钥信息对象
                    return new SessionQCloudCredentials(tmpSecretId, tmpSecretKey, sessionToken, startTime, expiredTime);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
