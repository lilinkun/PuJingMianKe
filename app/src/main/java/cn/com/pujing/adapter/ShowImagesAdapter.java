package cn.com.pujing.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.activity.ShowPhotoActivity;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.util.FileUtils;
import cn.com.pujing.util.UToast;

/**
 * c
 */
public class ShowImagesAdapter extends PagerAdapter {

    private List<View> views;
    private List<String> titles;
    private Context context;
    private int type = 0;

    public ShowImagesAdapter(List<View> views, List<String> titles, Context context,int type) {
        this.views = views;
        this.titles = titles;
        this.context = context;
        this.type = type;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ((ViewPager) container).addView(views.get(position));

        //这里设置长按事件的方法
        View imageView = views.get(position);

        if (type == 0){
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    View view = LayoutInflater.from(context).inflate(R.layout.dialog_image_brower,null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(view);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    TextView saveView = view.findViewById(R.id.tv_save_pic);
                    saveView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Glide.with(context)
                                    .asBitmap()
                                    .load(PujingService.PREFIX + PujingService.IMG + titles.get(position)).into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                        FileUtils.saveImageToGallery2(context, resource);
                                    }else {
                                        FileUtils.saveImage(resource,context);
                                    }
                                }
                            });

                            alertDialog.dismiss();
                        }
                    });

                    return false;
                }
            });
        }



        return views.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles == null ? "" : "";
    }

}
