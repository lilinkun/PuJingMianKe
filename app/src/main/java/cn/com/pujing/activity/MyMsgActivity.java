package cn.com.pujing.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import cn.com.pujing.R;
import cn.com.pujing.adapter.MsgAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.entity.MyMessageBean;
import cn.com.pujing.util.Urls;

public class MyMsgActivity extends BaseActivity implements View.OnClickListener {

    private MsgAdapter msgAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_msg;
    }

    public void init() {

        ImmersionBar.with(this)
                .statusBarColor(R.color.main_color)
                .fitsSystemWindows(true)
                .init();

        findViewById(R.id.iv_back).setOnClickListener(this);

        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL));
        msgAdapter = new MsgAdapter(R.layout.item_msg, null);
        recyclerView.setAdapter(msgAdapter);

        OkGo.get(Urls.MSG)
                .tag(this)
                .execute(new JsonCallback<>(MyMessageBean.class,this));

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.iv_back) {
            finish();
        }
    }

    @Override
    public void onSuccess(Response response) {
        if (response.body() instanceof MyMessageBean){

        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
