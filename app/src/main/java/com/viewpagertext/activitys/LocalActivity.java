package com.viewpagertext.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import com.viewpagertext.R;
import com.viewpagertext.adapters.LocalAdapter;
import com.viewpagertext.databinding.ActivityLocalBinding;
import com.viewpagertext.fragments.localSongFragment;
import com.viewpagertext.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * name :小龙虾
 * time :2019.5.21
 * type :本地音乐YViewPager切换
 */

public class LocalActivity extends AppCompatActivity {

    private List<Fragment> mDatas;
    private ActivityLocalBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_local);
        initView();
    }
    private void initView(){
        initDatas();
        LocalAdapter localAdapter=new LocalAdapter(getSupportFragmentManager(),mDatas);
        binding.localYViewPager.setAdapter(localAdapter);
    }

    private void initDatas(){
        mDatas=new ArrayList<>();
        mDatas.add(new localSongFragment());
//        mDatas.add(new LocalPlayFragment());
    }
}
