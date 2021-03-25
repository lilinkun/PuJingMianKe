package cn.com.pujing.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.model.Response;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.util.Methods;
import cn.com.pujing.R;
import cn.com.pujing.base.BaseActivity;

public class SplashActivity extends BaseActivity {
    private static final long DURATION = 3000;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_splash);

        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true)
                .init();

        ImageView alphaAnimatorImageView = findViewById(R.id.iv);
        ObjectAnimator alphaObjectAnimator = ObjectAnimator.ofFloat(alphaAnimatorImageView, "alpha", 1f, 0.3f);
        alphaObjectAnimator.setDuration(DURATION);
        alphaObjectAnimator.start();
        alphaObjectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if (Methods.logined(SplashActivity.this)) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public void onSuccess(Response response) {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
