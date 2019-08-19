package com.viewpagertext.fragments;

import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.viewpagertext.Lrc.DefaultLrcBuilder;
import com.viewpagertext.Lrc.ILrcBuilder;
import com.viewpagertext.Lrc.ILrcViewListener;
import com.viewpagertext.Lrc.LrcRow;
import com.viewpagertext.R;
import com.viewpagertext.constructor.ListToPlayEvent;
import com.viewpagertext.databinding.FragmentSongBinding;
import com.viewpagertext.help.MediaPlayerHelp;
import com.viewpagertext.utils.HttpUtil;
import com.viewpagertext.utils.TimeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.media.MediaPlayer.OnPreparedListener;
import android.widget.SeekBar;
import android.widget.Toast;

import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.Call;
import okhttp3.Response;

import static com.viewpagertext.help.MediaPlayerHelp.mMediaPlayer;


/**
 * name:小龙虾
 * time:2019.5.4
 * Type:歌曲播放页Fragment
 */

public class SongFragment extends Fragment {

    private ViewPager headlow;
    private FragmentSongBinding binding;
    private String LrcPath,Url,netPath;
    //更新歌词的频率，每秒更新一次
    private int mPalyTimerDuration = 1000;
    //更新歌词的定时器
    private Timer mTimer;
    //更新歌词的定时任务
    private TimerTask mTask;
    //    private MediaPlayer mPlayer;
    private int p;
    private boolean isPlaying;
    private MediaPlayerHelp mMediaPlayerHelp;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_song,container,false);
        EventBus.getDefault().register(this);//注册消息
        initstaus();//设置状态栏颜色
        initView();//初始化控件
        mMediaPlayerHelp=MediaPlayerHelp.getInstance(getActivity());
        return binding.getRoot();
    }

    //接收ListFragment中传递过来的消息数据
    @Subscribe(threadMode = ThreadMode.POSTING,sticky = true)
    public void NetPlayEventBus(ListToPlayEvent event){
        //网络播放页背景图
        Glide.with(getActivity())
                .load(event.getPic())
                .error(R.mipmap.stackblur_default)
                .bitmapTransform(new BlurTransformation(getActivity(), 200, 3))// 设置高斯模糊
                .into(binding.bgPhoto);

        binding.NetPlayTitleMusic.setText(event.getName()+" - "+event.getSinger());//播放页歌曲标题
        LrcPath=event.getLrc();//获取歌单详情页传递过来的歌词API接口
        Url=event.getUrl();//获取播放地址
        binding.playTime.setText(event.getTime());
        sendRequestWithOkHttp();
    }
    private void sendRequestWithOkHttp(){
        HttpUtil.sendOkHttpRequest(LrcPath,new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String lrc=response.body().string();//得到服务器返回歌词内容
                //解析歌词构造器
                ILrcBuilder builder = new DefaultLrcBuilder();
                //解析歌词返回LrcRow集合
                List<LrcRow> rows = builder.getLrcRows(lrc);
                binding.lrcView.setLrc(rows);//将得到的歌词集合传给mLrcView用来展示
                if (mMediaPlayer.isPlaying()){
                      stopLrcPlay();
                      stopMusic(Url);
                   }else{
                      beginLrcPlay();
//                    playMusic(Url);
                    }
            }
        });
    }

    /**
     * 开始播放歌曲并同步展示歌词
     */
    public void beginLrcPlay(){
        try {
            mMediaPlayer.setDataSource(Url);//设置歌词播放地址
            //准备播放歌曲监听
            mMediaPlayer.setOnPreparedListener(new OnPreparedListener() {
                //准备完毕
                public void onPrepared(MediaPlayer mp) {
//                    mp.start();
                    if(mTimer == null){
                        mTimer = new Timer();
                        mTask = new LrcTask();
                        mTimer.scheduleAtFixedRate(mTask, 0, mPalyTimerDuration);
                    }
                }
            });
            //歌曲播放完毕监听
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    stopLrcPlay();
                    binding.button2.setImageResource(R.mipmap.m_icon_player_play_normal);
                }
            });
//            //准备播放歌曲
            mMediaPlayer.prepare();
//            //开始播放歌曲
//            playMusic(Url);
//            mMediaPlayer.start();
//            playMusic(Url);
//            binding.button2.setImageResource(R.mipmap.m_icon_player_pause_normal);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 停止展示歌曲
     */
    public void stopLrcPlay(){
        if(mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }
    }

    /**
     * 展示歌曲的定时任务
     */
    class LrcTask extends TimerTask{

        public void run() {
            //获取歌曲播放的位置
            final long timePassed = mMediaPlayer.getCurrentPosition();
            binding.progress.setMax(mMediaPlayer.getDuration());//设置SeekBar的长度
            getProgress();
//            Log.d("网络歌曲播放界面","歌曲播放位置的时间点："+timePassed);
            if (getActivity()==null) return;
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
//                    //滚动歌词
                    binding.lrcView.seekLrcToTime(timePassed);
                }
            });

        }
    }

    public void initView(){//按钮事件

        headlow=getActivity().findViewById(R.id.play_viewpager);
        binding.playLow.setOnClickListener(new View.OnClickListener() {//下三角形返回
            @Override
            public void onClick(View v) {
                headlow.setCurrentItem(0);//跳转到歌曲列表页面
            }
        });
        binding.netPlayBack.setOnClickListener(new View.OnClickListener() {//左返回
            @Override
            public void onClick(View v) {
                headlow.setCurrentItem(0);//跳转到歌曲列表页面
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {//播放按钮监听
            @Override
            public void onClick(View v) {
                if (binding.NetPlayTitleMusic.equals("暂无歌曲")){
                    Toast.makeText(getActivity(),"找不到歌曲",Toast.LENGTH_SHORT).show();
                }
                if (mMediaPlayer.isPlaying()){
//                    stopMusic(Url);
//                    stopLrcPlay();
                    mMediaPlayer.pause();
                    binding.button2.setImageResource(R.mipmap.m_icon_player_play_normal);
//                    binding.button2.setImageResource(R.mipmap.m_icon_player_pause_normal);
                }else{
//                    beginLrcPlay();
                    playMusic(Url);
                    binding.button2.setImageResource(R.mipmap.m_icon_player_pause_normal);
//                    mMediaPlayer.start();
                }//播放按钮显示图标判断
            }
        });


        binding.progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //取消timer任务
                stopLrcPlay();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //获取进度条的进度
                int p = seekBar.getProgress();
                //将进度条的进度赋值给歌曲
                mMediaPlayer.seekTo(p);

                //开始音乐继续获取歌曲的进度
                getProgress();
            }
        });//进度条

        binding.lrcView.setListener(new ILrcViewListener() {//滑动歌词播放对应歌词
            @Override
            public void onLrcSeeked(int newPosition, LrcRow row) {
                if (mMediaPlayer != null) {
//                    Log.d(TAG, "onLrcSeeked:" + row.time);
                    mMediaPlayer.seekTo((int) row.time);
                }

            }
        });
    }

    //实时获取歌曲的进度
    private void getProgress() {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                //获取歌曲的进度
                p = mMediaPlayer.getCurrentPosition();
                //将获取歌曲的进度赋值给seekbar
                binding.progress.setProgress(p);
                if (getActivity()==null) return;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.playTime1.setText(TimeUtil.ShowTime(p));
                    }
                });
            }
        }, 0, 1000);
    }


    //播放歌曲
    private void playStart(){
        mMediaPlayer.start();
        binding.button2.setImageResource(R.mipmap.m_icon_player_pause_normal);
    }
    //状态栏设置
    private void initstaus(){
        if (Build.VERSION.SDK_INT>=21){
            View decorView = getActivity().getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册
//        if (mMediaPlayer != null) {
//            mMediaPlayer.stop();
//        }
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
//            Toast.makeText(getActivity(),"播放继续",Toast.LENGTH_SHORT).show();
            mMediaPlayer.start();
        }else{
            mMediaPlayerHelp.setPath(path);
            mMediaPlayerHelp.setOnMediaPlayerHelperListener(new MediaPlayerHelp.OnMediaPlayerHelperListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mMediaPlayer.start();
                    if(mTimer == null){
                        mTimer = new Timer();
                        mTask = new LrcTask();
                        mTimer.scheduleAtFixedRate(mTask, 0, mPalyTimerDuration);
                    }
//                    Toast.makeText(getActivity(),"播放开始",Toast.LENGTH_SHORT).show();
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
//            Toast.makeText(getActivity(),"播放暂停",Toast.LENGTH_SHORT).show();

        }else{
//        itemIvPlay.setVisibility(View.VISIBLE);
            mMediaPlayerHelp.setPath(path);
//          mMediaPlayerHelp.stop();

        }
    }

}
