package cn.com.pujing.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.LoginoutBean;
import cn.com.pujing.entity.MyInfoBean;
import cn.com.pujing.presenter.ProfilePresenter;
import cn.com.pujing.util.ActivityUtil;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.FileUtils;
import cn.com.pujing.util.Methods;
import cn.com.pujing.util.PuJingUtils;
import cn.com.pujing.util.UToast;
import cn.com.pujing.util.UploadFile;
import cn.com.pujing.view.ProfileView;

/**
 * 我的个人信息列表
 */
public class ProfileActivity extends BaseActivity<ProfileView, ProfilePresenter> implements View.OnClickListener, ProfileView, UploadFile.UploadListener {

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
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.iv_profile_head:
            case R.id.tv_profile_modify_head:
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 110);
                break;
            case R.id.tv_exit:

                Methods.saveKeyValue(Constants.AVATAR, "", this);
                Methods.saveKeyValue(Constants.AUTHORIZATION, "", this);
                Intent intent1 = new Intent(this,LoginActivity.class);
                startActivity(intent1);
                ActivityUtil.finishAll();

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
                                tvPersonalBirthday.setText(year + "-" + String.format("%02d-%02d",(month+1),dayOfMonth));

                                mPresenter.modifyPersonalInfo("", "", tvPersonalBirthday.getText().toString(), "");
                            }
                        },
                        // 传入年份
                        c.get(Calendar.YEAR),
                        // 传入月份
                        c.get(Calendar.MONTH),
                        // 传入天数
                        c.get(Calendar.DAY_OF_MONTH)
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
                                UploadFile.UpLoadFile(ProfileActivity.this,filePath,"user_avatar",ProfileActivity.this);
                            }
                        }.start();
                    }
                }
                break;

            case 0x2211:

                mPresenter.getMyInfo();

                break;

            default:
                break;
        }
    }

    @Override
    public void onSuccess(Response response) {
        if (response != null) {

            if (response.body() instanceof LoginoutBean){
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
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    @Override
    public void modifyFail(String msg) {
        UToast.show(this,msg);
        tvPersonalBirthday.setText("");
    }

    @Override
    public void uploadHeadImg(MyInfoBean myInfoBean) {
        loading(false);
        Glide.with(ProfileActivity.this)
                .load(avatar)
                .apply(PuJingUtils.setGlideCircle(10))
                .into(headImageView);
        Methods.saveKeyValue(Constants.AVATAR, String.valueOf(avatar), this);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onUploadData(JSONObject jsonObject,String accessUrl) {

        avatar = accessUrl;

        Glide.with(ProfileActivity.this)
                .load(accessUrl)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(50)))
                .into(headImageView);

        mPresenter.modifyHeadImg(jsonObject);

    }

    @Override
    public void onFail(String msg) {
        loading(false);
        runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        UToast.show(ProfileActivity.this,msg);
                    }
                }
        );
    }
}
