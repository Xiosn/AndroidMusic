package com.viewpagertext.fragments;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.transition.ArcMotion;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.viewpagertext.R;
import com.viewpagertext.adapters.ListRecAdapter;
import com.viewpagertext.constructor.MessageEvent;
import com.viewpagertext.databinding.ActivityMovieDetailBinding;
import com.viewpagertext.utils.CommonUtils;
import com.viewpagertext.utils.CustomChangeBounds;
import com.viewpagertext.utils.StatusBarUtil;
import com.viewpagertext.views.MyNestedScrollView;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * name:小龙虾
 * time:2019.5.4
 * Type:详情页Fragment
 */

public class ListFragment extends Fragment {


    public final static String IMAGE_URL_LARGE ="https://img3.doubanio.com/view/subject/m/public/s4477716.jpg";
    public final static String IMAGE_URL_MEDIUM = "https://graph.baidu.com/resource/1065f6950f399b9f27a8a01557811529.jpg";
    public final static String IMAGE_URL_SMALL = "https://img3.doubanio.com/view/subject/s/public/s4477716.jpg";
    public final static String PARAM = "isRecyclerView";
    // 这个是高斯图背景的高度
    private int imageBgHeight;
    // 在多大范围内变色
    private int slidingDistance;
    private boolean isRecyclerView;
    private ActivityMovieDetailBinding binding;
    private List<String> mDatas;
    private ViewPager viewPager;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=DataBindingUtil.inflate(inflater, R.layout.activity_movie_detail, container, false);
        EventBus.getDefault().register(this);//注册消息
        if (getActivity().getIntent() != null) {
            isRecyclerView = getActivity().getIntent().getBooleanExtra(PARAM, true);
        }

        setTitleBar();
        setPicture();
        initSlideShapeTheme();


        // RecyclerView列表显示
        setAdapter();

        viewPager=getActivity().findViewById(R.id.play_viewpager);
        return binding.getRoot();
    }


    /**
     * 一般图片和高斯背景图
     */
    private void setPicture() {
//        Glide.with(getActivity())
//                .load(IMAGE_URL_LARGE)
//                .override((int)CommonUtils.getDimens(R.dimen.dp_140), (int) CommonUtils.getDimens(R.dimen.dp_140))
//                .into(binding.include.ivOnePhoto);

        // "14":模糊度；"3":图片缩放3倍后再进行模糊
        Glide.with(getActivity())
                .load(R.mipmap.stackblur_default)
                .error(R.mipmap.stackblur_default)
                .placeholder(R.mipmap.stackblur_default)
//                .crossFade(3000)//动画效果时长
                .dontAnimate()//取消动画效果
//                .bitmapTransform(new BlurTransformation(getActivity(), 14, 3))
                .into(binding.include.imgItemBg);
    }

    /**
     * 接收FindFragment中RecyclerView的Item消息类
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.POSTING,sticky = true)
    public void messageEventBus(MessageEvent event){
         binding.titleToolBar.setTitle(event.getFindGridItemName());
         binding.include.ivOnePhoto.setImageBitmap(event.getImage());
    }

    /**
     * 设置RecyclerView
     */
    private void setAdapter() {
        binding.tvTxt.setVisibility(View.GONE);
        binding.xrvList.setVisibility(View.VISIBLE);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.xrvList.setLayoutManager(mLayoutManager);
        // 需加，不然滑动不流畅
        binding.xrvList.setNestedScrollingEnabled(false);
        binding.xrvList.setHasFixedSize(false);
        initData();
        final ListRecAdapter adapter = new ListRecAdapter(getActivity(),mDatas);
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener(new ListRecAdapter.OnRecyclerViewItemClickListener(){

            @Override
            public void onItemClick(View view, int postion) {
                viewPager.setCurrentItem(1);
            }
        });
        binding.xrvList.setAdapter(adapter);
    }

    protected void initData(){
        mDatas=new ArrayList<String>();
        for (int i=1;i<31;i++){
            mDatas.add(""+i);
        }
    }
    /**
     * toolbar设置
     */
    private void setTitleBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.titleToolBar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back);
        }
        binding.titleToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }



    /**
     * 初始化滑动渐变
     */
    private void initSlideShapeTheme() {

        setImgHeaderBg();

        // toolbar的高度
        int toolbarHeight = binding.titleToolBar.getLayoutParams().height;
        // toolbar+状态栏的高度
        final int headerBgHeight = toolbarHeight + StatusBarUtil.getStatusBarHeight(getActivity());

        // 使背景图向上移动到图片的最底端，保留toolbar+状态栏的高度
        binding.ivTitleHeadBg.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams params = binding.ivTitleHeadBg.getLayoutParams();
        ViewGroup.MarginLayoutParams ivTitleHeadBgParams = (ViewGroup.MarginLayoutParams) binding.ivTitleHeadBg.getLayoutParams();
        int marginTop = params.height - headerBgHeight;
        ivTitleHeadBgParams.setMargins(0, -marginTop, 0, 0);
        binding.ivTitleHeadBg.setImageAlpha(0);

        // 为头部是View的界面设置状态栏透明
        StatusBarUtil.setTranslucentImageHeader(getActivity(), 0, binding.titleToolBar);

        ViewGroup.LayoutParams imgItemBgparams = binding.include.imgItemBg.getLayoutParams();
        // 获得高斯图背景的高度
        imageBgHeight = imgItemBgparams.height;

        // 监听改变透明度
        initScrollViewListener();

    }


    /**
     * 加载titlebar背景,加载后将背景设为透明
     */
    private void setImgHeaderBg() {
        Glide.with(getActivity())
                .load(ListFragment.IMAGE_URL_MEDIUM)
//                .placeholder(R.mipmap.stackblur_default)
                .error(R.mipmap.stackblur_default)
                .bitmapTransform(new BlurTransformation(getActivity(), 14, 3))// 设置高斯模糊
                .listener(new RequestListener<String, GlideDrawable>() {//监听加载状态
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        binding.titleToolBar.setBackgroundColor(Color.TRANSPARENT);
                        binding.ivTitleHeadBg.setImageAlpha(0);
                        binding.ivTitleHeadBg.setVisibility(View.VISIBLE);
                        return false;
                    }
                }).into(binding.ivTitleHeadBg);
    }

    private void initScrollViewListener() {
        // 为了兼容api23以下
        binding.nsvScrollview.setOnMyScrollChangeListener(new MyNestedScrollView.ScrollInterface() {
            @Override
            public void onScrollChange(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                scrollChangeHeader(scrollY);
            }
        });

        int titleBarAndStatusHeight = (int) (CommonUtils.getDimens(R.dimen.dp_58) + StatusBarUtil.getStatusBarHeight(getActivity()));
        slidingDistance = imageBgHeight - titleBarAndStatusHeight - (int) (CommonUtils.getDimens(R.dimen.dp_30));
    }

    /**
     * 根据页面滑动距离改变Header透明度方法
     */
    private void scrollChangeHeader(int scrolledY) {

//        DebugUtil.error("---scrolledY:  " + scrolledY);
//        DebugUtil.error("-----slidingDistance: " + slidingDistance);

        if (scrolledY < 0) {
            scrolledY = 0;
        }
        float alpha = Math.abs(scrolledY) * 1.0f / (slidingDistance);
        Drawable drawable = binding.ivTitleHeadBg.getDrawable();
//        DebugUtil.error("----alpha:  " + alpha);

        if (drawable != null) {
            if (scrolledY <= slidingDistance) {
                // title部分的渐变
                drawable.mutate().setAlpha((int) (alpha * 255));
                binding.ivTitleHeadBg.setImageDrawable(drawable);
            } else {
                drawable.mutate().setAlpha(255);
                binding.ivTitleHeadBg.setImageDrawable(drawable);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.xrvList.setFocusable(false);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);//反注册
    }


}

