package com.viewpagertext.adapters;

import android.content.Context;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.viewpagertext.R;

import java.util.List;

/**
 * name:小龙虾
 * time:2019.5.4
 * Type:详情页RecyclerView适配器
 */

public class ListRecAdapter extends RecyclerView.Adapter<ListRecAdapter.NormalTextViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private List<String> mDatas;
    private OnRecyclerViewItemClickListener myClickItemListener;// 声明自定义的接口


    public ListRecAdapter(Context context,List<String> mDatas) {
        this.mDatas=mDatas;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mLayoutInflater.inflate(R.layout.item_layout, parent, false);
        NormalTextViewHolder holder=new NormalTextViewHolder(view,myClickItemListener);
        return holder;
    }

    /**
     * 定义public方法用以将接口暴露给外部
     * @param listener
     */
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.myClickItemListener = listener;
    }


    @Override
    public void onBindViewHolder(NormalTextViewHolder holder, int position) {
        holder.mTextView.setText(mDatas.get(position));
        holder.itemView.setTag(position);//子项点击
        holder.playorpause.setOnClickListener(new View.OnClickListener() {//详情页播放按钮
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 自定义接口
     */
    public interface OnRecyclerViewItemClickListener {
        public void onItemClick(View view, int postion);
    }

    class NormalTextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTextView;
        ImageView playorpause;
        private OnRecyclerViewItemClickListener mListener;// 声明自定义的接口

        NormalTextViewHolder(View view, OnRecyclerViewItemClickListener mListener) {
            super(view);
            itemView.setOnClickListener(this);
            this.mListener = mListener;
            mTextView =  view.findViewById(R.id.item_name);//子项
            playorpause=view.findViewById(R.id.playorpause);//详情页播放按钮
        }
        @Override
        public void onClick(View v) {
            //getAdapterPosition()为Viewholder自带的一个方法，用来获取RecyclerView当前的位置，将此作为参数，传出去
            mListener.onItemClick(v, getAdapterPosition());
        }
    }
}


