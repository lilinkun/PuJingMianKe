package cn.com.pujing.util;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import cn.com.pujing.callback.DragItemCallback;
import cn.com.pujing.callback.DragItemData;

/**
 * Created by caizepeng on 16/5/18.
 */
public class DragItemHelper {
    private ItemTouchHelper mItemTouchHelper;
    private RecyclerView mRecyclerView;
    private DragItemData mDragItemData;
    private DragItemCallback mDragItemCallback;
    public DragItemHelper(RecyclerView recyclerView, DragItemData dragItemData) {
        this.mRecyclerView = recyclerView;
        this.mDragItemData = dragItemData;
        this.mDragItemCallback = new DragItemCallback(mDragItemData,recyclerView.getAdapter());
        mItemTouchHelper = new ItemTouchHelper(mDragItemCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    /**
     * 禁止拖动的Position
     * @param positions
     */
    public void addInvalidPos(Integer...positions){
            mDragItemCallback.addinvalidPositons(positions);
    }

    /**
     * 拖动开始和结束的回调
     * @param dragStateCallback
     */
    public void setDragStateCallback(DragItemCallback.DragStateCallback dragStateCallback){
        mDragItemCallback.setmDragStateCallback(dragStateCallback);
    }
    /**
     *
     * @param islongPress
     *        true : 全部无法拖动
     *        fals : 清空所有不可拖动项
     */
    public void isLongPressDragEnabled(boolean islongPress){
        if(islongPress){
            for(int i = 0; i < mRecyclerView.getAdapter().getItemCount();i++){
                mDragItemCallback.addinvalidPositons(i);
            }
        }else{
            mDragItemCallback.clearInvalidPositons();
        }
    }


}
