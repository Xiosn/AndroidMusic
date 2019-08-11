package com.viewpagertext.activitys;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import com.viewpagertext.R;
import com.viewpagertext.utils.TypefacesUtil;
import com.viewpagertext.views.Lead;
import com.viewpagertext.views.LeadTextView;

/**
 * name:小龙虾
 * time:2019.5.4
 * Type:启动欢迎页
 */


public class WelcomeActivity extends AppCompatActivity {

    LeadTextView leadTv;
    private static final int mDuration = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        initView();//延时秒数
        initLeadText();//添加启动页动画
    }


    private void initLeadText(){
        leadTv=findViewById(R.id.my_text_view);
        leadTv.setTypeface(TypefacesUtil.get(this,"Satisfy-Regular.ttf"));
        final Lead lead = new Lead(mDuration);
        lead.start(leadTv);
        new Lead(mDuration).start(leadTv);
    }


    private void initView(){
       Thread td=new Thread(new Runnable() {
           @Override
           public void run() {
               try {
                   Thread.sleep(3*900);
                   toMain();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       });
       td.start();
    }


    /**
     * 跳转到主页MainActivity
     */
    private void toMain(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);//过渡动画
    }
}
