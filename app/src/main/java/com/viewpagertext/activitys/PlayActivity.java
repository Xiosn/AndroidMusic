package com.viewpagertext.activitys;


import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.viewpagertext.R;
import com.viewpagertext.adapters.PlayFragmentPagerAdapter;
import com.viewpagertext.databinding.ActivityPlayBinding;
import com.viewpagertext.fragments.ListFragment;
import com.viewpagertext.fragments.SongFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * name:小龙虾
 * time:2019.5.4
 */

public class PlayActivity extends AppCompatActivity {

    private List<Fragment> mDatas;
    private ActivityPlayBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding=DataBindingUtil.setContentView(this,R.layout.activity_play);

        initView();//实例化控件
    }

   private void initView(){
       initDatas();
       PlayFragmentPagerAdapter playFragmentPagerAdapter=new PlayFragmentPagerAdapter(getSupportFragmentManager(),mDatas);
       binding.playViewpager.setAdapter(playFragmentPagerAdapter);
   }

   private void initDatas(){
        mDatas=new ArrayList<>();
        mDatas.add(new ListFragment());
        mDatas.add(new SongFragment());
   }
}


