package cn.com.pujing.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.com.pujing.R;
import cn.com.pujing.entity.CommemorationDayBean;

/**
 * author : liguo
 * date : 2021/4/13 9:31
 * description :
 */
public class CommemorationDayAdapter extends BaseQuickAdapter<CommemorationDayBean.CommemorationDay, BaseViewHolder> {

    private SetDayListener setDayListener;

    public CommemorationDayAdapter(int layoutResId, @Nullable List<CommemorationDayBean.CommemorationDay> data,SetDayListener setDayListener) {
        super(layoutResId, data);
        this.setDayListener = setDayListener;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CommemorationDayBean.CommemorationDay commemorationDay) {
        baseViewHolder.setText(R.id.tv_commemoration_day_name,commemorationDay.commemorationName);
        baseViewHolder.setText(R.id.tv_commemoration_day_date,commemorationDay.commemorationDay);

        ImageView ivCommemorationDelete = baseViewHolder.getView(R.id.iv_commemoration_delete);
        ImageView ivCommemorationEdit = baseViewHolder.getView(R.id.iv_commemoration_edit);

        ivCommemorationDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDayListener.setDelete(baseViewHolder.getAdapterPosition());
            }
        });

        ivCommemorationEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDayListener.setEdit(baseViewHolder.getAdapterPosition());
            }
        });

    }

    public interface SetDayListener{
        public void setDelete(int position);
        public void setEdit(int position);
    }
}
