package com.viewpagertext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.viewpagertext.R;
import com.zhouwei.mzbanner.holder.MZViewHolder;

/**
 * name:小龙虾
 * time:2019.5.4
 * Type:发现页Banner轮播图适配器
 */

public class  BannerViewHolder implements MZViewHolder<Integer> {

    private ImageView imageView;
    @Override
    public View createView(Context context) {
        View view= LayoutInflater.from(context).inflate(R.layout.banner_item,null);
        imageView=view.findViewById(R.id.banner_image);
        return view;
    }

    @Override
    public void onBind(Context context, int i, Integer integer) {
        imageView.setImageResource(integer);
    }
}