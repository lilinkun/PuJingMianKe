package cn.com.pujing.adapter;

import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.http.PujingService;

/**
 * author : liguo
 * date : 2021/5/18 16:20
 * description :
 */
public class FeedBackDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public FeedBackDetailAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {

        ImageView imageView = baseViewHolder.getView(R.id.iv_upload);
        setViewHeightByWidth(imageView);
        Glide.with(getContext()).load(PujingService.PREFIX + PujingService.IMG + s)
                .placeholder(R.drawable.loading).error(R.drawable.ic_no_pic).into(imageView);
    }

    public void setViewHeightByWidth(ImageView view) {
        final ImageView mv = view;
        final ViewTreeObserver observer = mv.getViewTreeObserver();


        final ViewTreeObserver.OnPreDrawListener preDrawListener = new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {

                int width = mv.getMeasuredWidth();
                int height = mv.getMeasuredHeight();


                android.view.ViewGroup.LayoutParams lp = mv.getLayoutParams();
                lp.height = mv.getMeasuredWidth();
                mv.setLayoutParams(lp);

                final ViewTreeObserver vto1 = mv.getViewTreeObserver();

                //调用一次之后移除，不影响性能
                vto1.removeOnPreDrawListener(this);


                return true;
            }
        };

        observer.addOnPreDrawListener(preDrawListener);
    }
}
