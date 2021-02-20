package cn.com.pujing.callback;

import java.util.List;

/**
 * Created by caizepeng on 16/5/19.
 */
public  interface DragItemData{
    public List getDatas();
    public void notifyItemRemoved(int position);
}
