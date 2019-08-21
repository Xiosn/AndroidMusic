package com.viewpagertext.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.viewpagertext.R;
import com.viewpagertext.json.HotVideo;
import java.util.ArrayList;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * name:小龙虾
 * time:2019.5.28
 * type:获取本地音乐的适配器
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<HotVideo.ResultBean> mList;

    public VideoAdapter(Context context, ArrayList<HotVideo.ResultBean> list) {
        this.context = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.friend_video_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        Glide.with(context).load(mList.get(i).getHeader()).into(myViewHolder.video_pic);
        myViewHolder.video_name.setText(mList.get(i).getName());
        myViewHolder.video_text.setText(mList.get(i).getText());
        myViewHolder.video_time.setText(mList.get(i).getPasstime());
        myViewHolder.videoplayer.setUp(mList.get(i).getVideo(),JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,mList.get(i).getName());
        Glide.with(context).load(mList.get(i).getThumbnail()).into(myViewHolder.videoplayer.thumbImageView);


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }




    class MyViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView video_pic;
        TextView video_name, video_text, video_time;
        JZVideoPlayerStandard videoplayer;

        public MyViewHolder(View itemView) {
            super(itemView);
            video_pic = itemView.findViewById(R.id.video_pic);
            video_name = itemView.findViewById(R.id.video_name);
            video_text = itemView.findViewById(R.id.video_text);
            videoplayer = itemView.findViewById(R.id.videoplayer);
            video_time = itemView.findViewById(R.id.video_time);
        }
    }

}