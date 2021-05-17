package cn.com.pujing.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

/**
 * author : liguo
 * date : 2021/5/17 16:23
 * description : 设置recyclerview高度的最高值
 */
public class MaxCountLayoutManager extends GridLayoutManager {
    private int maxCount = 4;

    public MaxCountLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MaxCountLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    @Override
    public void setMeasuredDimension(int widthSize, int heightSize) {
        int maxHeight = getMaxHeight();

        if (maxHeight>0&&maxHeight<heightSize){

        super.setMeasuredDimension(widthSize, maxHeight);

    }else {

        super.setMeasuredDimension(widthSize, heightSize);

    }

}

    private int getMaxHeight() {

        if (getChildCount() == 0||maxCount<=0) {

            return 0;

        }

        View child = getChildAt(0);

        int height = child.getHeight();

        final LayoutParams lp = (LayoutParams) child.getLayoutParams();

        height += lp.topMargin + lp.bottomMargin;

        return height*maxCount+getPaddingBottom()+getPaddingTop();

    }
}
