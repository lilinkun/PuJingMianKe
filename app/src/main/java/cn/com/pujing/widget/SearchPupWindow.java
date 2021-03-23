package cn.com.pujing.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.io.Serializable;
import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.adapter.HomeItemAdapter;
import cn.com.pujing.db.DBManager;
import cn.com.pujing.entity.GridItem;

/**
 * author : liguo
 * date : 2021/3/22 11:19
 * description :
 */
public class SearchPupWindow extends PopupWindow {
    private View conentView;
    private List<GridItem> gridItems;
    private GridItem gridItem;

    public SearchPupWindow(Context context,OnHomeClick onHomeClick){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.layout_home_list, null);

        this.setContentView(conentView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SearchPupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);

        TextView tvSearchCancel = conentView.findViewById(R.id.tv_search_cancel);
        RecyclerView rvHomeList = conentView.findViewById(R.id.rv_home_list);
        LinearLayout llPupTop = conentView.findViewById(R.id.ll_pup_top);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        HomeItemAdapter homeItemAdapter = new HomeItemAdapter(R.layout.adapter_home_grid_item,null);


        rvHomeList.setLayoutManager(linearLayoutManager);
        rvHomeList.setAdapter(homeItemAdapter);

        homeItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                gridItem = gridItems.get(position);
                onHomeClick.onHomeClick(gridItem);
                dismiss();
            }
        });

        llPupTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        EditText editText = conentView.findViewById(R.id.et_home_search);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editText.getText().toString().trim().length() > 0) {
                    gridItems = DBManager.getInstance(context).queryHomeTitle(s.toString());
                    homeItemAdapter.setNewInstance(gridItems);
                }else {
                    homeItemAdapter.setNewInstance(null);
                }
            }
        });

        tvSearchCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public interface OnHomeClick{
        public void onHomeClick(GridItem gridItem);
    }



}
