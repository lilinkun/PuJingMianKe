package cn.com.pujing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import cn.com.pujing.R;
import cn.com.pujing.util.Urls;

public class ImgViewAdapter extends PagerAdapter {
    private String[] strings;
    private Context context;

    public ImgViewAdapter(String[] strings, Context context) {
        this.strings = strings;
        this.context = context;
    }

    @Override
    public int getCount() {
        return strings == null ? 0 : strings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_img_view, null);
        ImageView imageView = view.findViewById(R.id.iv);
        Glide.with(context).load(Urls.PREFIX + Urls.IMG + strings[position]).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
