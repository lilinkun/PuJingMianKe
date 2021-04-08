package cn.com.pujing.adapter;

import android.os.Build;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.util.BannerUtils;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.BasicServiceVoListBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.viewholder.ImageHolder;

/**
 * author : liguo
 * date : 2021/4/8 20:15
 * description :
 */
public class ServiceImageNetAdapter extends BannerAdapter<BasicServiceVoListBean, ImageHolder> {

    public ServiceImageNetAdapter(List<BasicServiceVoListBean> mDatas) {
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
    public void onBindView(ImageHolder holder,BasicServiceVoListBean data, int position, int size) {
        //通过图片加载器实现圆角，你也可以自己使用圆角的imageview，实现圆角的方法很多，自己尝试哈
        Glide.with(holder.itemView)
                .load(PujingService.PREFIX + PujingService.IMG + data.themePic)
                .thumbnail(Glide.with(holder.itemView).load(R.drawable.loading))
                .error(R.drawable.ic_no_pic)
//                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(holder.imageView);
    }

}
