package cn.com.pujing.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
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

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.Methods;
import cn.com.pujing.R;
import cn.com.pujing.TCloud.MySessionCredentialProvider;
import cn.com.pujing.util.Urls;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.entity.EditMyInfo;
import cn.com.pujing.entity.GetFilePathKey;
import cn.com.pujing.entity.MyInfo;
import cn.com.pujing.util.FileUtils;
import okhttp3.ResponseBody;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {

    private String avatar;
    private MyInfo.Data data;

    @BindView(R.id.iv_head)
    ImageView headImageView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    public void init() {
        ImmersionBar.with(this).statusBarColor("#ED6D0F").fitsSystemWindows(true).init();

        String avatar = Methods.getValueByKey(Constants.AVATAR, this);
        if (!TextUtils.isEmpty(avatar)) {
            Glide.with(this)
                    .load(avatar)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                    .into(headImageView);
        }

        data = (MyInfo.Data) getIntent().getSerializableExtra(Constants.KEY);
        if (data != null) {
            TextView textView = findViewById(R.id.tv_identity_authentication_value);
            textView.setText(data.auditStatus_label);

            TextView phoneTextView = findViewById(R.id.tv_cellphone_number_value);
            phoneTextView.setText(data.phone);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();
        requestPermissions();
    }

    @Override
    @OnClick({R.id.iv_back,R.id.iv_head})
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.iv_head) {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, 110);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 110:
                Uri uri = data.getData();
                String filePath = FileUtils.getFilePathByUri(this, uri);

                if (!TextUtils.isEmpty(filePath)) {
                    new Thread() {

                        @Override
                        public void run() {
                            upload(filePath);
                        }
                    }.start();
                }
                break;
        }
    }

    private void upload(String filePath) {
        QCloudCredentialProvider myCredentialProvider = new MySessionCredentialProvider();

        // 存储桶所在地域简称，例如广州地区是 ap-guangzhou
        String region = "ap-chengdu";
        // 创建 CosXmlServiceConfig 对象，根据需要修改默认的配置参数
        CosXmlServiceConfig serviceConfig = new CosXmlServiceConfig.Builder()
                .setRegion(region)
                .isHttps(true) // 使用 HTTPS 请求, 默认为 HTTP 请求
                .builder();
        // 初始化 COS Service，获取实例
        CosXmlService cosXmlService = new CosXmlService(this,
                serviceConfig, myCredentialProvider);

        // 初始化 TransferConfig，这里使用默认配置，如果需要定制，请参考 SDK 接口文档
        TransferConfig transferConfig = new TransferConfig.Builder().build();
        // 初始化 TransferManager
        TransferManager transferManager = new TransferManager(cosXmlService, transferConfig);

        HashMap<String, String> params = new HashMap<>();
        String suffix = filePath.substring(filePath.lastIndexOf(".") + 1);
        params.put(Constants.EXTNAME, suffix);
        params.put(Constants.MODENAME, "user_avatar");
        JSONObject jsonObject = new JSONObject(params);

        try {
            okhttp3.Response response = OkGo.post(Urls.GETFILEPATHKEY).upJson(jsonObject).execute();

            if (response != null) {
                ResponseBody responseBody = response.body();
                JsonReader jsonReader = new JsonReader(responseBody.charStream());
                Gson gson = new Gson();

                GetFilePathKey getFilePathKey = gson.fromJson(jsonReader, GetFilePathKey.class);
                GetFilePathKey.Data data = getFilePathKey.data;
                String bucket = data.bucket; //存储桶，格式：BucketName-APPID
                String cosPath = data.key; //对象在存储桶中的位置标识符，即称对象键
                String srcPath = filePath; //本地文件的绝对路径

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
                                avatar = data.prefixurl + data.key;

                                Glide.with(ProfileActivity.this)
                                        .load(avatar)
                                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(50)))
                                        .into(headImageView);

                                HashMap<String, String> params = new HashMap<>();
                                params.put(Constants.AVATAR, data.prefixurl + data.key);
                                JSONObject jsonObject = new JSONObject(params);

                                OkGo.post(Urls.EDITMYINFO)
                                        .tag(this)
                                        .upJson(jsonObject)
                                        .execute(new JsonCallback<>(EditMyInfo.class, ProfileActivity.this));
                            }
                        });
                    }

                    @Override
                    public void onFail(CosXmlRequest request, CosXmlClientException clientException, CosXmlServiceException serviceException) {
                        Log.i("OkGo", "onFail");
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

    @Override
    public void onSuccess(Response response) {
        if (response != null) {

            if (response.body() instanceof EditMyInfo) {
                Methods.saveKeyValue(Constants.AVATAR, String.valueOf(avatar), this);
                finish();
            }
        }
    }

}
