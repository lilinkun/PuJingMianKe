package cn.com.pujing.activity;

import android.view.View;

import com.gyf.immersionbar.ImmersionBar;

import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.presenter.MyCardPresenter;
import cn.com.pujing.view.MyCardView;

/**
 * author : liguo
 * date : 2021/3/29 11:04
 * description :我的卡包
 */
public class MyCardActivity extends BaseActivity<MyCardView, MyCardPresenter> implements MyCardView{

    @Override
    public int getLayoutId() {
        return R.layout.activity_mycard;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this)
                .statusBarColor(R.color.main_color)
                .fitsSystemWindows(true)
                .init();
    }

    @Override
    protected MyCardPresenter createPresenter() {
        return new MyCardPresenter();
    }

    @OnClick({R.id.iv_card_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_card_back:
                    finish();
                break;

            default:

                break;
        }
    }
}
