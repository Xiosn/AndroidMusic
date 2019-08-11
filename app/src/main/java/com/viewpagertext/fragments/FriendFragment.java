package com.viewpagertext.fragments;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagertext.R;
import com.viewpagertext.adapters.VideoAdapter;
import com.viewpagertext.databinding.FragmentFriendBinding;
import com.viewpagertext.json.HotVideo;
import com.viewpagertext.utils.HttpUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import cn.jzvd.JZVideoPlayer;
import okhttp3.Call;
import okhttp3.Response;


/**
 * name:小龙虾
 * time:2019.5.4
 * Type:朋友页Fragment
 */

public class FriendFragment extends Fragment {
    private FragmentFriendBinding binding;
    private ArrayList<HotVideo.ResultBean> list=new ArrayList<>();
    private VideoAdapter videoAdapter;
    private String videoApi="https://api.apiopen.top/getJoke?page=1&count=50&type=video";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_friend,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView(){
        binding.videoRec.setLayoutManager(new LinearLayoutManager(getActivity()));
        videoAdapter=new VideoAdapter(getActivity(),list);

        binding.videoRec.setAdapter(videoAdapter);
    }

    private void sendRequestOkHttp(){//访问接口
        HttpUtil.sendOkHttpRequest(videoApi, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String videoDatas=response.body().string();
                parseJSONWithGSON(videoDatas);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }


    private HotVideo parseJSONWithGSON(String jsonData){

        list.clear();
        try {
            JSONObject jsonObject=new JSONObject(jsonData);
            JSONArray jsonArray=jsonObject.getJSONArray("result");
//            Log.d("ListFragment","Data共有多少条数据"+jsonArray.length());
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                String header = jsonObject1.getString("header");//头像
                String name=jsonObject1.getString("name");//作者
                String text=jsonObject1.getString("text");//标题
                String video = jsonObject1.getString("video");//播放地址
                String thumbnail=jsonObject1.getString("thumbnail");//预览图
                String passtime=jsonObject1.getString("passtime");//发布时间

                HotVideo.ResultBean hd=new HotVideo.ResultBean();
                hd.setHeader(header);
                hd.setName(name);
                hd.setText(text);
                hd.setVideo(video);
                hd.setThumbnail(thumbnail);
                hd.setPasstime(passtime);
                list.add(hd);
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                   videoAdapter.notifyDataSetChanged();
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
//            Toast.makeText(getActivity(),"获取服务器数据失败",Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        sendRequestOkHttp();
    }
}
