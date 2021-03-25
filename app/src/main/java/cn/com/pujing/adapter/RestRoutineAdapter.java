package cn.com.pujing.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.activity.RestRoutineActivity;
import cn.com.pujing.entity.SetMealBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.util.PuJingUtils;
import cn.com.pujing.util.Urls;
import cn.com.pujing.widget.GlideRoundTransform;

/**
 * author : liguo
 * date : 2021/3/2 14:39
 * description :
 */
public class RestRoutineAdapter extends BaseQuickAdapter<SetMealBean, BaseViewHolder> {

    private Context context;
    private RestRoutineChildAdapter restRoutineChildAdapter;
    private boolean isNewData = false;

    public RestRoutineAdapter(int layoutResId, @Nullable List<SetMealBean> data,Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SetMealBean setMealBean) {
        baseViewHolder.setText(R.id.tv_content_name,setMealBean.getMealName());
        baseViewHolder.setText(R.id.tv_setmeal_name,context.getResources().getString(R.string.meal)+ PuJingUtils.numberToLetter(baseViewHolder.getAdapterPosition()+1));
        ImageView imageView = baseViewHolder.getView(R.id.iv_restroutine);
        ImageView ivRestArrow = baseViewHolder.getView(R.id.iv_rest_arrow);

        RecyclerView rvChildRest = baseViewHolder.getView(R.id.rv_child_rest);

        LinearLayout llSearchDetail = baseViewHolder.getView(R.id.ll_search_detail);
        TextView textView = baseViewHolder.getView(R.id.tv_search_detail);

        ImageView ivSelectRestroutine = baseViewHolder.getView(R.id.iv_select_restroutine);

//        if (restRoutineChildAdapter == null) {
            restRoutineChildAdapter = new RestRoutineChildAdapter(R.layout.adapter_restroutine_child, setMealBean.getFoodDetailVoList());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            rvChildRest.setLayoutManager(linearLayoutManager);
            rvChildRest.setAdapter(restRoutineChildAdapter);
//        }

        if (setMealBean.isVisibel()){

            ivSelectRestroutine.setVisibility(View.VISIBLE);
            if (setMealBean.isCheck()){
                ivSelectRestroutine.setSelected(true);
                RestRoutineActivity.checkId = setMealBean.getCheckId();
            }else{
                ivSelectRestroutine.setSelected(false);
            }
        }else {
            ivSelectRestroutine.setVisibility(View.GONE);
        }

        textView.setText(R.string.search_detail);
        ivRestArrow.setImageResource(R.mipmap.ic_rest_open_arrow);
        rvChildRest.setVisibility(View.GONE);


        llSearchDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(textView.getText().toString().equals(context.getString(R.string.search_detail))) {

                    textView.setText(R.string.fold);
                    ivRestArrow.setImageResource(R.mipmap.ic_rest_close_arrow);

                    rvChildRest.setVisibility(View.VISIBLE);



                }else {
                    textView.setText(R.string.search_detail);
                    ivRestArrow.setImageResource(R.mipmap.ic_rest_open_arrow);
                    rvChildRest.setVisibility(View.GONE);
//                    textView.setdra
                }
            }
        });

        String coverPic = "";

        if (setMealBean.getCoverPic() != null) {
            if (setMealBean.getCoverPic().contains(",")) {
                coverPic = setMealBean.getCoverPic().split(",")[0];
            } else {
                coverPic = setMealBean.getCoverPic();
            }
        }

        if (isNewData){
            Glide.with(getContext())
                    .load(PujingService.PREFIX + PujingService.IMG + coverPic)
                    .apply(PuJingUtils.setGlideCircle(10))
                    .error(R.color.gray_line)
                    .skipMemoryCache(true)
                    .into(imageView);
        }

    }

    public void setBooleanNewData(boolean isNewData){
        this.isNewData = isNewData;
    }

}
