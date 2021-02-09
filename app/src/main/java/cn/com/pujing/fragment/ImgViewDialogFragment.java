package cn.com.pujing.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import cn.com.pujing.R;
import cn.com.pujing.adapter.ImgViewAdapter;

public class ImgViewDialogFragment extends DialogFragment implements View.OnClickListener {
    private int pos;
    private String[] strings;

    public ImgViewDialogFragment(int pos, String[] strings) {
        this.pos = pos;
        this.strings = strings;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogFragmentImgView);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_img_view, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        ViewPager viewPager = view.findViewById(R.id.vp);
        ImgViewAdapter imgViewAdapter = new ImgViewAdapter(strings, getContext());
        viewPager.setAdapter(imgViewAdapter);

        TextView posTextView = view.findViewById(R.id.tv_pos);
        if (strings != null) {
            posTextView.setText((pos + 1) + "/" + strings.length);
        }
        viewPager.setCurrentItem(pos);

        view.findViewById(R.id.tv_cancel).setOnClickListener(this);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                posTextView.setText((position + 1) + "/" + strings.length);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.tv_cancel) {
            dismiss();
        }
    }
}
