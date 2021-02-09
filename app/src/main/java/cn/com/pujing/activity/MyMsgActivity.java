package cn.com.pujing.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;

import cn.com.pujing.R;
import cn.com.pujing.adapter.MsgAdapter;

public class MyMsgActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setContentView(R.layout.activity_my_msg);

        ImmersionBar.with(this)
                .statusBarColor("#ED6D0F")
                .fitsSystemWindows(true)
                .init();

        findViewById(R.id.iv_back).setOnClickListener(this);

        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL));
        MsgAdapter msgAdapter = new MsgAdapter(R.layout.item_msg, null);
        recyclerView.setAdapter(msgAdapter);
        msgAdapter.setEmptyView(R.layout.empty_view);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.iv_back) {
            finish();
        }
    }
}
