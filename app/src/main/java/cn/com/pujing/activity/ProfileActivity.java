package cn.com.pujing.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.TCloud.MySessionCredentialProvider;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.entity.EditMyInfo;
import cn.com.pujing.entity.GetFilePathKey;
import cn.com.pujing.entity.LoginoutBean;
import cn.com.pujing.entity.MyInfoBean;
import cn.com.pujing.presenter.ProfilePresenter;
import cn.com.pujing.util.ActivityUtil;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.FileUtils;
import cn.com.pujing.util.Methods;
import cn.com.pujing.util.PuJingUtils;
import cn.com.pujing.util.UToast;
import cn.com.pujing.util.Urls;
import cn.com.pujing.view.ProfileView;
import okhttp3.ResponseBody;

public class ProfileActivity extends BaseActivity<ProfileView, ProfilePresenter> implements View.OnClickListener ,ProfileView{

    private String avatar;
    private MyInfoBean myInfoBean;

    @BindView(R.id.iv_profile_head)
    ImageView headImageView;
    @BindView(R.id.tv_cellphone_number)
    TextView phoneTextView;
    @BindView(R.id.tv_nick_name_value)
    TextView tvNickName;
    @BindView(R.id.tv_personal_signature_value)
    TextView tvPersonalSignature;
    @BindView(R.id.tv_room_number)
    TextView tvRoomNumber;
    @BindView(R.id.tv_personal_birthday_value)
    TextView tvPersonalBirthday;

    @Override
    public int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this).transparentStatusBar().init();

        ActivityUtil.addActivity(this);

        String avatar = Methods.getValueByKey(Constants.AVATAR, this);
        if (!TextUtils.isEmpty(avatar)) {
            Glide.with(this)
                    .load(avatar)
                    .apply(PuJingUtils.setGlideCircle(10))
                    .into(headImageView);
        }


        myInfoBean = (MyInfoBean) getIntent().getSerializableExtra(Constants.KEY);
        initData();

        mPresenter.getMyInfo();
    }

    private void initData(){
        if (myInfoBean != null) {
            phoneTextView.setText(myInfoBean.getPhone());
            tvNickName.setText(myInfoBean.getNikeName());
            tvPersonalSignature.setText(myInfoBean.getSignature());
            tvRoomNumber.setText(myInfoBean.getRoomNumber());
            tvPersonalBirthday.setText(myInfoBean.getBirthday());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();
        requestPermissions();
    }

    @Override
    @OnClick({R.id.iv_back,R.id.iv_profile_head,R.id.tv_profile_modify_head,R.id.tv_exit,R.id.rl_personal_signature,R.id.rl_personal_nickname,
    R.id.rl_personal_birthday,R.id.rl_personal_room_number})
    public void onClick(View v) {
        int id = v.getId();

        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_profile_head:
            case R.id.tv_profile_modify_head:
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 110);
                break;
            case R.id.tv_exit:

                OkGo.post(Urls.LOGINOUT)
                        .tag(this)
                        .execute(new JsonCallback<>(LoginoutBean.class,this));
                break;
            case R.id.rl_personal_nickname:
                modifyInfo(1);
                break;
            case R.id.rl_personal_signature:
                modifyInfo(2);
                break;
            case R.id.rl_personal_birthday:
//                modifyInfo(3);
                Calendar c = Calendar.getInstance();
                Dialog dialog = new DatePickerDialog(
                        this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
//                                et.setText("您选择了：" + year + "年" + (month+1) + "月" + dayOfMonth + "日");
//                                tvModifyDate.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                                tvPersonalBirthday.setText(year + "-" + String.format("%02d-%02d",(month+1),dayOfMonth));

                                mPresenter.modifyPersonalInfo("", "", tvPersonalBirthday.getText().toString(), "");
                            }
                        },
                        c.get(Calendar.YEAR), // 传入年份
                        c.get(Calendar.MONTH), // 传入月份
                        c.get(Calendar.DAY_OF_MONTH) // 传入天数
                );

                dialog.show();
                break;
            case R.id.rl_personal_room_number:
//                modifyInfo(4);
                break;


            default:

                break;
        }

    }

    private void modifyInfo(int type){
        Intent intent = new Intent();
        intent.putExtra("modifyType",type);
        intent.putExtra("personalinfo",myInfoBean);
        intent.setClass(this,ModifyPersonalInfoActivity.class);
        startActivityForResult(intent,0x2211);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 110:
                if (data != null) {
                    Uri uri = data.getData();
                    String filePath = FileUtils.getFilePathByUri(this, uri);

                    loading(true);
                    if (!TextUtils.isEmpty(filePath)) {
                        new Thread() {
                            @Override
                            public void run() {
                                upload(filePath);
                            }
                        }.start();
                    }
                }
                break;

            case 0x2211:

                mPresenter.getMyInfo();

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
                                avatar = result.accessUrl;
//                                avatar = data.prefixurl + data.key;

                                Glide.with(ProfileActivity.this)
                                        .load(avatar)
                                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(50)))
                                        .into(headImageView);

                                HashMap<String, String> params = new HashMap<>();
                                params.put(Constants.AVATAR, result.accessUrl);
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
                loading(false);
                Glide.with(ProfileActivity.this)
                        .load(avatar)
                        .apply(PuJingUtils.setGlideCircle(10))
                        .into(headImageView);
                Methods.saveKeyValue(Constants.AVATAR, String.valueOf(avatar), this);
                setResult(RESULT_OK);
                finish();
            }else if (response.body() instanceof LoginoutBean){
                Methods.saveKeyValue(Constants.AVATAR, "", this);
                Methods.saveKeyValue(Constants.AUTHORIZATION, "", this);
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                ActivityUtil.finishAll();

            }
        }
    }

    @Override
    protected ProfilePresenter createPresenter() {
        return new ProfilePresenter();
    }

    @Override
    public void getMyInfoSuccess(MyInfoBean myInfoBean) {
        if (this.myInfoBean != myInfoBean) {
            this.myInfoBean = myInfoBean;
            initData();
        }
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void modifyPersonalInfoSuccess(MyInfoBean myInfoBean) {

    }

    @Override
    public void modifyFail(String msg) {
        UToast.show(this,msg);
        tvPersonalBirthday.setText("");
    }
}
