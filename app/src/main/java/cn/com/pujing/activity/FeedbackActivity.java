package cn.com.pujing.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.OkGo;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.util.Constants;
import cn.com.pujing.R;
import cn.com.pujing.util.UploadFile;
import cn.com.pujing.util.Urls;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.entity.Attachment;
import cn.com.pujing.entity.FeedbackSave;
import cn.com.pujing.util.FileUtils;
import cn.com.pujing.view.FeedbackDialog;
import cn.com.pujing.view.FeedbackPopup;


public class FeedbackActivity extends BaseActivity implements View.OnClickListener, FeedbackPopup.FeedbackTypeClickListener {
    @BindView(R.id.rg)
    RadioGroup radioGroup;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.rl_feedback_type)
    RelativeLayout rlFeedbackType;
    @BindView(R.id.tv_feedback_type)
    TextView tvFeedbackType;
    @BindView(R.id.tv_length_limit)
    TextView tvLengthLimit;
    private int id;
    private ImageView uploadImageView;
    private int checkedId;
    private int MAX_NUM = 500;

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }


    @Override
    public void init() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        uploadImageView = findViewById(R.id.iv_upload);
        uploadImageView.setOnClickListener(this);

        etContent.addTextChangedListener(watcher);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();
        requestPermissions();
    }


    @Override
    @OnClick({R.id.iv_back,R.id.iv_upload,R.id.tv_submit,R.id.rl_feedback_type})
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.iv_upload) {
//            Intent intent = new Intent(Intent.ACTION_PICK, null);
//            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//            startActivityForResult(intent, 110);
            Intent mediaChooser = new Intent(Intent.ACTION_GET_CONTENT);
            //comma-separated MIME types
            mediaChooser.setType("video/*, image/*");
            startActivityForResult(mediaChooser, 110);

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

            String content = etContent.getText().toString();

            if (content.trim().length() == 0){
                Toast.makeText(this,R.string.feedback_tip,Toast.LENGTH_LONG).show();
                return;
            }

            HashMap<String, String> params = new HashMap<>();
            params.put(Constants.CONTENT, content);
            params.put(Constants.PHOTO, String.valueOf(this.id));
            params.put(Constants.TYPE, String.valueOf(type));
            JSONObject jsonObject = new JSONObject(params);

            OkGo.post(Urls.FEEDBACKSAVE)
                    .tag(this)
                    .upJson(jsonObject)
                    .execute(new JsonCallback<>(FeedbackSave.class, FeedbackActivity.this));
        } else if (id == R.id.rl_feedback_type){
            FeedbackPopup feedbackPopup = new FeedbackPopup(this);
            feedbackPopup.setListener(this);
            feedbackPopup.showAsDropDown(rlFeedbackType);
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
//                            upload(filePath);
                            UploadFile.UpLoadFile(FeedbackActivity.this,filePath,uploadImageView);
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
                    FeedbackDialog feedbackDialog = new FeedbackDialog(this);
                    feedbackDialog.show();
                    feedbackDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            finish();
                        }
                    });
                }
            }
        }
    }

    @Override
    public void setItemValue(String value) {
        tvFeedbackType.setText(value);
    }

    TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //只要编辑框内容有变化就会调用该方法，s为编辑框变化后的内容
            Log.i("onTextChanged", s.toString());
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //编辑框内容变化之前会调用该方法，s为编辑框内容变化之前的内容
            Log.i("beforeTextChanged", s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
            //编辑框内容变化之后会调用该方法，s为编辑框内容变化后的内容
            Log.i("afterTextChanged", s.toString());
            if (s.length() > MAX_NUM) {
                s.delete(MAX_NUM, s.length());
            }
            int num = MAX_NUM - s.length();
            tvLengthLimit.setText( s.length() + "/500");
        }
    };
}
