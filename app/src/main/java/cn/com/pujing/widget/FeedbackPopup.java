package cn.com.pujing.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.Arrays;
import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.adapter.FeedbackPopAdapter;
import cn.com.pujing.entity.FeedbackBean;

public class FeedbackPopup extends PopupWindow {

    private View conentView;
    Context context;
    private FeedbackTypeClickListener feedbackTypeClickListener;
    private List<String> strings;
    private List<FeedbackBean> opinionTypeBeans;

    public FeedbackPopup(Context context, List<FeedbackBean> opinionTypeBeans){

        this.opinionTypeBeans = opinionTypeBeans;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popup_feedback, null);
        this.context = context;
        this.setContentView(conentView);
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);

        initData();
    }

    private void initData(){
        RecyclerView recyclerView = conentView.findViewById(R.id.rv_feedback);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        strings = Arrays.asList(conentView.getResources().getStringArray(R.array.feedback_type));

        FeedbackPopAdapter feedbackPopAdapter = new FeedbackPopAdapter(R.layout.adapter_popup_feedback, opinionTypeBeans);

        recyclerView.setAdapter(feedbackPopAdapter);

        feedbackPopAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                feedbackTypeClickListener.setItemValue(strings.get(position),position);
                dismiss();
            }
        });
    }

    public void setListener(FeedbackTypeClickListener listener){
        this.feedbackTypeClickListener = listener;
    }


    public interface FeedbackTypeClickListener{
        public void setItemValue(String value,int position);
    }

}
