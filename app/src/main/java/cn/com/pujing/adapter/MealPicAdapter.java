package cn.com.pujing.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.RestMealBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.util.Urls;

/**
 * author : liguo
 * date : 2021/3/9 19:49
 * description :
 */
public class MealPicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context context;

    public MealPicAdapter(int layoutResId, @Nullable ArrayList<String> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String pic) {

        ImageView imageView = baseViewHolder.getView(R.id.iv_meal_pic);

        Glide.with(context).load(PujingService.PREFIX + Urls.IMG + pic).error(R.drawable.ic_no_pic).into(imageView);
    }
}
