package cn.com.pujing.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.SetMealBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.util.Urls;

/**
 * author : liguo
 * date : 2021/3/2 14:39
 * description :
 */
public class RestRoutineAdapter extends BaseQuickAdapter<SetMealBean, BaseViewHolder> {

    private Context context;
    private RestRoutineChildAdapter restRoutineChildAdapter;

    public RestRoutineAdapter(int layoutResId, @Nullable List<SetMealBean> data,Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SetMealBean setMealBean) {
        baseViewHolder.setText(R.id.tv_content_name,setMealBean.getMealName());
        baseViewHolder.setText(R.id.tv_setmeal_name,"套餐"+ baseViewHolder.getAdapterPosition());
        ImageView imageView = baseViewHolder.getView(R.id.iv_restroutine);

        RecyclerView rvChildRest = baseViewHolder.getView(R.id.rv_child_rest);

        TextView textView = baseViewHolder.getView(R.id.tv_search_detail);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(textView.getText().toString().equals(R.string.search_detail)) {

                    rvChildRest.setVisibility(View.VISIBLE);

                    if (restRoutineChildAdapter == null) {
                        restRoutineChildAdapter = new RestRoutineChildAdapter(R.layout.adapter_restroutine_child, setMealBean.getFoodDetailVoList());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                        rvChildRest.setLayoutManager(linearLayoutManager);
                        rvChildRest.setAdapter(restRoutineChildAdapter);
                    }

                }else {
                    textView.setText(R.string.search_detail);
//                    textView.setdra
                }
            }
        });

        Glide.with(getContext())
                .load(PujingService.PREFIX + PujingService.IMG + setMealBean.getCoverPic())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(imageView);
    }
}
