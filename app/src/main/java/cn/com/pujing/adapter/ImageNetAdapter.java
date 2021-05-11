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
import cn.com.pujing.viewholder.ImageHolder;

/**
 * 自定义布局，网络图片
 */
public class ImageNetAdapter extends BannerAdapter<BannerBean, ImageHolder> {

    private int type = 0;
    private ImageView imageView;

    public ImageNetAdapter(List<BannerBean> mDatas,int type) {
        super(mDatas);
        this.type = type;
    }

    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        if (type == 0) {
             imageView = (ImageView) BannerUtils.getView(parent, R.layout.banner_image);
        }else if (type == 1) {
            imageView = (ImageView) BannerUtils.getView(parent, R.layout.banner_image1);
        }

        /*ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.width = 1000 ;
        layoutParams.height = 800 ;
        imageView.setLayoutParams(layoutParams);*/

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
                .load(PujingService.PREFIX + PujingService.IMG + data.getPicture())
                .thumbnail(Glide.with(holder.itemView).load(R.drawable.loading))
//                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(holder.imageView);
    }

}
