package com.viewpagertext.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.List;

/**
 * name:小龙虾
 * time:20.19.5.25
 * type:获取本地音乐的适配器
 */

public class PlayFragmentPagerAdapter extends FragmentPagerAdapter {


    private List<Fragment> mData;
    public PlayFragmentPagerAdapter(FragmentManager fm,List<Fragment> mData) {
        super(fm);
        this.mData=mData;
    }

    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }
}
