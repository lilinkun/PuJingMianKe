package cn.com.pujing.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.dragswipe.DragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.module.BaseDraggableModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.adapter.GridAdapter;
import cn.com.pujing.db.DBManager;
import cn.com.pujing.entity.GridItem;

public class HomePopupWindow extends PopupWindow {
    private Context context;
    private View conentView;
    private GridAdapter gridAdapter;

    public HomePopupWindow(@NonNull Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.dialog_layout_home, null);
        this.context = context;
        this.setContentView(conentView);
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        this.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);

        initData();
    }


    private void initData(){
        RecyclerView rvDialogHome = (RecyclerView) conentView.findViewById(R.id.rv_dialog_home);
        TextView tvHomePopupSave = (TextView) conentView.findViewById(R.id.tv_home_popup_save);
        LinearLayout llHomePopup = (LinearLayout) conentView.findViewById(R.id.ll_home_pop);
        llHomePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        tvHomePopupSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<GridItem> gridItems = gridAdapter.getData();

                DBManager.getInstance(context).deleteHomeTitle();

                for (int i = 0;i<gridItems.size();i++){
                    DBManager.getInstance(context).insertHomeTitle(gridItems.get(i));
                }
                DBManager.getInstance(context).insertHomeTitle(new GridItem(R.mipmap.ic_more_services, "更多服务",9));

                dismiss();
            }
        });

        List<GridItem> gridItems = DBManager.getInstance(context).queryHomeTitle();
        gridItems.remove(8);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 5);
        rvDialogHome.setLayoutManager(gridLayoutManager);
        gridAdapter = new GridAdapter(R.layout.item_grid, gridItems);

        BaseDraggableModule baseDraggableModule = new BaseDraggableModule(gridAdapter);
        DragAndSwipeCallback dragAndSwipeCallback = new DragAndSwipeCallback(baseDraggableModule);
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(dragAndSwipeCallback);
//        itemTouchHelper.attachToRecyclerView(rvDialogHome);

        gridAdapter.getDraggableModule().setDragEnabled(true);
        gridAdapter.getDraggableModule().setOnItemDragListener(listener);
        gridAdapter.getDraggableModule().getItemTouchHelperCallback().setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);

        rvDialogHome.setAdapter(gridAdapter);

    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, 0, 0);
        } else {
            this.dismiss();
        }
    }

    // 拖拽监听
    OnItemDragListener listener = new OnItemDragListener() {
        @Override
        public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
            Log.d(TAG, "drag start");
            final BaseViewHolder holder = ((BaseViewHolder) viewHolder);

            // 开始时，item背景色变化，demo这里使用了一个动画渐变，使得自然
            int startColor = Color.WHITE;
            int endColor = Color.rgb(245, 245, 245);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ValueAnimator v = ValueAnimator.ofArgb(startColor, endColor);
                v.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        holder.itemView.setBackgroundColor((int)animation.getAnimatedValue());
                    }
                });
                v.setDuration(300);
                v.start();
            }
        }

        @Override
        public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
            Log.d(TAG, "move from: " + source.getAdapterPosition() + " to: " + target.getAdapterPosition());
        }

        @Override
        public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
            Log.d(TAG, "drag end");
            final BaseViewHolder holder = ((BaseViewHolder) viewHolder);
            // 结束时，item背景色变化，demo这里使用了一个动画渐变，使得自然
            int startColor = Color.rgb(245, 245, 245);
            int endColor = Color.WHITE;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ValueAnimator v = ValueAnimator.ofArgb(startColor, endColor);
                v.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        holder.itemView.setBackgroundColor((int)animation.getAnimatedValue());
                    }
                });
                v.setDuration(300);
                v.start();
            }
        }
    };

    private String TAG = "LG";

}
