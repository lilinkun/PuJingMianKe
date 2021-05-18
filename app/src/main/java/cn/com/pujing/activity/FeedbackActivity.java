package cn.com.pujing.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gyf.immersionbar.ImmersionBar;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.adapter.FeedBackAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.AttachmentBean;
import cn.com.pujing.entity.FeedbackBean;
import cn.com.pujing.presenter.FeedbackPresenter;
import cn.com.pujing.util.FileUtils;
import cn.com.pujing.util.UToast;
import cn.com.pujing.util.UploadFile;
import cn.com.pujing.view.FeedbackView;
import cn.com.pujing.widget.FeedbackDialog;
import cn.com.pujing.widget.FeedbackPopup;
import cn.com.pujing.widget.ShowImagesDialog;
import cn.com.pujing.widget.ShowVideoDialog;

public class FeedbackActivity extends BaseActivity<FeedbackView, FeedbackPresenter> implements FeedbackView, View.OnClickListener, FeedbackPopup.FeedbackTypeClickListener, UploadFile.UploadListener, FeedBackAdapter.FeedbackOnclickListener {

    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.rl_feedback_type)
    RelativeLayout rlFeedbackType;
    @BindView(R.id.tv_feedback_type)
    TextView tvFeedbackType;
    @BindView(R.id.tv_length_limit)
    TextView tvLengthLimit;
    @BindView(R.id.rb_feedback)
    RadioButton rbFeedback;
    @BindView(R.id.rb_complaint)
    RadioButton rbComplaint;
    @BindView(R.id.rb_opinion)
    RadioButton rbOpinion;
    @BindView(R.id.rb_praise)
    RadioButton rbPraise;
    @BindView(R.id.rg_feedback_type)
    RadioGroup rgFeedbackType;
    @BindView(R.id.iv_video)
    ImageView ivVideo;
    @BindView(R.id.rv_feedback_pic)
    RecyclerView rvFeedbackPic;

    private String id;
    private ImageView uploadImageView;
    private int checkedId;
    private int MAX_NUM = 500;
    private String filePath;
    List<FeedbackBean> feedbackBeans;
    String content;
    private String type = "1";
    private ArrayList<String> picStrs = new ArrayList<>();
    private FeedBackAdapter feedBackAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }


    @Override
    public void initView() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        uploadImageView = findViewById(R.id.iv_upload);
        uploadImageView.setOnClickListener(this);

        etContent.addTextChangedListener(watcher);

        mPresenter.giveFeedback();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);

        picStrs.add("1");

        feedBackAdapter = new FeedBackAdapter(R.layout.adapter_feedback,picStrs,this);

        rvFeedbackPic.setLayoutManager(gridLayoutManager);

        rvFeedbackPic.setAdapter(feedBackAdapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();
        requestPermissions();
    }


    @Override
    @OnClick({R.id.iv_back,R.id.iv_upload,R.id.tv_submit,R.id.rl_feedback_type,R.id.tv_upload_image,R.id.tv_my_feedback})
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.iv_upload) {
            if (filePath != null && filePath.trim().length() > 0){
                List<String> strings = new ArrayList<>();
                strings.add(filePath);
                if (filePath.endsWith("mp4")){
                    new ShowVideoDialog(this,filePath,1).show();
                }else {
                    new ShowImagesDialog(this, strings, 0, 1).show();
                }
            }else {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
//            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/* video/*");
                startActivityForResult(intent, 110);
            }
        } else if (id == R.id.tv_upload_image) {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
//            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/* video/*");
            startActivityForResult(intent, 110);

        } else if (id == R.id.tv_submit) {

            content = etContent.getText().toString();

            if (content.trim().length() == 0){
                Toast.makeText(this,R.string.feedback_tip,Toast.LENGTH_LONG).show();
                return;
            }

            loading(true);

            if (picStrs != null && picStrs.size() > 0 && !picStrs.get(0).equals("1")){
                int picLength = 0;
                if (picStrs.size() > 1){
                    picLength = picStrs.size() - 1;
                }else {
                    picLength = picStrs.size();
                }
                for (int i = 0;i < picLength; i++) {
                    String picPath = picStrs.get(i);
                    new Thread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    UploadFile.UpLoadFile(FeedbackActivity.this, picPath, "feedback", FeedbackActivity.this);
                                }
                            }
                    ).start();
                }
            }else {
                submitData();
            }

        } else if (id == R.id.rl_feedback_type){
            FeedbackPopup feedbackPopup = new FeedbackPopup(this,feedbackBeans);
            feedbackPopup.setListener(this);
            feedbackPopup.showAsDropDown(rlFeedbackType);
        }else if (id == R.id.tv_my_feedback){
            Intent intent = new Intent();
            intent.setClass(this,MyFeedBackActivity.class);
            startActivity(intent);
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

//                    Glide.with(this).load(filePath).into(uploadImageView);
                    picStrs.set(picStrs.size()-1,filePath);
                    if (filePath.endsWith(".mp4")){

                    }else {
                        if (picStrs.size() < 9) {
                            picStrs.add("1");
                        }
                    }

                    feedBackAdapter.setList(picStrs);

                }

                break;

            default:

                break;
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

        mPresenter.saveFeedBack(content,id,String.valueOf(findViewById(rgFeedbackType.getCheckedRadioButtonId()).getTag()));
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
        loading(false);
        UToast.show(this,msg);
    }

    @Override
    public void saveFeedback(boolean b) {
        loading(false);
        if (b) {
            FeedbackDialog feedbackDialog = new FeedbackDialog(this,0,"");
            feedbackDialog.show();
            feedbackDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    finish();
                }
            });
        }
    }

    @Override
    public void saveFeedFile(AttachmentBean attachmentBean) {
        if (id == null || id.equals("")) {
            id = attachmentBean.id + "";
        }else {
            id = id + "," + attachmentBean.id;
        }

        if (picStrs.size() == 1) {
            if (id.split(",").length == picStrs.size()) {
                submitData();
            }
        }else {
            if (id.split(",").length == picStrs.size()-1) {
                submitData();
            }
        }
    }


    @Override
    public void onUploadData(JSONObject jsonObject, String accessUrl) {
        mPresenter.saveFeedFile(jsonObject);
    }

    @Override
    public void onFail(String msg) {
        loading(false);
        runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        UToast.show(FeedbackActivity.this,msg);
                    }
                }
        );
    }

    @Override
    public void exitItem(int position) {
        picStrs.remove(position);
        if (picStrs.size() == 0){
            picStrs.add("1");
        }
        feedBackAdapter.setList(picStrs);
    }

    @Override
    public void feedbackUpload() {

        if (picStrs.size() == 1) {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
//            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/* video/*");
            startActivityForResult(intent, 110);
        }else {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, 110);
        }
    }

    @Override
    public void feedbackPic(int position) {
        if (picStrs.get(position).endsWith("mp4")){
            new ShowVideoDialog(this,picStrs.get(position),1).show();
        }else {
            ArrayList<String> strings = picStrs;
            if (strings.size() != 9) {
                strings.remove(strings.size() - 1);
            }
            new ShowImagesDialog(this, strings, position, 1).show();
        }
    }
}
