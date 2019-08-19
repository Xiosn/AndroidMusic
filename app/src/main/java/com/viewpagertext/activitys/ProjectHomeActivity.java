package com.viewpagertext.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.webkit.WebViewClient;
import com.viewpagertext.R;
import com.viewpagertext.databinding.ActivityProjectHomeBinding;
import com.viewpagertext.utils.StatusBarUtil;

/**
 * name:小龙虾
 * time:2019.6.29
 */
public class ProjectHomeActivity extends AppCompatActivity {

    private  ActivityProjectHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_home);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_project_home);
        StatusBarUtil.StatusBarTextColor(this,true);
        initView();
    }

    private void initView(){
        binding.ProjectHomeWebView.getSettings().setJavaScriptEnabled(true);
        binding.ProjectHomeWebView.setWebViewClient(new WebViewClient());
        binding.ProjectHomeWebView.loadUrl("https://github.com/hsxiaodev/Share-Music");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
