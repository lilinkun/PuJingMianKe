package cn.com.pujing.activity;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.pujing.R;
import cn.com.pujing.util.Urls;
import cn.com.pujing.adapter.NodeSectionAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.datastructure.PhotoWall;
import cn.com.pujing.datastructure.section.ItemNode;
import cn.com.pujing.datastructure.section.RootNode;
import cn.com.pujing.fragment.ImgViewDialogFragment;

public class PhotoWallActivity extends BaseActivity implements View.OnClickListener {
    private NodeSectionAdapter nodeSectionAdapter;

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_photo_wall;
    }

    @Override
    public void init() {

        ImmersionBar.with(this).statusBarColor("#ED6D0F").fitsSystemWindows(true).init();

        findViewById(R.id.iv_back).setOnClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        nodeSectionAdapter = new NodeSectionAdapter();
        recyclerView.setAdapter(nodeSectionAdapter);
        nodeSectionAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                ItemNode itemNode = (ItemNode) adapter.getItem(position);

                if (itemNode != null) {
                    String[] strings = itemNode.photo.split(",");

                    if (strings != null) {
                        ImgViewDialogFragment imgViewDialogFragment = new ImgViewDialogFragment(itemNode.pos, strings);
                        imgViewDialogFragment.show(getSupportFragmentManager(), "");
                    }
                }
            }
        });


        OkGo.get(Urls.PHOTOWALL)
                .tag(this)
                .execute(new JsonCallback<>(PhotoWall.class, this));
    }

    @Override
    public void onSuccess(Response response) {
        if (response != null) {

            if (response.body() instanceof PhotoWall) {
                PhotoWall photoWall = (PhotoWall) response.body();
                nodeSectionAdapter.setNewInstance(getEntity(photoWall.data.records));
            }
        }
    }

    private List<BaseNode> getEntity(List<PhotoWall.Data.Record> records) {
        if (records != null && records.size() > 0) {
            List<BaseNode> list = new ArrayList<>();

            for (PhotoWall.Data.Record record : records) {
                String photo = record.photo;

                if (!TextUtils.isEmpty(photo)) {
                    String[] strings = photo.split(",");
                    if (strings != null) {
                        List<BaseNode> itemNodes = new ArrayList<>();

                        for (int i = 0; i < strings.length; i++) {
                            String string = strings[i];
                            ItemNode itemNode = new ItemNode(string);
                            itemNode.pos = i;
                            itemNode.photo = photo;
                            itemNodes.add(itemNode);
                        }

                        RootNode rootNode = new RootNode(itemNodes, record.title, record.content);
                        list.add(rootNode);
                    }
                }
            }
            return list;
        }

        return null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.iv_back) {
            finish();
        }
    }
}
