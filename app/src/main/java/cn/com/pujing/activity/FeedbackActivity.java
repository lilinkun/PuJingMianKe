package cn.com.pujing.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.OkGo;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.entity.Attachment;
import cn.com.pujing.entity.FeedbackBean;
import cn.com.pujing.entity.FeedbackSave;
import cn.com.pujing.presenter.FeedbackPresenter;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.FileUtils;
import cn.com.pujing.util.UploadFile;
import cn.com.pujing.util.Urls;
import cn.com.pujing.view.FeedbackView;
import cn.com.pujing.widget.FeedbackDialog;
import cn.com.pujing.widget.FeedbackPopup;

public class FeedbackActivity extends BaseActivity<FeedbackView, FeedbackPresenter> implements FeedbackView, View.OnClickListener, FeedbackPopup.FeedbackTypeClickListener {

    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.rl_feedback_type)
    RelativeLayout rlFeedbackType;
    @BindView(R.id.tv_feedback_type)
    TextView tvFeedbackType;
    @BindView(R.id.tv_length_limit)
    TextView tvLengthLimit;
    private int id = 0;
    private ImageView uploadImageView;
    private int checkedId;
    private int MAX_NUM = 500;
    private String filePath;
    List<FeedbackBean> feedbackBeans;
    String content;
    private String type = "1";

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

        mPresenter.giveFeedback();

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
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, 110);

        } else if (id == R.id.tv_submit) {

            content = etContent.getText().toString();

            if (content.trim().length() == 0){
                Toast.makeText(this,R.string.feedback_tip,Toast.LENGTH_LONG).show();
                return;
            }

            loading(true);

            if (filePath != null && filePath.trim().length() > 0){
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                UploadFile.UpLoadFile(FeedbackActivity.this,filePath,"feedback");
                            }
                        }
                ).start();
            }else {
                submitData();
            }



        } else if (id == R.id.rl_feedback_type){
            FeedbackPopup feedbackPopup = new FeedbackPopup(this,feedbackBeans);
            feedbackPopup.setListener(this);
            feedbackPopup.showAsDropDown(rlFeedbackType);
            }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 110:
                if (data != null) {
                    Uri uri = data.getData();
                    filePath = FileUtils.getFilePathByUri(this, uri);

                    Glide.with(this)
                            .load(filePath)
                            .into(uploadImageView);
                }

                break;

            default:

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
                submitData();
            } else if (response.body() instanceof FeedbackSave) {
                /*FeedbackSave feedbackSave = (FeedbackSave) response.body();
                loading(false);
                if (feedbackSave.data) {
                    FeedbackDialog feedbackDialog = new FeedbackDialog(this);
                    feedbackDialog.show();
                    feedbackDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            finish();
                        }
                    });
                }*/
            }
        }
    }

    @Override
    protected FeedbackPresenter createPresenter() {
        return new FeedbackPresenter();
    }


    /**
     * 提交数据
     */
    private void submitData(){

        /*HashMap<String, String> params = new HashMap<>();
        params.put(Constants.CONTENT, content);
        if (id != 0){
            params.put(Constants.PHOTO, String.valueOf(this.id));

        }
        params.put(Constants.TYPE, String.valueOf(type));
        JSONObject jsonObject = new JSONObject(params);

        OkGo.post(Urls.FEEDBACKSAVE)
                .tag(this)
                .upJson(jsonObject)
                .execute(new JsonCallback<>(FeedbackSave.class, FeedbackActivity.this));*/

        mPresenter.saveFeedBack(content,String.valueOf(this.id),String.valueOf(type));
    }

    @Override
    public void setItemValue(String value,int pos) {
        tvFeedbackType.setText(value);
        type = feedbackBeans.get(pos).value;
    }

    TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //只要编辑框内容有变化就会调用该方法，s为编辑框变化后的内容
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //编辑框内容变化之前会调用该方法，s为编辑框内容变化之前的内容
        }

        @Override
        public void afterTextChanged(Editable s) {
            //编辑框内容变化之后会调用该方法，s为编辑框内容变化后的内容
            if (s.length() > MAX_NUM) {
                s.delete(MAX_NUM, s.length());
            }
            int num = MAX_NUM - s.length();
            tvLengthLimit.setText( s.length() + "/500");
        }
    };

    @Override
    public void giveFeedbackType(List<FeedbackBean> feedbackBeans) {
        this.feedbackBeans = feedbackBeans;
        tvFeedbackType.setText(feedbackBeans.get(0).label);
        type = feedbackBeans.get(0).value;
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void saveFeedback(boolean b) {
        loading(false);
        if (b) {
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
