package cn.com.pujing.adapter;

import android.os.Build;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.util.BannerUtils;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.BannerBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.util.Urls;
import cn.com.pujing.entity.BannerInfo;
import cn.com.pujing.viewholder.ImageHolder;

/**
 * 自定义布局，网络图片
 */
public class ImageNetAdapter extends BannerAdapter<BannerBean, ImageHolder> {

    public ImageNetAdapter(List<BannerBean> mDatas) {
        super(mDatas);
    }

    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = (ImageView) BannerUtils.getView(parent, R.layout.banner_image);
        //通过裁剪实现圆角
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BannerUtils.setBannerRound(imageView, 15);
        }
        return new ImageHolder(imageView);
    }

    @Override
    public void onBindView(ImageHolder holder,BannerBean data, int position, int size) {
        //通过图片加载器实现圆角，你也可以自己使用圆角的imageview，实现圆角的方法很多，自己尝试哈
        Glide.with(holder.itemView)
                .load(PujingService.PREFIX + Urls.IMG + data.getPicture())
                .thumbnail(Glide.with(holder.itemView).load(R.drawable.loading))
//                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(holder.imageView);
    }

}
