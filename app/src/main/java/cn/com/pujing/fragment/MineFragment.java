package cn.com.pujing.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.activity.MyMsgActivity;
import cn.com.pujing.entity.MyInfoBean;
import cn.com.pujing.presenter.MinePresenter;
import cn.com.pujing.util.Constants;
import cn.com.pujing.util.Methods;
import cn.com.pujing.R;
import cn.com.pujing.util.PuJingUtils;
import cn.com.pujing.util.UToast;
import cn.com.pujing.activity.MyCalendarActivity;
import cn.com.pujing.activity.ProfileActivity;
import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.entity.MyInfo;
import cn.com.pujing.view.MineView;

public class MineFragment extends BaseFragment<MineView, MinePresenter> implements View.OnClickListener,MineView {
    private String avatar;
    private MyInfo.Data data;
    private MyInfoBean myInfoBean;

    @BindView(R.id.iv_head)
    ImageView headImageView;
    @BindView(R.id.tv_name)
    TextView tvName;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initEventAndData() {

        avatar = Methods.getValueByKey(Constants.AVATAR, getContext());
        if (!TextUtils.isEmpty(avatar)) {
            Glide.with(getContext())
                    .load(avatar)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(50)))
                    .into(headImageView);
        }

        mPresenter.getMyInfo();

        /*OkGo.get(Urls.MYINFO)
                .tag(this)
                .execute(new JsonCallback<>(MyInfo.class, this));*/
    }

    @Override
    public void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(avatar)) {
            String avatar = Methods.getValueByKey(Constants.AVATAR, getContext());
            if (!TextUtils.isEmpty(avatar)) {
                avatar = avatar;
                Glide.with(getContext())
                        .load(avatar)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(50)))
                        .into(headImageView);
            }
        }
    }

    @Override
    @OnClick({R.id.tv_name,R.id.iv_next,R.id.tv_my_calendar,R.id.my_order,R.id.my_msg,R.id.my_bill,R.id.iv_head})
    public void onClick(View v) {

        if (v.getId() == R.id.iv_head || v.getId() == R.id.tv_name || v.getId() == R.id.iv_next) {

            if (!PuJingUtils.isFastDoubleClick()){
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.KEY, myInfoBean);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        } else if (v.getId() == R.id.tv_my_calendar) {

            if (!PuJingUtils.isFastDoubleClick()) {
                startActivity(new Intent(getContext(), MyCalendarActivity.class));
            }
        } else if (v.getId() == R.id.my_order) {
//            startActivity(new Intent(getContext(), MyOrderActivity.class));

            if (!PuJingUtils.isFastDoubleClick()) {
                Toast.makeText(getContext(), R.string.comming_soon, Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.my_msg) {

            if (!PuJingUtils.isFastDoubleClick()) {
                startActivity(new Intent(getContext(), MyMsgActivity.class));
            }
//            Toast.makeText(getContext(), R.string.comming_soon, Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.my_bill) {
//            startActivity(new Intent(getContext(), MyBillActivity.class));

            if (!PuJingUtils.isFastDoubleClick()) {
                Toast.makeText(getContext(), R.string.comming_soon, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSuccess(Response response) {

        if (response != null) {

            if (response.body() instanceof MyInfo) {
                MyInfo myInfo = (MyInfo) response.body();
                MyInfo.Data data = myInfo.data;
                tvName.setText(data.username);

                if (!TextUtils.isEmpty(data.avatar)) {
                    Glide.with(getContext())
                            .load(data.avatar)
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(50)))
                            .into(headImageView);
                    Methods.saveKeyValue(Constants.AVATAR, data.avatar, getContext());
                }
                this.data = data;
            }
        }
    }

    @Override
    public boolean immersionBarEnabled() {
        return true;
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).init();
    }

    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter();
    }

    @Override
    public void getMyInfoSuccess(MyInfoBean myInfoBean) {
        tvName.setText(myInfoBean.getUsername());

        if (!TextUtils.isEmpty(myInfoBean.getAvatar())) {
            Glide.with(getContext())
                    .load(myInfoBean.getAvatar())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(50)))
                    .into(headImageView);
            Methods.saveKeyValue(Constants.AVATAR, myInfoBean.getAvatar(), getContext());
        }
        this.myInfoBean = myInfoBean;
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(getActivity(),msg);
    }
}
