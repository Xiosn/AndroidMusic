package com.viewpagertext.fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.viewpagertext.R;
import com.viewpagertext.constructor.Song;
import com.viewpagertext.help.MediaPlayerHelp;
import com.viewpagertext.utils.LoadSongUtils;
import java.util.ArrayList;
import java.util.List;


public class LoadSongFragment extends Fragment {

    private ListView mylist;
    private List<Song> list;
    private MediaPlayerHelp mMediaPlaerHelp;
    private SwipeRefreshLayout refreshLayout;
    private boolean isPlaying;
    private MyAdapter myAdapter;
    private ImageView itemIvPlay;
    private String mPath;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_load_song,container,false);

        list = new ArrayList<>();
        initCheckSelfPermission();//运行时检查权限
        initListViewItemClickEvent();//ListView子项添加点击事件，实现点击哪首音乐就进行播放
        initRefreshLayout();//下拉刷新

        myAdapter = new MyAdapter(list);
        mylist.setAdapter(myAdapter);

        return v;
    }

    private void initListViewItemClickEvent(){
        mMediaPlaerHelp=MediaPlayerHelp.getInstance(getActivity());
        mylist = getActivity().findViewById(R.id.mylist);
        mylist.setDivider(null);
        //给ListView添加点击事件，实现点击哪首音乐就进行播放
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String p=list.get(i).path;//获得歌曲的地址
                itemIvPlay = view.findViewById(R.id.local_play);
                if (isPlaying){
                    stopMusic(p);
                    itemIvPlay.setImageResource(R.mipmap.localplay);
                }else{

                    playMusic(p);
                    itemIvPlay.setImageResource(R.mipmap.localpause);
                }
            }
        });
    }


    private void initCheckSelfPermission(){

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        else{
            list = LoadSongUtils.getmusic(getActivity());
        }
    }

    //监听下拉刷新
    private void initRefreshLayout(){
        refreshLayout =getActivity().findViewById(R.id.refreshLayout);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light);
        //给swipeRefreshLayout绑定刷新监听
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //设置2秒的时间来执行以下事件
                refresh();
            }
        });

    }

    private void refresh(){
        //设置2秒的时间来执行以下事件
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        newData();
                        myAdapter.notifyDataSetChanged();
                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    // 更新本地音乐数据
    private void newData(){
        list.clear();
        list.addAll(LoadSongUtils.getmusic(getActivity()));
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getActivity(),"权限被拒绝",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    // ListView适配器
    class MyAdapter extends BaseAdapter {

        Context context;
        List<Song> list;

        public MyAdapter( List<Song> list) {
            this.context = getActivity();
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            Myholder myholder;
            if (view == null) {
                myholder = new Myholder();
                view = LayoutInflater.from(getActivity()).inflate(R.layout.load_item, null);
                myholder.t_position = view.findViewById(R.id.t_postion);
                myholder.t_song = view.findViewById(R.id.t_song);
                myholder.t_singer = view.findViewById(R.id.t_singer);
//              myholder.t_duration = view.findViewById(R.id.t_duration);
                myholder.intoPlayPage=view.findViewById(R.id.intoPlayPage);
//                itemIvPlay=view.findViewById(R.id.local_play);
                view.setTag(myholder);

            } else {
                myholder = (Myholder) view.getTag();
            }

            myholder.t_song.setText(list.get(i).song.toString());
            myholder.t_singer.setText(list.get(i).singer.toString());
            String time = LoadSongUtils.formatTime(list.get(i).duration);
//          myholder.t_duration.setText(time);
            myholder.t_position.setText(i + 1 + "");

            myholder.intoPlayPage.setOnClickListener(new View.OnClickListener() {//Item进入歌曲播放页
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),"测试", Toast.LENGTH_SHORT).show();
                }
            });

            return view;
        }

        class Myholder {
            TextView t_position, t_song, t_singer, t_duration;
            LinearLayout intoPlayPage;
        }
    }

    @Override//activity暂停交互时调用,用于刷新本地歌曲
    public void onPause() {
        super.onPause();
        newData();
        myAdapter.notifyDataSetChanged();
    }

    //播放音乐
    public void playMusic(String path){
//        mPath=path;
        isPlaying=true;
        /**
         * 1、当前音乐是否已经在播放的音乐
         * 2、如果当前的音乐已经是在播放的音乐的话，那么就直接执行start方法
         * 3、如果当前播放的音乐不是需要播放的音乐，那么就调用setPath的方法
         */
        if (mMediaPlaerHelp.getPath()!=null
                && mMediaPlaerHelp.getPath().equals(path)){
            mMediaPlaerHelp.start();
        }else{

            mMediaPlaerHelp.setPath(path);
            mMediaPlaerHelp.setOnMediaPlayerHelperListener(new MediaPlayerHelp.OnMediaPlayerHelperListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mMediaPlaerHelp.start();
                }
            });
        }
    }

    //切换播放状态
//    private void trigger(){
//
//        if (isPlaying){
//            stopMusic();
//
//        }else{
//            playMusic(mPath);
//        }
//    }

    //停止音乐
    public void stopMusic(String path){
        isPlaying=false;
        if (mMediaPlaerHelp.getPath()!=null
                && mMediaPlaerHelp.getPath().equals(path)){
            mMediaPlaerHelp.pause();

        }else{
//        itemIvPlay.setVisibility(View.VISIBLE);
            mMediaPlaerHelp.setPath(path);
            mMediaPlaerHelp.stop();
        }
    }
}
