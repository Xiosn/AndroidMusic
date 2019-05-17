package com.viewpagertext.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.viewpagertext.R;

/**
 * name:小龙虾
 * time:2019.5.4
 * Type:我的页Fragment
 */

public class MyFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     return inflater.inflate(R.layout.fragment_my,container,false);
    }
}
