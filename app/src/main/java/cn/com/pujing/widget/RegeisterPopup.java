package cn.com.pujing.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.com.pujing.R;

/**
 * author : liguo
 * date : 2021/4/14 18:54
 * description :
 */
public class RegeisterPopup  extends PopupWindow implements View.OnClickListener {

    private View conentView;
    Context context;
    private OnListener onListener;
    private int sex = 1;
    private TextView tvMale;
    private TextView tvFemale;


    public RegeisterPopup(Context context,OnListener onListener){
        this.onListener = onListener;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.pop_register, null);
        this.context = context;
        this.setContentView(conentView);
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        this.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);

        initData();

    }

    private void initData(){
        tvMale = conentView.findViewById(R.id.tv_male);
        tvFemale = conentView.findViewById(R.id.tv_female);

        conentView.findViewById(R.id.tv_cancel).setOnClickListener(this);
        conentView.findViewById(R.id.tv_sure).setOnClickListener(this);
        conentView.findViewById(R.id.tv_male).setOnClickListener(this);
        conentView.findViewById(R.id.tv_female).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:

                dismiss();

                break;
            case R.id.tv_sure:

                onListener.getSex(sex);

                dismiss();
                break;
            case R.id.tv_male:
                sex = 1;

                tvFemale.setBackground(context.getResources().getDrawable(R.drawable.bg_register_sex_unclick));
                tvMale.setBackground(context.getResources().getDrawable(R.drawable.bg_register_sex_click));

                break;
            case R.id.tv_female:
                sex = 2;

                tvFemale.setBackground(context.getResources().getDrawable(R.drawable.bg_register_sex_click));
                tvMale.setBackground(context.getResources().getDrawable(R.drawable.bg_register_sex_unclick));

                break;

            default:

                break;
        }
    }

    public interface OnListener{
        public void getSex(int sex);
    }
}
