package cn.com.pujing.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;

/**
 * author : liguo
 * date : 2021/5/18 9:09
 * description :
 */
public class FeedBackAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    FeedbackOnclickListener feedbackOnclickListener;

    public FeedBackAdapter(int layoutResId, @Nullable List<String> data,FeedbackOnclickListener feedbackOnclickListener) {
        super(layoutResId, data);
        this.feedbackOnclickListener = feedbackOnclickListener;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {

        ImageView imageView = baseViewHolder.getView(R.id.iv_upload);
        ImageView ivExit = baseViewHolder.getView(R.id.iv_exit);

        setViewHeightByWidth(imageView);

        if (s.equals("1")){
            imageView.setImageResource(R.mipmap.ic_upload);
            ivExit.setVisibility(View.GONE);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    feedbackOnclickListener.feedbackUpload();
                }
            });
        }else {
            Glide.with(getContext()).load(s)
                    .placeholder(R.drawable.loading).error(R.drawable.ic_no_pic).into(imageView);
            ivExit.setVisibility(View.VISIBLE);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    feedbackOnclickListener.feedbackPic(baseViewHolder.getAdapterPosition());
                }
            });
        }

        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbackOnclickListener.exitItem(baseViewHolder.getAdapterPosition());
            }
        });
    }


    public interface FeedbackOnclickListener{
        public void exitItem(int position);
        public void feedbackUpload();
        public void feedbackPic(int position);
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
