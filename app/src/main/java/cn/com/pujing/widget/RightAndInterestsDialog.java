package cn.com.pujing.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import cn.com.pujing.R;
import cn.com.pujing.adapter.ChooseRightAndInterestsAdapter;
import cn.com.pujing.entity.ChooseRightsAndInterestsBean;
import cn.com.pujing.entity.MyCardBean;

/**
 * author : liguo
 * date : 2021/3/26 13:09
 * description :
 */
public class RightAndInterestsDialog extends Dialog {

    private Context context;
    private RecyclerView rvChooseRightAndInterests;
    private ChooseRightAndInterestsAdapter chooseRightAndInterestsAdapter;
    private TextView tvNoUseCoupon,tvUseCoupon;
    private List<MyCardBean> myCardBeans;
    private OnChooseItemsListener onChooseItemsListener;
    private int posId = -1;

    public RightAndInterestsDialog(@NonNull Context context, List<MyCardBean> myCardBeans,OnChooseItemsListener onChooseItemsListener) {
        super(context);
        this.context = context;
        this.myCardBeans = myCardBeans;
        this.onChooseItemsListener = onChooseItemsListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_health_center);

        rvChooseRightAndInterests = findViewById(R.id.rv_choose_right_and_interests);

        tvNoUseCoupon = findViewById(R.id.tv_no_use_coupon);
        tvUseCoupon = findViewById(R.id.tv_use_coupon);

        tvNoUseCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChange(-1);
                posId = -1;
                dismiss();
            }
        });

        tvUseCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChooseItemsListener.onChooseItems(posId);
            }
        });

        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        chooseRightAndInterestsAdapter = new ChooseRightAndInterestsAdapter(R.layout.adapter_choose_rights_and_interests,myCardBeans);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rvChooseRightAndInterests.setLayoutManager(linearLayoutManager);

        rvChooseRightAndInterests.setAdapter(chooseRightAndInterestsAdapter);

        chooseRightAndInterestsAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                onChange(position);
                posId = position;
            }
        });
    }

    public void onChange(int pos) {
        chooseRightAndInterestsAdapter.setPos(pos);
        chooseRightAndInterestsAdapter.notifyDataSetChanged();
    }

    public interface OnChooseItemsListener{
        public void onChooseItems(int pos);
    }
}
