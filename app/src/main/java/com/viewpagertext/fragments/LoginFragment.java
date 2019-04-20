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

//    protected void initView(){
//        TextView loginBtn=getActivity().findViewById(R.id.loginBtn);
//        loginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frameContain, new RegisterFragment());
//                transaction.commit();
//            }
//        });
//    }

}
