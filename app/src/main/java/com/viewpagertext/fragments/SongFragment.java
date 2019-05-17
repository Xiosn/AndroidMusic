package com.viewpagertext.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.viewpagertext.R;


/**
 * name:小龙虾
 * time:2019.5.4
 * Type:歌曲播放页Fragment
 */

public class SongFragment extends Fragment {

    private View v;
    private ImageView play_low;
    ViewPager headlow;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.fragment_song,container,false);
        initstaus();//设置状态栏颜色
        initView();//初始化控件
        return v;
    }

    public void initView(){
        play_low=v.findViewById(R.id.play_low);
        headlow=getActivity().findViewById(R.id.play_viewpager);
        play_low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                headlow.setCurrentItem(0);//跳转到歌曲列表页面
            }
        });
    }

    private void initstaus(){
        if (Build.VERSION.SDK_INT>=21){
            View decorView = getActivity().getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
