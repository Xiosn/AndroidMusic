package com.viewpagertext.help;

import android.content.Context;
import android.media.MediaPlayer;
import java.io.IOException;

public class MediaPlayerHelp {

    private static MediaPlayerHelp instance;
    private Context mContext;
    public static MediaPlayer mMediaPlayer;
    private OnMediaPlayerHelperListener onMediaPlayerHelperListener;
    private String mPath;

    public void setOnMediaPlayerHelperListener(OnMediaPlayerHelperListener onMediaPlayerHelperListener) {
        this.onMediaPlayerHelperListener = onMediaPlayerHelperListener;
    }

    //单例器
    public static MediaPlayerHelp getInstance(Context context){
        if (instance==null){
            synchronized(MediaPlayerHelp.class){
                if (instance==null){
                    instance=new MediaPlayerHelp(context);
                }
            }
        }
        return instance;
    }

    public MediaPlayerHelp(Context context){
        mContext=context;
        mMediaPlayer=new MediaPlayer();
    }

    /**
     *  1、setPath:当前需要播放的音乐
     *  2、start:播放音乐
     *  3、pause:暂停播放
     */


    /***
     * 设置路径
     * 当前需要播放的音乐
     * @param path
     */
    public void setPath(String path){
        /**
         * 1、音乐正在播放，重置音乐播放状态
         * 2、设置播放音乐路径
         * 3、准备播放
         */

        mPath=path;
//        1、音乐正在播放，重置音乐播放状态
//        if (mMediaPlayer.isPlaying()){
            mMediaPlayer.reset();
//        }

//        2、设置播放音乐路径
            try {
                mMediaPlayer.setDataSource(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
//        3、准备播放
        mMediaPlayer.prepareAsync();
//        捕获异步加载完成的通知
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // 1、音乐正在播放，重置音乐播放状态
                if (onMediaPlayerHelperListener!=null){
                    onMediaPlayerHelperListener.onPrepared(mp);
                }
            }
        });
    }

    /**
     * 返回正在播放的路径
     */
    public String getPath(){
        return mPath;
    }

    /**
     * 播放音乐
     */
    public void start(){
        if (mMediaPlayer.isPlaying()) return;
        mMediaPlayer.start();
    }

    /**
     * 暂停播放
     */
    public void pause(){
        mMediaPlayer.pause();
    }

    public interface OnMediaPlayerHelperListener{
        void onPrepared(MediaPlayer mp);
    }

    /**
     * 停止音乐
     */
    public void stop(){
        mMediaPlayer.stop();
    }

    /**
     * 释放资源
     */
    public void release(){
        mMediaPlayer.release();
    }
}
