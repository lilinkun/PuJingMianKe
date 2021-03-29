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

import butterknife.BindView;
import cn.com.pujing.R;
import cn.com.pujing.adapter.ChooseRightAndInterestsAdapter;
import cn.com.pujing.entity.ChooseRightsAndInterestsBean;

/**
 * author : liguo
 * date : 2021/3/26 13:09
 * description :
 */
public class RightAndInterestsDialog extends Dialog {

    private Context context;
    private RecyclerView rvChooseRightAndInterests;
    private ChooseRightAndInterestsAdapter chooseRightAndInterestsAdapter;
    private ArrayList<ChooseRightsAndInterestsBean> chooseRightsAndInterestsBeans;
    private TextView tvNoUse;

    public RightAndInterestsDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_health_center);

        rvChooseRightAndInterests = findViewById(R.id.rv_choose_right_and_interests);

        tvNoUse = findViewById(R.id.tv_no_use);

        tvNoUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        chooseRightsAndInterestsBeans = new ArrayList<>();

        for (int i = 0;i<4;i++) {
            ChooseRightsAndInterestsBean chooseRightsAndInterestsBean = new ChooseRightsAndInterestsBean("中医理疗兑换券", "抵扣单次服务费用", "2021-04-17", false);

            chooseRightsAndInterestsBeans.add(chooseRightsAndInterestsBean);
        }

        chooseRightAndInterestsAdapter = new ChooseRightAndInterestsAdapter(R.layout.adapter_choose_rights_and_interests,chooseRightsAndInterestsBeans);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rvChooseRightAndInterests.setLayoutManager(linearLayoutManager);

        rvChooseRightAndInterests.setAdapter(chooseRightAndInterestsAdapter);

        chooseRightAndInterestsAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                onChange(position);
            }
        });
    }

    public void onChange(int pos) {
        for (int i = 0;i < chooseRightsAndInterestsBeans.size();i++){
            if (i == pos){
                chooseRightsAndInterestsBeans.get(i).isChoose = true;
            }else {
                chooseRightsAndInterestsBeans.get(i).isChoose = false;
            }
        }
        chooseRightAndInterestsAdapter.notifyDataSetChanged();
    }
}
