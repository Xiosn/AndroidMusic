package com.viewpagertext.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;
import java.util.List;

public class FindFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mDatas;


    public FindFragmentStatePagerAdapter(FragmentManager fm, List<Fragment> mDatas) {
        super(fm);
        this.mDatas=mDatas;
    }

    /**
     * 获取子项
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return mDatas.get(position);
    }


    /**
     * 获取子项数量
     * @return
     */
    @Override
    public int getCount() {
        return mDatas.size();
    }

    /**
     * 初始化子项
     * @param container
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    /**
     * 销毁子项
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }
}
