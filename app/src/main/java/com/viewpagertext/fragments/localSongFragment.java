package com.viewpagertext.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.viewpagertext.R;
import com.viewpagertext.activitys.MainActivity;
import com.viewpagertext.adapters.LocalSongListViewAdapter;
import com.viewpagertext.constructor.Song;
import com.viewpagertext.help.MediaPlayerHelp;
import com.viewpagertext.utils.LoadSongUtils;
import com.viewpagertext.views.MyRoundedImageView;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class localSongFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView mylist;
    private List<Song> list;
    private MediaPlayerHelp mMediaPlayerHelp;
    private SwipeRefreshLayout refreshLayout;
    private boolean isPlaying;
    private LocalSongListViewAdapter myAdapter;
    private View v;
    private BottomSheetDialog bottomSheetDialog;
    private String song,singer,album,duration;
    private long size;
    private MyRoundedImageView localDialogIcon;
    private Bitmap bitmap;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_localsong,container,false);
        list = new ArrayList<>();

        initCheckSelfPermission();//运行时检查权限

        initView();//控件实例化

        initRefreshLayout();//下拉刷新
        return v;
    }

    private void initView(){
        mylist = v.findViewById(R.id.mylist);
        mMediaPlayerHelp=MediaPlayerHelp.getInstance(getActivity());
        mylist.setDivider(null);
        refreshLayout =v.findViewById(R.id.refreshLayout);
        MyRoundedImageView local_back_icon = v.findViewById(R.id.local_back_icon);
        local_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
        myAdapter = new LocalSongListViewAdapter(getActivity(), list);
        mylist.setOnItemClickListener(this);
        mylist.setAdapter(myAdapter);


    }

    @Override//ListView子项添加点击事件
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


        song=list.get(position).song;//获取歌名
        singer=list.get(position).singer;//获取歌手
        size=list.get(position).size;//歌曲大小
        duration = LoadSongUtils.formatTime(list.get(position).duration);//歌曲时长
        album=list.get(position).album;//歌曲专辑

        long albumId=list.get(position).albumId;
        bitmap=LoadSongUtils.getMusicBitemp(getActivity(), position, albumId);

        LinearLayout intoPlayPage=view.findViewById(R.id.intoPlayPage);//item中间文字
        intoPlayPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(),"分享",Toast.LENGTH_SHORT).show();
                String p=list.get(position).path;//获得歌曲的地址

                if (isPlaying){
                    stopMusic(p);
                }else{
                    playMusic(p);
                }
            }
        });

        initDialog();

    }

    private void initDialog() {
        View view = View.inflate(getActivity(), R.layout.local_dialog, null);
        TextView dialog_song=view.findViewById(R.id.dialog_song);//歌名
        dialog_song.setText(song);
        TextView dialog_album=view.findViewById(R.id.dialog_album);//专辑
        dialog_album.setText(album);
        TextView dialog_singer=view.findViewById(R.id.dialog_singer);//歌手
        dialog_singer.setText(singer);
        TextView dialog_singer2=view.findViewById(R.id.dialog_singer2);//歌手
        dialog_singer2.setText(singer);
        TextView dialog_time=view.findViewById(R.id.dialog_time);//时长
        dialog_time.setText(duration);
        TextView dialog_size=view.findViewById(R.id.dialog_size);//歌曲大小
        dialog_size.setText(((new DecimalFormat("#.00")).format(Long.valueOf(size).floatValue()/1000000)));
        localDialogIcon=view.findViewById(R.id.localDialogIcon);
        MyRoundedImageView localDialogIcon=view.findViewById(R.id.localDialogIcon);//歌手
        localDialogIcon.setImageBitmap(bitmap);

        bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//设置BottomSheetDialog圆角
        bottomSheetDialog.show();
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
        if (mMediaPlayerHelp.getPath()!=null
                && mMediaPlayerHelp.getPath().equals(path)){
            Toast.makeText(getActivity(),"播放继续",Toast.LENGTH_SHORT).show();
            mMediaPlayerHelp.start();
        }else{
            mMediaPlayerHelp.setPath(path);
            mMediaPlayerHelp.setOnMediaPlayerHelperListener(new MediaPlayerHelp.OnMediaPlayerHelperListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mMediaPlayerHelp.start();
                    Toast.makeText(getActivity(),"播放开始",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //停止音乐
    public void stopMusic(String path){
        isPlaying=false;
        if (mMediaPlayerHelp.getPath()!=null
                && mMediaPlayerHelp.getPath().equals(path)){
            mMediaPlayerHelp.pause();
            Toast.makeText(getActivity(),"播放暂停",Toast.LENGTH_SHORT).show();

        }else{
//        itemIvPlay.setVisibility(View.VISIBLE);
            mMediaPlayerHelp.setPath(path);
            mMediaPlayerHelp.stop();

        }
    }

}
