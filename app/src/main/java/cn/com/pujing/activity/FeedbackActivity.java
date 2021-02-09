package cn.com.pujing.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.gyf.immersionbar.ImmersionBar;
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

import cn.com.pujing.util.Constants;
import cn.com.pujing.R;
import cn.com.pujing.TCloud.MySessionCredentialProvider;
import cn.com.pujing.util.Urls;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.datastructure.Attachment;
import cn.com.pujing.datastructure.FeedbackSave;
import cn.com.pujing.datastructure.GetFilePathKey;
import cn.com.pujing.util.FileUtils;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class FeedbackActivity extends BaseActivity implements View.OnClickListener {
    private RadioGroup radioGroup;
    private ImageView uploadImageView;
    private int checkedId;
    private EditText editText;
    private int id;

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void init() {

        ImmersionBar.with(this).statusBarColor("#ED6D0F").fitsSystemWindows(true).init();

        radioGroup = findViewById(R.id.rg);
        editText = findViewById(R.id.et_content);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_submit).setOnClickListener(this);

        uploadImageView = findViewById(R.id.iv_upload);
        uploadImageView.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();
        requestPermissions();
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
        params.put(Constants.MODENAME, "feedback");
        JSONObject jsonObject = new JSONObject(params);

        try {
            Response response = OkGo.post(Urls.GETFILEPATHKEY).upJson(jsonObject).execute();

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
                                Glide.with(FeedbackActivity.this)
                                        .load(data.prefixurl + data.key)
                                        .into(uploadImageView);

                                HashMap<String, String> params = new HashMap<>();
                                params.put(Constants.FILEEXT, suffix);
                                params.put(Constants.FILEPATH, data.key);
                                JSONObject jsonObject = new JSONObject(params);

                                OkGo.post(Urls.ATTACHMENT)
                                        .tag(this)
                                        .upJson(jsonObject)
                                        .execute(new JsonCallback<>(Attachment.class, FeedbackActivity.this));
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
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.iv_upload) {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, 110);
        } else if (id == R.id.tv_submit) {

            int checkedId = radioGroup.getCheckedRadioButtonId();
            int type = 0;
            if (checkedId == R.id.rb_opinion) {
                type = 1;
            } else if (checkedId == R.id.rb_opinion) {
                type = 2;
            } else if (checkedId == R.id.rb_opinion) {
                type = 3;
            } else if (checkedId == R.id.rb_opinion) {
                type = 4;
            }

            String content = editText.getText().toString();

            HashMap<String, String> params = new HashMap<>();
            params.put(Constants.CONTENT, content);
            params.put(Constants.PHOTO, String.valueOf(this.id));
            params.put(Constants.TYPE, String.valueOf(type));
            JSONObject jsonObject = new JSONObject(params);

            OkGo.post(Urls.FEEDBACKSAVE)
                    .tag(this)
                    .upJson(jsonObject)
                    .execute(new JsonCallback<>(FeedbackSave.class, FeedbackActivity.this));
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

    @Override
    public void onSuccess(com.lzy.okgo.model.Response response) {

        if (response != null) {

            if (response.body() instanceof Attachment) {
                Attachment attachment = (Attachment) response.body();
                Attachment.Data data = attachment.data;
                this.id = data.id;
            } else if (response.body() instanceof FeedbackSave) {
                FeedbackSave feedbackSave = (FeedbackSave) response.body();
                if (feedbackSave.data) {
                    Toast.makeText(this, "反馈成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
}
