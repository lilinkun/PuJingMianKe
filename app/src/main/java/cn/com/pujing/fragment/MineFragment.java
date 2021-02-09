package cn.com.pujing.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import cn.com.pujing.Constants;
import cn.com.pujing.Methods;
import cn.com.pujing.R;
import cn.com.pujing.Urls;
import cn.com.pujing.activity.MyCalendarActivity;
import cn.com.pujing.activity.ProfileActivity;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.datastructure.MyInfo;

public class MineFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private String avatar;
    private ImageView headImageView;
    private MyInfo.Data data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_mine, null);
            init(view);
        }
        return view;
    }

    private void init(View view) {
        headImageView = view.findViewById(R.id.iv_head);
        avatar = Methods.getValueByKey(Constants.AVATAR, getContext());
        if (!TextUtils.isEmpty(avatar)) {
            Glide.with(getContext())
                    .load(avatar)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(50)))
                    .into(headImageView);
        }

        headImageView.setOnClickListener(this);
        view.findViewById(R.id.tv_name).setOnClickListener(this);
        view.findViewById(R.id.iv_next).setOnClickListener(this);
        view.findViewById(R.id.tv_my_calendar).setOnClickListener(this);
        view.findViewById(R.id.my_order).setOnClickListener(this);
        view.findViewById(R.id.my_msg).setOnClickListener(this);
        view.findViewById(R.id.my_bill).setOnClickListener(this);

        OkGo.get(Urls.MYINFO)
                .tag(this)
                .execute(new JsonCallback<>(MyInfo.class, this));
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
    public void onClick(View v) {

        if (v.getId() == R.id.iv_head || v.getId() == R.id.tv_name || v.getId() == R.id.iv_next) {
            Intent intent = new Intent(getContext(), ProfileActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.KEY, data);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (v.getId() == R.id.tv_my_calendar) {
            startActivity(new Intent(getContext(), MyCalendarActivity.class));
        } else if (v.getId() == R.id.my_order) {
//            startActivity(new Intent(getContext(), MyOrderActivity.class));
            Toast.makeText(getContext(), R.string.comming_soon, Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.my_msg) {
//            startActivity(new Intent(getContext(), MyMsgActivity.class));
            Toast.makeText(getContext(), R.string.comming_soon, Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.my_bill) {
//            startActivity(new Intent(getContext(), MyBillActivity.class));
            Toast.makeText(getContext(), R.string.comming_soon, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccess(Response response) {

        if (response != null) {

            if (response.body() instanceof MyInfo) {
                MyInfo myInfo = (MyInfo) response.body();
                MyInfo.Data data = myInfo.data;
                ((TextView) view.findViewById(R.id.tv_name)).setText(data.username);

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
}
