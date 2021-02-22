package cn.com.pujing.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

import cn.com.pujing.base.BaseFragment;
import cn.com.pujing.entity.Base;

public class VpAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragmentList;

    public VpAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragmentList = fragmentList;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
}
