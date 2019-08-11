package com.viewpagertext.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.List;

/**
 * name:小龙虾
 * time:20.19.5.21
 */
public class LocalAdapter extends FragmentPagerAdapter {


    private List<Fragment> Datas;
    public LocalAdapter(FragmentManager fm,List<Fragment> Datas) {
        super(fm);
        this.Datas=Datas;
    }

    @Override
    public Fragment getItem(int position) {
        return Datas.get(position);
    }

    @Override
    public int getCount() {
        return Datas.size();
    }
}
