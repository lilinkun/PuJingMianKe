package cn.com.pujing.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.BanquetBean;
import cn.com.pujing.entity.RestSortDetailBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.util.PuJingUtils;
import cn.com.pujing.util.UToast;
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
    private boolean isNotify = false;
    private List<RestSortDetailBean> restSortDetailBeans;
    private HashMap<Integer,HashMap<Integer,TextView>> viewHashMap = new HashMap<>();
    private ArrayList<Integer> integers;
    private ArrayList<ArrayList<Integer>> arrayLists;
    private ArrayList<HashMap<Integer,TextView>> textViews = new ArrayList<>();

    public RestDetailAdapter(Context context,List<RestSortDetailBean> restSortDetailBeans,OnRestLisener listener){
        addItemType(0, R.layout.adapter_rest_sort_detail);
        addItemType(1,R.layout.item_title);
        this.context = context;
        this.listener = listener;
        this.restSortDetailBeans =restSortDetailBeans;
    }

    public void setNotify(boolean isNotify){
        this.isNotify = isNotify;
        if (isNotify) {
            notifyDataSetChanged();
        }
    }

    public void setHash(ArrayList<HashMap<Integer,ArrayList<Integer>>> hashMaps,ArrayList<Integer> integers,ArrayList<ArrayList<Integer>> arrayLists){
        this.integers = integers;
        this.arrayLists = arrayLists;
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RestSortDetailBean categoryList) {
        switch (baseViewHolder.getItemViewType()){
            case 1:

                baseViewHolder.setText(R.id.tv_title, categoryList.getTitleName());

                baseViewHolder.getView(R.id.tv_title).setTag(baseViewHolder.getAdapterPosition());

                break;


            case 0:

                baseViewHolder.setText(R.id.iv_goods_name, categoryList.getName());
                baseViewHolder.setText(R.id.tv_rest_detail, categoryList.getDetail());
                baseViewHolder.setText(R.id.iv_goods_price, "￥" + PuJingUtils.removeAmtLastZero(categoryList.getPrice()));


                ImageView ivGoodsReduce = baseViewHolder.getView(R.id.iv_goods_reduce);
                TextView tvGoodsNum = baseViewHolder.getView(R.id.tv_goods_num);

                ImageView ivGoodsImg = baseViewHolder.getView(R.id.iv_goods_img);

                HashMap<Integer,TextView> textViewHashMap = new HashMap<>();
                textViewHashMap.put(baseViewHolder.getAdapterPosition(),tvGoodsNum);

                textViews.add(textViewHashMap);

                if (integers.contains(categoryList.getFoodId())) {

                    if (viewHashMap.get(categoryList.getFoodId()) == null || viewHashMap.get(categoryList.getFoodId()).size() < 2) {

                        HashMap<Integer, TextView> integerTextViewHashMap = new HashMap<>();
                        integerTextViewHashMap.put(baseViewHolder.getAdapterPosition(), tvGoodsNum);

                        viewHashMap.put(categoryList.getFoodId(), integerTextViewHashMap);
                    }
                }

                String imgUrl = PujingService.PREFIX + Urls.IMG + categoryList.getImgsrc();

                if (ivGoodsImg.getTag() == null || !(imgUrl+baseViewHolder.getAdapterPosition()).equals(ivGoodsImg.getTag())) {
                    ivGoodsImg.setTag(imgUrl+baseViewHolder.getAdapterPosition());
                    Glide.with(context).load(imgUrl).error(R.color.gray_line)
                            .apply(PuJingUtils.setGlideCircle(10)).skipMemoryCache(true).into(ivGoodsImg);
                }

                imageUpdate(categoryList.getGoodsNum(),ivGoodsReduce,tvGoodsNum);

                baseViewHolder.getView(R.id.iv_goods_reduce).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int num1 = categoryList.getGoodsNum()-1;
                        categoryList.setGoodsNum(num1);
                        listener.onAdd(categoryList.getmId(),num1);

                        imageUpdate(categoryList.getGoodsNum(),ivGoodsReduce,tvGoodsNum);

//                        setNotify(true);
                    }
                });


                baseViewHolder.getView(R.id.iv_goods_add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (categoryList.getGoodsNum() < 99) {
                            int num = categoryList.getGoodsNum() + 1;
                            categoryList.setGoodsNum(num);

                            imageUpdate(categoryList.getGoodsNum(), ivGoodsReduce, tvGoodsNum);

                            listener.onAdd(categoryList.getmId(), num);
                        }else {
                            UToast.show(context,"已到最大值");
                        }

//                        setNotify(true);

                        /*if (integers.contains(categoryList.getFoodId())){

                            for (int i = 0;i<arrayLists.size();i++){

                                if (arrayLists.get(i).contains(baseViewHolder.getAdapterPosition())){
                                    ArrayList<Integer> integers = arrayLists.get(i);
                                    for (int j = 0 ; j< integers.size();j++){
                                        for (int k = 0;k<textViews.size();k++){
                                            if (baseViewHolder.getAdapterPosition() != integers.get(j)) {
                                                textViews.get(k).get(integers.get(j)).setText(num+"");
                                                textViews.get(k).get(integers.get(j)).setVisibility(View.VISIBLE);
                                            }
                                        }
                                    }
                                }

                            }*/


                           /* HashMap<Integer,TextView> integerTextViewHashMap = new HashMap<>();
                            integerTextViewHashMap.put(baseViewHolder.getAdapterPosition(),tvGoodsNum);

                            viewHashMap.put(categoryList.getFoodId(),integerTextViewHashMap);*/
                        }


//                    }
                });

                break;

            default:

                break;
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return restSortDetailBeans.size();
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(true);
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
