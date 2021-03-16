package cn.com.pujing.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.adapter.RestMealTypeAdapter;
import cn.com.pujing.entity.RestMealTypeBean;

/**
 * author : liguo
 * date : 2021/3/10 20:37
 * description :
 */
public class RestMealTypeDialog extends AlertDialog {

    private List<RestMealTypeBean> restMealTypeBeans;
    private Context context;
    private MealTypeClickListener feedbackTypeClickListener;

    public RestMealTypeDialog(@NonNull Context context,List<RestMealTypeBean>restMealTypeBeans,MealTypeClickListener feedbackTypeClickListener) {
        super(context);
        this.restMealTypeBeans = restMealTypeBeans;
        this.context = context;
        this.feedbackTypeClickListener = feedbackTypeClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_rest_meal_type);

        RecyclerView rvRestMealType = findViewById(R.id.rv_rest_meal_type);


        RestMealTypeAdapter restMealTypeAdapter = new RestMealTypeAdapter(R.layout.adapter_meal_reserve,restMealTypeBeans);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);

        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rvRestMealType.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.HORIZONTAL));

        rvRestMealType.setLayoutManager(linearLayoutManager);

        rvRestMealType.setAdapter(restMealTypeAdapter);

        restMealTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                feedbackTypeClickListener.setItemValue(restMealTypeBeans.get(position));
                dismiss();
            }
        });

    }


    public interface MealTypeClickListener{
        public void setItemValue(RestMealTypeBean restMealTypeBean);
    }
}
