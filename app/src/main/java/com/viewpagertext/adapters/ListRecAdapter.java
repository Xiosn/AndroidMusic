package com.viewpagertext.adapters;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viewpagertext.R;
import com.viewpagertext.json.ListFragmentSongMusics;

import java.util.ArrayList;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * name:小龙虾
 * time:2019.5.4
 * Type:发现页9宫格RecyclerView适配器
 */

public class ListRecAdapter extends RecyclerView.Adapter<ListRecAdapter.NormalTextViewHolder> implements View.OnClickListener{
    private Context mContext;
    private ArrayList<ListFragmentSongMusics.DataBean> mData;
    public ListRecAdapter(Context context, ArrayList<ListFragmentSongMusics.DataBean> mData) {
        this.mContext=context;
        this.mData=mData;
    }

    @Override
    public NormalTextViewHolder onCreateViewHolder(ViewGroup viewGroup,  int viewType) {
        NormalTextViewHolder holder=new NormalTextViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_layout,viewGroup,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(NormalTextViewHolder viewHolder, final int position) {
        ListFragmentSongMusics.DataBean data=mData.get(position);
        viewHolder.Listt_song.setText(data.getName());
        viewHolder.Listt_singer.setText(data.getSinger());//设置歌手

        viewHolder.itemView.setTag(position);
        viewHolder.ListFragment_menu.setTag(position);
        viewHolder.Listt_postion.setText(data.getNumber());//设置编号
        viewHolder.ListintoPlayPage.setTag(position);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return mData.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class NormalTextViewHolder extends RecyclerView.ViewHolder {
        TextView Listt_song,Listt_postion,Listt_singer;
        ImageView ListFragment_menu;
        LinearLayout ListintoPlayPage;

        public NormalTextViewHolder(View view){
            super(view);

            ListFragment_menu=view.findViewById(R.id.ListFragment_menu);//菜单弹窗
            Listt_song = view.findViewById(R.id.Listt_song);//歌名
            Listt_postion=view.findViewById(R.id.Listt_postion);
            Listt_singer=view.findViewById(R.id.Listt_singer);
            ListintoPlayPage=view.findViewById(R.id.ListintoPlayPage);

            itemView.setOnClickListener(ListRecAdapter.this);
            ListFragment_menu.setOnClickListener(ListRecAdapter.this);
            ListintoPlayPage.setOnClickListener(ListRecAdapter.this);
        }
    }


    //=======================以下为item中的button控件点击事件处理===================================

    //item里面有多个控件可以点击（item+item内部控件）
    public enum ViewName {
        ITEM,
        PRACTISE
    }


    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener  {
        void onItemClick(View v, ViewName viewName, int position);
        void onItemLongClick(View v);
    }


    private OnItemClickListener mOnItemClickListener;//声明自定义的接口


    //定义方法并传给外面的使用者
    public void setOnItemClickListener(OnItemClickListener  listener) {
        this.mOnItemClickListener  = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                case R.id.xrv_list:
                    mOnItemClickListener.onItemClick(v, ViewName.PRACTISE, position);
                    break;
                default:
                    mOnItemClickListener.onItemClick(v, ViewName.ITEM, position);
                    break;
            }
        }
    }
}