package com.viewpagertext.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.viewpagertext.R;

/**
 * name:小龙虾
 * time:2019.5.4
 * Type:发现页9宫格RecyclerView适配器
 */

public class MusicGridAdapter extends RecyclerView.Adapter<MusicGridAdapter.ViewHolder> implements View.OnClickListener{
   Context mContext;
    public MusicGridAdapter(Context context) {
        this.mContext=context;
    }
    private OnItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,  int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.find_grid_music, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        //将position保存在itemView的Tag中，以便点击时进行获取
        viewHolder.itemView.setTag(position);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    //获取数据的数量
    @Override
    public int getItemCount() {
        return 6;
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view){
            super(view);
        }
    }
}