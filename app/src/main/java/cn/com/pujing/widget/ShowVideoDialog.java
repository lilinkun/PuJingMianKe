package cn.com.pujing.widget;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.adapter.ShowImagesAdapter;
import cn.com.pujing.http.PujingService;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * author : liguo
 * date : 2021/4/19 15:33
 * description :
 */
public class ShowVideoDialog extends Dialog {

    private View mView;
    private Context mContext;
    private VideoView mVvShow;
    private String mImgUrls;
    private LinearLayout llVv;

    public ShowVideoDialog(@NonNull Context context, String imgUrls) {
        super(context, R.style.transparentBgDialog);
        this.mContext = context;
        this.mImgUrls = imgUrls;
        initView();
        initData();
    }

    private void initView() {
        mView = View.inflate(mContext, R.layout.dialog_video, null);
        mVvShow = (VideoView) mView.findViewById(R.id.vv_show);
        llVv = (LinearLayout) mView.findViewById(R.id.ll_vv);

        llVv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mView);
        Window window = getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = 0;
        wl.height = WindowManager.LayoutParams.MATCH_PARENT;
        wl.width = WindowManager.LayoutParams.MATCH_PARENT;
        wl.gravity = Gravity.CENTER;
        window.setAttributes(wl);
    }

    private void initData() {
        //设置有进度条可以拖动快进
        MediaController localMediaController = new MediaController(mContext);
        mVvShow.setMediaController(localMediaController);

//        mVvShow.setVideoPath("http://172.18.7.21/basic-service/attachment/cos/cosKey/leaseInfo/2021-04-15/c8bc879fb96c46198281f2102510acfb.mp4");
        mVvShow.setVideoPath(PujingService.PREFIX + PujingService.IMG + mImgUrls);

        /**
         * 视频播放完成时回调
         */
        mVvShow.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.i("tag", "------------------视频播放完毕..........");
                /**播放完成时，再次循环播放*/
                mVvShow.start();
            }
        });
        /**
         * 视频播放发送错误时回调
         */
        mVvShow.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.i("tag", "---------------------视频播放失败...........");
                return false;
            }
        });

        /**开始播放视频
         * */
        mVvShow.start();

    }

}
