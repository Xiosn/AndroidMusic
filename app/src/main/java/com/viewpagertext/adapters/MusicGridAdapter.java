package com.viewpagertext.adapters;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.viewpagertext.R;
import com.viewpagertext.activitys.PlayActivity;
import com.viewpagertext.constructor.MessageEvent;
import com.viewpagertext.constructor.SongsDatas;
import com.viewpagertext.views.MyRoundedImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * name:小龙虾
 * time:2019.5.4
 * Type:发现页9宫格RecyclerView适配器
 */

public class MusicGridAdapter extends RecyclerView.Adapter<MusicGridAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<SongsDatas> mDatas;
    public static String FindImgUrlPath;
    public static long FindSongsId;
    public MusicGridAdapter(Context context,ArrayList<SongsDatas> mDatas) {
        this.mContext=context;
        this.mDatas=mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,  int viewType) {
       ViewHolder holder=new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.find_grid_music,viewGroup,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.grid_text.setText(mDatas.get(position).getTitle());
        Glide.with(mContext).load(mDatas.get(position).getImageurl()).into(viewHolder.grid_image);
        viewHolder.find_songs_play_num.setText(mDatas.get(position).getPlayCount());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mContext, PlayActivity.class);
                FindImgUrlPath=mDatas.get(position).getImageurl();//获取图片地址设置全局供ListFragment接收
                FindSongsId=mDatas.get(position).getId();//获取FindFragment歌单ID
//                Toast.makeText(mContext,"我的ID是："+FindSongsId,Toast.LENGTH_SHORT).show();
                //EventBus发送数据消息
                String ft=viewHolder.grid_text.getText().toString();
                long l=mDatas.get(position).getId();
                EventBus.getDefault().postSticky(new MessageEvent(ft));//发送
                mContext.startActivity(intent);
            }
        });
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView grid_text,find_songs_play_num;
        MyRoundedImageView grid_image;
        public ViewHolder(View view){
            super(view);
            grid_text = view.findViewById(R.id.grid_song_name);//子项的名称
            grid_image=view.findViewById(R.id.iv_Grid_Icon);//子项的图片
            find_songs_play_num=view.findViewById(R.id.find_songs_play_num);//歌单播放数量
        }
    }
}