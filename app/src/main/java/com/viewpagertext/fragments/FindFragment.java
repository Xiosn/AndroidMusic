package com.viewpagertext.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.viewpagertext.R;
import com.viewpagertext.activitys.PlayActivity;
import com.viewpagertext.adapters.BannerViewHolder;
import com.viewpagertext.adapters.MusicGridAdapter;
import com.viewpagertext.constructor.MessageEvent;
import com.viewpagertext.views.GridSpaceitemDecoration;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;

/**
 * name:小龙虾
 * time:2019.5.4
 * Type:发现页Fragment
 */

public class FindFragment extends Fragment {

    private MZBannerView mzBanner;
    private RecyclerView rv_Grid;
    private MusicGridAdapter musicGridAdapter;
    private TextView grid_text;
    private ImageView grid_image;
    private int[] RES={R.mipmap.banner1,R.mipmap.banner2,R.mipmap.banner3,R.mipmap.banner4,R.mipmap.banner5,R.mipmap.banner6};
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_find,container,false);
        initBanner(view);//Banner
        initRvGird(view);//RecyclerViewGrid
        return view;
    }

    public void initBanner(View view) {

        List<Integer> list=new ArrayList<>();
        for (int i=0;i<RES.length;i++){
            list.add(RES[i]);
        }

        mzBanner=view.findViewById(R.id.banner);
        mzBanner.setPages(list, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
    }

    public void initRvGird(View view){
        rv_Grid=view.findViewById(R.id.rv_Grid);
        rv_Grid.setLayoutManager(new GridLayoutManager(getActivity(),3));
        rv_Grid.addItemDecoration(new GridSpaceitemDecoration(getResources().getDimensionPixelSize(R.dimen.dp_5),rv_Grid));
        rv_Grid.setFocusable(false);//获取焦点
        musicGridAdapter=new MusicGridAdapter(getActivity());

        //item子项跳转
        musicGridAdapter.setOnItemClickListener(new MusicGridAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view , int position){
                Intent intent=new Intent(getActivity(),PlayActivity.class);

                grid_text = view.findViewById(R.id.grid_song_name);//子项的名称
                String ft=grid_text.getText().toString();
                grid_image=view.findViewById(R.id.iv_Grid_Icon);//子项的图片
                grid_image.setDrawingCacheEnabled(true);
                Bitmap bitmap=Bitmap.createBitmap(grid_image.getDrawingCache());
                EventBus.getDefault().postSticky(new MessageEvent(ft,bitmap,null,null));//发送
                grid_image.setDrawingCacheEnabled(false);
               startActivity(intent);
            }
        });
        rv_Grid.setAdapter(musicGridAdapter);
    }
    @Override
    public void onPause() {
        super.onPause();
        mzBanner.pause();//暂停轮播
    }

    @Override
    public void onResume() {
        super.onResume();
        mzBanner.start();//开始轮播
    }
}
