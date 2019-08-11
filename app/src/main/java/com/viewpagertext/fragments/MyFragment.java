package com.viewpagertext.fragments;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.viewpagertext.R;
import com.viewpagertext.activitys.LocalActivity;
import com.viewpagertext.databinding.FragmentMyBinding;

/**
 * name:小龙虾
 * time:2019.5.4
 * Type:我的页Fragment
 */

public class MyFragment extends Fragment {

    FragmentMyBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_my,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView(){
        binding.loadMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LocalActivity.class));
            }
        });
    }
}
