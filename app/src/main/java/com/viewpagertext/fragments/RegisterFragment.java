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
 * Type:注册页Fragment
 */

public class RegisterFragment extends Fragment {

    private TextView userLoginBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_register,null);
        userLoginBtn = v.findViewById(R.id.userLoginBtn);
        userLoginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction()
                        //替换为LoginFragment
                        .replace(R.id.frameContain,new LoginFragment())
                        .commit();
            }
        });

        return v;
    }

}
