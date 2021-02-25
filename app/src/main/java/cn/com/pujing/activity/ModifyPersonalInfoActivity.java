package cn.com.pujing.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.OkGo;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.pujing.R;
import cn.com.pujing.base.BaseActivity;
import cn.com.pujing.callback.JsonCallback;
import cn.com.pujing.entity.MyInfo;
import cn.com.pujing.util.Urls;

/**
 * author : liguo
 * date : 2021/2/25 11:12
 * description :修改个人信息
 */
public class ModifyPersonalInfoActivity extends BaseActivity {

    // 1,昵称 2，个性签名 3，出生日期 4，房号
    private int modifytype;

    @BindView(R.id.tv_modify_personalinfo_title)
    TextView tvModifyPersonalinfoTitle;
    @BindView(R.id.et_modify)
    EditText etModify;

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_personalinfo;
    }

    @Override
    public void init() {

        modifytype = getIntent().getIntExtra("modifyType",0);

        if (modifytype == 1){
            tvModifyPersonalinfoTitle.setText("修改昵称");
        }else if (modifytype == 2){
            tvModifyPersonalinfoTitle.setText("编辑个人签名");
        }else if (modifytype == 3){
            tvModifyPersonalinfoTitle.setText("编辑房号");
        }

    }

    @OnClick({R.id.tv_modify_personalinfo_save})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_modify_personalinfo_save:

                if (etModify != null && etModify.getText().toString().trim().length() > 0){


                    OkGo.post(Urls.EDITMYINFO)
                            .tag(this)
                            .params("nikeName",etModify.getText().toString())
                            .execute(new JsonCallback<>(MyInfo.class, this));

                }

                break;
        }
    }
}
