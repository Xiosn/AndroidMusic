package com.viewpagertext.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;

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
