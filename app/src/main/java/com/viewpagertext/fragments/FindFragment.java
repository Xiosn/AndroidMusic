package com.viewpagertext.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.viewpagertext.R;
import com.viewpagertext.adapters.BannerViewHolder;
import com.viewpagertext.adapters.MusicGridAdapter;
import com.viewpagertext.constructor.SongsDatas;
import com.viewpagertext.json.HotSongs;
import com.viewpagertext.utils.HttpUtil;
import com.viewpagertext.views.GridSpaceitemDecoration;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Response;

/**
 * name:小龙虾
 * time:2019.5.4
 * Type:发现页Fragment
 */

public class FindFragment extends Fragment {

    private MZBannerView mzBanner;
    private RecyclerView rv_Grid;
    private MusicGridAdapter musicGridAdapter;
    private ArrayList<SongsDatas> mDatas=new ArrayList<>();
    private View view;
    private String findsongs="http://v1.itooi.cn/netease/songList/hot?&page=0&pageSize=6";
    private int[] RES={R.mipmap.banner1,R.mipmap.banner2,R.mipmap.banner3,R.mipmap.banner4,R.mipmap.banner5,R.mipmap.banner6};
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_find,container,false);
        initBanner(view);//Banner

        initRvGird(view);//RecyclerViewGrid
        sendRequestWithOkHttp();
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
        musicGridAdapter=new MusicGridAdapter(getActivity(),mDatas);
        rv_Grid.setAdapter(musicGridAdapter);
    }


    public void sendRequestWithOkHttp(){

        HttpUtil.sendOkHttpRequest(findsongs, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Toast.makeText(getActivity(),"IO发生异常",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData=response.body().string();//得到服务器返回的具体内容
//                Log.d("MainActivity","测试结果"+responseData);
                parseJSONWithGSON(responseData);
            }
        });
    }


    public HotSongs parseJSONWithGSON(String jsonData) {
        mDatas.clear();
                try {
                    JSONObject jsonObject1 = new JSONObject(jsonData);
                    JSONArray jsonArray=jsonObject1.getJSONArray("data");
                    Log.d("MainActivity","Data共有多少条数据"+jsonArray.length());
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");  //获取到json数据中的activity数组里的内容name
                        String image=jsonObject.getString("coverImgUrl");//获取歌单图片
                        int playCount=jsonObject.getInt("playCount");//歌单播放数量
                        long songsId=jsonObject.getLong("id");//歌单id

                        SongsDatas ss = new SongsDatas();
                        ss.setTitle(name);
                        ss.setImageurl(image);
                        ss.setPlayCount(playCount/10000+"");
                        ss.setId(songsId);
                        mDatas.add(ss);

                    }

                   getActivity().runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           musicGridAdapter.notifyDataSetChanged();
                       }
                   });

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),"获取服务器数据失败",Toast.LENGTH_SHORT).show();
                }
       return null;
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