package com.viewpagertext.activitys;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;
import com.makeramen.roundedimageview.RoundedImageView;
import com.viewpagertext.R;
import com.viewpagertext.adapters.HomeFragmentStatePagerAdapter;
import com.viewpagertext.fragments.FindFragment;
import com.viewpagertext.fragments.FriendFragment;
import com.viewpagertext.fragments.MyFragment;
import com.viewpagertext.utils.PermissionUtils;
import com.viewpagertext.utils.StatusBarUtil;
import com.viewpagertext.utils.UserUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * name:小龙虾
 * time:2019.5.4
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener{

    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private HomeFragmentStatePagerAdapter myFragmentStatePagerAdapter;
    private List<Fragment> mDatas;
    private TextView tv_my,tv_find,tv_friends,nav_close,username,btn_song_name,btn_singer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setTransparentForImageView(this,toolbar);
        StatusBarUtil.StatusBarTextColor(this,true);
        initData();//添加Fragment到 List<Fragment> mDatas集合
        initIdListener();//获取控件实例
        header_click();//动态加载nav头布局
        StatusBarUtil.statusBarVersion(this);
        PermissionUtils.initCheckSelfPermission(this);
        myFragmentStatePagerAdapter=new HomeFragmentStatePagerAdapter(getSupportFragmentManager(),mDatas);
        viewPager.setAdapter(myFragmentStatePagerAdapter);
        viewPager.setCurrentItem(1);//默认首次进入的页面
        setTitleTextViewColor(viewPager.getCurrentItem());//首次加载viewPager 页面对应的button的文字颜色

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    showDialog();
                }
                break;
        }
    }

    private void showDialog(){
        Dialog dialog= new AlertDialog.Builder(this)
                .setMessage("请授权必要权限")
                .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        PermissionUtils.initCheckSelfPermission(MainActivity.this);
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create();
        dialog.setCanceledOnTouchOutside(false);//点击屏幕不消失
        dialog.setCancelable(false);//点击返回键不消失
        dialog.show();
    }




    /**
     * 发现页面的事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_my:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_find:
                viewPager.setCurrentItem(1);
                break;
            case R.id.tv_friends:
                viewPager.setCurrentItem(2);
                break;
            case R.id.nav_close:
                System.exit(0);
                break;
            case R.id.username:
                Intent intent=new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    //nav布局Item点击项
    private void header_click(){
        NavigationView navigationView=findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.AppHome:
                        Intent intent=new Intent(MainActivity.this,ProjectHomeActivity.class);
                        startActivity(intent);
                }

                return true;
            }
        });

        View nav_header=navigationView.inflateHeaderView(R.layout.nav_header);
        username=nav_header.findViewById(R.id.username);
        username.setOnClickListener(this);
    }

    /**
     * 获取控件实例
     */
    private void initIdListener(){
        //toolbar
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //抽屉布局
        mDrawerLayout=findViewById(R.id.drawer_layout);
        viewPager=findViewById(R.id.viewPager);


        //toolbar_TextView
        tv_my=findViewById(R.id.tv_my);
        tv_find=findViewById(R.id.tv_find);
        tv_friends=findViewById(R.id.tv_friends);
        viewPager.setOnPageChangeListener(this);
        tv_my.setOnClickListener(this);
        tv_find.setOnClickListener(this);
        tv_friends.setOnClickListener(this);

        //nav的退出
        nav_close=findViewById(R.id.nav_close);
        nav_close.setOnClickListener(this);

        btn_song_name=findViewById(R.id.btn_song_name);
        btn_singer=findViewById(R.id.btn_singer);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                RoundedImageView MainButtomPic=findViewById(R.id.MainButtomPic);//Main底部图片动画
                Animation rotateAnimation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.main_bottom_pic_rotate);
                rotateAnimation.setInterpolator(new LinearInterpolator());
                MainButtomPic.startAnimation(rotateAnimation);
            }
        }).start();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }


    /**
     * Toolbar子项菜单点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                Toast.makeText(this,"搜索",Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }


    /**
     * 添加Fragment到 List<Fragment> mDatas集合
     */
    private void initData(){
        mDatas = new ArrayList<>();
        mDatas.add(new MyFragment());
        mDatas.add(new FindFragment());
        mDatas.add(new FriendFragment());
    }

    /**
     * 设置字体属性
     */
    private void setTexAttr(){
        UserUtils.setTextColor(tv_my,this,R.color.gray);//设置字体颜色
        UserUtils.setTextColor(tv_find,this,R.color.gray);
        UserUtils.setTextColor(tv_friends,this,R.color.gray);
        UserUtils.setTextSize(tv_my,16);//设置字体大小
        UserUtils.setTextSize(tv_find,16);
        UserUtils.setTextSize(tv_friends,16);
        UserUtils.setTypeface(tv_my,Typeface.NORMAL);//取消加粗
        UserUtils.setTypeface(tv_find,Typeface.NORMAL);
        UserUtils.setTypeface(tv_friends,Typeface.NORMAL);
    }


    /**
     * 设置titleTextView的颜色状态
     * @param currentItem
     */
    private void setTitleTextViewColor(int currentItem) {
        setTexAttr();
        switch (currentItem) {
            case 0:
                UserUtils.setTextColor(tv_my,this,R.color.black);
                UserUtils.setTypeface(tv_my,Typeface.BOLD);
                UserUtils.setTextSize(tv_my,18);
                break;
            case 1:
                UserUtils.setTextColor(tv_find,this,R.color.black);
                UserUtils.setTypeface(tv_find,Typeface.BOLD);
                UserUtils.setTextSize(tv_find,18);
                break;
            case 2:
                UserUtils.setTextColor(tv_friends,this,R.color.black);
                UserUtils.setTypeface(tv_friends,Typeface.BOLD);
                UserUtils.setTextSize(tv_friends,18);
                break;

        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int i) {
        setTitleTextViewColor(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        PermissionUtils.initCheckSelfPermission(this);
    }
}