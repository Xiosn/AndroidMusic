package com.viewpagertext.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.viewpagertext.R;

/**
 * name:小龙虾
 * time:2019.5.4
 * Type:登录页Fragment
 */

public class LoginFragment extends Fragment {


    private TextView userRegisterBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_login,null);
//        initView();
        userRegisterBtn = v.findViewById(R.id.userRegisterBtn);
        userRegisterBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction()
                        //替换为RegisterFragment
                        .replace(R.id.frameContain,new RegisterFragment())

                        .commit();
            }
        });

        return v;
    }
}
