package com.viewpagertext.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viewpagertext.R;

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
//                        .addToBackStack(null)
                        .commit();
            }
        });

        return v;
    }
//
//    public static Fragment newInstance() {
//        return new RegisterFragment();
//    }
}
