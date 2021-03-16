package cn.com.pujing.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.BanquetBean;
import cn.com.pujing.entity.RestSortDetailBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.util.PuJingUtils;
import cn.com.pujing.util.Urls;
import cn.com.pujing.widget.GlideRoundTransform;

/**
 * author : liguo
 * date : 2021/3/5 15:32
 * description :
 */
public class RestDetailAdapter extends BaseMultiItemQuickAdapter<RestSortDetailBean, BaseViewHolder> {

    private Context context;
    private OnRestLisener listener;

    public RestDetailAdapter(Context context,List<RestSortDetailBean> restSortDetailBeans,OnRestLisener listener){
        addItemType(0, R.layout.adapter_rest_sort_detail);
        addItemType(1,R.layout.item_title);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RestSortDetailBean categoryList) {
        switch (baseViewHolder.getItemViewType()){
            case 1:

                baseViewHolder.setText(R.id.tv_title, categoryList.getTitleName());

                break;


            case 0:

                baseViewHolder.setText(R.id.iv_goods_name, categoryList.getName());
                baseViewHolder.setText(R.id.tv_rest_detail, categoryList.getDetail());
                baseViewHolder.setText(R.id.iv_goods_price, "￥" + categoryList.getPrice());

                ImageView ivGoodsReduce = baseViewHolder.getView(R.id.iv_goods_reduce);
                TextView tvGoodsNum = baseViewHolder.getView(R.id.tv_goods_num);

                ImageView ivGoodsImg = baseViewHolder.getView(R.id.iv_goods_img);

                String imgUrl = PujingService.PREFIX + Urls.IMG + categoryList.getImgsrc();

                Glide.with(context).load(imgUrl).error(R.color.gray_line)
                        .apply(PuJingUtils.setGlideCircle(10)).into(ivGoodsImg);


                imageUpdate(categoryList.getGoodsNum(),ivGoodsReduce,tvGoodsNum);

                baseViewHolder.getView(R.id.iv_goods_reduce).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int num1 = categoryList.getGoodsNum()-1;
                        categoryList.setGoodsNum(num1);
                        listener.onAdd(categoryList.getmId(),num1);

                        imageUpdate(categoryList.getGoodsNum(),ivGoodsReduce,tvGoodsNum);


                    }
                });


                baseViewHolder.getView(R.id.iv_goods_add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int num = categoryList.getGoodsNum()+1;
                        categoryList.setGoodsNum(num);



                        imageUpdate(categoryList.getGoodsNum(),ivGoodsReduce,tvGoodsNum);

                        listener.onAdd(categoryList.getmId(),num);


                    }
                });

                break;

        }
    }

    public interface OnRestLisener{
        public void onAdd(int menuItemId,int quantity);
    }

    public void imageUpdate(int num , ImageView ivGoodsReduce,TextView tvGoodsNum){


        if (num > 0){
            ivGoodsReduce.setVisibility(View.VISIBLE);
            tvGoodsNum.setVisibility(View.VISIBLE);
            tvGoodsNum.setText(num + "");
        }else {
            ivGoodsReduce.setVisibility(View.GONE);
            tvGoodsNum.setVisibility(View.GONE);
        }

    }


}
