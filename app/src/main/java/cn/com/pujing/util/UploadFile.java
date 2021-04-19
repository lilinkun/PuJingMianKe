package cn.com.pujing.util;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.OkGo;
import com.tencent.cos.xml.CosXmlService;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.transfer.COSXMLUploadTask;
import com.tencent.cos.xml.transfer.TransferConfig;
import com.tencent.cos.xml.transfer.TransferManager;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import cn.com.pujing.TCloud.MySessionCredentialProvider;
import cn.com.pujing.entity.GetFilePathKey;
import cn.com.pujing.http.PujingService;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

public class UploadFile {

    public static UploadListener uploadListener;

    //上传照片视频
    public static void UpLoadFile(Context context, String filePath, String istype,UploadListener lisener) {

        QCloudCredentialProvider myCredentialProvider = new MySessionCredentialProvider();

        // 存储桶所在地域简称，例如上海地区是 ap-guangzhou
//        String region = "ap-chengdu";
        String region = "ap-shanghai";
        // 创建 CosXmlServiceConfig 对象，根据需要修改默认的配置参数
        CosXmlServiceConfig serviceConfig = new CosXmlServiceConfig.Builder()
                .setRegion(region)
                .isHttps(true) // 使用 HTTPS 请求, 默认为 HTTP 请求
                .builder();
        // 初始化 COS Service，获取实例
        CosXmlService cosXmlService = new CosXmlService(context,
                serviceConfig, myCredentialProvider);

        // 初始化 TransferConfig，这里使用默认配置，如果需要定制，请参考 SDK 接口文档
        TransferConfig transferConfig = new TransferConfig.Builder().build();
        // 初始化 TransferManager
        TransferManager transferManager = new TransferManager(cosXmlService, transferConfig);

        HashMap<String, String> params = new HashMap<>();
        String suffix = filePath.substring(filePath.lastIndexOf(".") + 1);
        params.put(Constants.EXTNAME, suffix);
        params.put(Constants.MODENAME, "feedback");
        JSONObject jsonObject = new JSONObject(params);

        try {
            Response response = OkGo.post(PujingService.GETFILEPATHKEY).upJson(jsonObject).execute();

            if (response != null) {
                ResponseBody responseBody = response.body();
                JsonReader jsonReader = new JsonReader(responseBody.charStream());
                Gson gson = new Gson();

                GetFilePathKey getFilePathKey = gson.fromJson(jsonReader, GetFilePathKey.class);
                GetFilePathKey.Data data = getFilePathKey.data;
                //存储桶，格式：BucketName-APPID
                String bucket = data.bucket;
                //对象在存储桶中的位置标识符，即称对象键
                String cosPath = data.key;
                //本地文件的绝对路径
                String srcPath = filePath;

                //若存在初始化分块上传的 UploadId，则赋值对应的 uploadId 值用于续传；否则，赋值 null
                String uploadId = null;
                // 上传文件
                COSXMLUploadTask cosxmlUploadTask = transferManager.upload(bucket, cosPath, srcPath, uploadId);

                //设置返回结果回调
                cosxmlUploadTask.setCosXmlResultListener(new CosXmlResultListener() {
                    @Override
                    public void onSuccess(CosXmlRequest request, CosXmlResult result) {

//                        COSXMLUploadTask.COSXMLUploadTaskResult cOSXMLUploadTaskResult = (COSXMLUploadTask.COSXMLUploadTaskResult) result;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (istype.equals("feedback")) {

                                    HashMap<String, String> params = new HashMap<>();
                                    params.put(Constants.FILEEXT, suffix);
                                    params.put(Constants.FILEPATH, data.key);
                                    JSONObject jsonObject = new JSONObject(params);

                                    lisener.onUploadData(jsonObject,"");
                                }else {
                                    HashMap<String, String> params = new HashMap<>();
                                    params.put(Constants.AVATAR, result.accessUrl);
                                    JSONObject jsonObject = new JSONObject(params);
                                    lisener.onUploadData(jsonObject,result.accessUrl);
                                }
                            }
                        });
                    }

                    @Override
                    public void onFail(CosXmlRequest request, CosXmlClientException clientException, CosXmlServiceException serviceException) {
                        Log.i("OkGo", "onFail");
                        lisener.onFail("上传失败");

                        if (clientException != null) {
                            clientException.printStackTrace();
                            Log.i("OkGo", "onFail=" + clientException.toString());
                        } else {
                            serviceException.printStackTrace();
                            Log.i("OkGo", "onFail=" + serviceException.toString());
                        }
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static interface UploadListener{
        public void onUploadData(JSONObject jsonObject,String accessUrl);
        public void onFail(String msg);
    }


}
