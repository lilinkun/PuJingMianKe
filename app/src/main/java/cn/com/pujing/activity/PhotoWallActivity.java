package cn.com.pujing.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

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
import cn.com.pujing.adapter.NodeSectionAdapter;
import cn.com.pujing.adapter.PhotoWallAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.entity.PhotoWall;
import cn.com.pujing.entity.section.ItemNode;
import cn.com.pujing.entity.section.RootFooterNode;
import cn.com.pujing.entity.section.RootNode;
import cn.com.pujing.util.Urls;

public class PhotoWallActivity extends BaseActivity implements View.OnClickListener {
    private NodeSectionAdapter nodeSectionAdapter;

    @BindView(R.id.rv_photo_wall)
    RecyclerView recyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_photo_wall;
    }

    @Override
    public void init() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        findViewById(R.id.iv_back).setOnClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
//        recyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL));
        nodeSectionAdapter = new NodeSectionAdapter();
        recyclerView.setAdapter(nodeSectionAdapter);
        nodeSectionAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {

                if (adapter.getItemViewType(position) == 1) {
                    ItemNode itemNode = (ItemNode) adapter.getItem(position);
                    if (itemNode != null) {
                        String[] strings = itemNode.photo.split(",");

                        if (strings != null) {
//                            ImgViewDialogFragment imgViewDialogFragment = new ImgViewDialogFragment(itemNode.pos, strings);
//                            imgViewDialogFragment.show(getSupportFragmentManager(), "");
                            Intent intent = new Intent(PhotoWallActivity.this,ShowPhotoActivity.class);
                            intent.putExtra("showphoto",strings);
                            startActivity(intent);
                        }
                    }
                }else {
                    RootFooterNode pos = (RootFooterNode) adapter.getItem(position);
                    switch (view.getId()){
                        case R.id.iv_download:
                            Toast.makeText(PhotoWallActivity.this,"down"+pos.getPos(),Toast.LENGTH_LONG).show();
                            break;
                        case R.id.iv_collect:
                            Toast.makeText(PhotoWallActivity.this,"collect"+pos.getPos(),Toast.LENGTH_LONG).show();
                            break;
                        case R.id.iv_share:
                            Toast.makeText(PhotoWallActivity.this,"share"+pos.getPos(),Toast.LENGTH_LONG).show();
                            break;
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

                PhotoWallAdapter photoWallAdapter =  new PhotoWallAdapter(R.layout.adapter_photo_wall,photoWall.data.records,this);

//                recyclerView.setAdapter(photoWallAdapter);
                nodeSectionAdapter.setNewInstance(getEntity(photoWall.data.records));
            }
        }
    }

    private List<BaseNode> getEntity(List<PhotoWall.Data.Record> records) {
        if (records != null && records.size() > 0) {
            List<BaseNode> list = new ArrayList<>();

            for (int j = 0; j < records.size();j++) {
                PhotoWall.Data.Record record = records.get(j);
                String photo = record.photo;

                if (!TextUtils.isEmpty(photo)) {
                    String[] strings = photo.split(",");
                    if (strings != null) {
                        List<BaseNode> itemNodes = new ArrayList<>();

//                        int photoLength = strings.length > 9 ? 9 : strings.length;
                        int photoLength = strings.length;

                        for (int i = 0; i < photoLength; i++) {
                            String string = strings[i];
                            ItemNode itemNode = new ItemNode(string);
                            itemNode.pos = i;
                            itemNode.photo = photo;
                            if (i == 8){
                                itemNode.showMore = true;
                            }else {
                                itemNode.showMore = false;
                            }
                            itemNodes.add(itemNode);
                        }

                        RootNode rootNode = new RootNode(itemNodes, record.title, record.content,j);
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
