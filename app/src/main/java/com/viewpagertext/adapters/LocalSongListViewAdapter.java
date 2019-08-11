package com.viewpagertext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.viewpagertext.R;
import com.viewpagertext.constructor.Song;
import com.viewpagertext.utils.LoadSongUtils;
import java.util.List;

/**
 * name:小龙虾
 * time:20.19.5.22
 * type:获取本地音乐的适配器
 */

public class LocalSongListViewAdapter extends BaseAdapter {
    Context context;
    List<Song> list;
//    InnerItemOnclickListener mListener;

    public LocalSongListViewAdapter(Context context, List<Song> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Myholder myholder;
        if (convertView == null) {
            myholder = new Myholder();
            convertView = LayoutInflater.from(context).inflate(R.layout.load_item, null);
            myholder.t_position = convertView.findViewById(R.id.t_postion);
            myholder.t_song = convertView.findViewById(R.id.t_song);
            myholder.t_singer = convertView.findViewById(R.id.t_singer);
//            myholder.localmenu=convertView.findViewById(R.id.localmenu);//子项菜单按钮
//          myholder.t_duration = view.findViewById(R.id.t_duration);

            convertView.setTag(myholder);

        } else {
            myholder = (Myholder) convertView.getTag();
        }

        myholder.t_song.setText(list.get(position).song.toString());
        myholder.t_singer.setText(list.get(position).singer.toString());
        String time = LoadSongUtils.formatTime(list.get(position).duration);
//          myholder.t_duration.setText(time);
        myholder.t_position.setText(position + 1 + "");
//        myholder.localmenu.setOnClickListener(this);

        return convertView;
    }

    public final static class Myholder {
        TextView t_position, t_song, t_singer, t_duration;
        ImageView localmenu;
    }


//    public interface InnerItemOnclickListener {
//        void itemClick(View v);
//    }

//    public void setOnInnerItemOnClickListener(InnerItemOnclickListener listener){
//        this.mListener=listener;
//    }

//    @Override
//    public void onClick(View v) {
//        mListener.itemClick(v);
//    }
}
