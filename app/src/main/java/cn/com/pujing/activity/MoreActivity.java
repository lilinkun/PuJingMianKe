package cn.com.pujing.activity;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.model.Response;

import java.util.Collections;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.callback.DragItemCallback;
import cn.com.pujing.util.Constants;
import cn.com.pujing.R;
import cn.com.pujing.util.DragItemHelper;
import cn.com.pujing.util.Urls;
import cn.com.pujing.adapter.GridAdapter;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.entity.GridItem;

public class MoreActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    private GridAdapter gridAdapter;
    private DragItemHelper dragHelper;

    @Override
    public int getLayoutId() {
        return R.layout.activity_more;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this).statusBarColor(R.color.main_color).fitsSystemWindows(true).init();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getBaseContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        gridAdapter = new GridAdapter(R.layout.item_grid_another, GridItem.getTestData1());
        gridAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                GridItem gridItem = (GridItem) adapter.getItem(position);

                if (gridItem != null) {
                    if (getString(R.string.life_service).equals(gridItem.title)) {
                        Toast.makeText(MoreActivity.this, getString(R.string.comming_soon), Toast.LENGTH_SHORT).show();
                    } else if (getString(R.string.community_calendar).equals(gridItem.title)) {
                        startActivity(new Intent(MoreActivity.this, CommunityCalendarActivity.class));
                    } else if ("场馆预约".equals(gridItem.title)) {
                        Toast.makeText(MoreActivity.this, getString(R.string.comming_soon), Toast.LENGTH_SHORT).show();
                    } else if (getString(R.string.photo_wall).equals(gridItem.title)) {
                        startActivity(new Intent(MoreActivity.this, PhotoWallActivity.class));
                    } else if ("问卷调查".equals(gridItem.title)) {
                        Intent intent = new Intent(MoreActivity.this, WebviewActivity.class);
                        intent.putExtra(Constants.URL, Urls.SURVEYLIST);
                        startActivity(intent);
                    } else if (getString(R.string.feedback).equals(gridItem.title)) {
                        startActivity(new Intent(getBaseContext(), FeedbackActivity.class));
                    }
                }
            }
        });
        recyclerView.setAdapter(gridAdapter);


        //设置拖拽相关
//        DragItemHelper mDragItemHelper = new DragItemHelper(recyclerView,gridAdapter);
      /*  mDragItemHelper.setDragStateCallback(new DragItemCallback.DragStateCallback() {
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if(actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    viewHolder.itemView.setBackgroundColor(Color.GRAY);
                }
            }
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if(viewHolder!=null)
                    viewHolder.itemView.setBackgroundColor(0);
            }


        });*/
    }

    @Override
    @OnClick({R.id.iv_back})
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.iv_back) {
            finish();
        }
    }

    @Override
    public void onSuccess(Response response) {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    ItemTouchHelper mItemHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            Log.e("hsjkkk", "getMovementFlags()");
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            } else {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            }
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            Log.e("hsjkkk", "onMove()");
            //得到当拖拽的viewHolder的Position
            int fromPosition = viewHolder.getAdapterPosition();
            //拿到当前拖拽到的item的viewHolder
            int toPosition = target.getAdapterPosition();
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(GridItem.getTestData1(), i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(GridItem.getTestData1(), i, i - 1);
                }
            }
            gridAdapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                Toast.makeText(MainActivity.this, "拖拽完成 方向" + direction, Toast.LENGTH_SHORT).show();
            Log.e("hsjkkk", "拖拽完成 方向" + direction);

        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            Log.e("hsjkkk", "onSelectedChanged()");
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE)
                viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            Log.e("hsjkkk", "clearView()");
            viewHolder.itemView.setBackgroundColor(0);

        }

        //重写拖拽不可用
        @Override
        public boolean isLongPressDragEnabled() {
            Log.e("hsjkkk", "isLongPressDragEnabled()");
            return false;
        }


    });


}
