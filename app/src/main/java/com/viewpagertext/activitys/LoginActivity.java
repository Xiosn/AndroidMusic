package com.viewpagertext.activitys;

import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import com.viewpagertext.R;
import com.viewpagertext.fragments.LoginFragment;

/**
 * name:小龙虾
 * time:2019.5.4
 */

public class LoginActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();//Fragment切换
    }


    /**
     * 登录/注册页面切换
     * 替换为LoginFragment碎片
     */
    private void initView(){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction ft=fragmentManager.beginTransaction();
        ft.replace(R.id.frameContain,new LoginFragment());
        ft.commit();
    }

    /**
     * 登录界面Back返回按钮的回调
     */
    public void backBtnOnClick(View v){
        finish();
    }

}
