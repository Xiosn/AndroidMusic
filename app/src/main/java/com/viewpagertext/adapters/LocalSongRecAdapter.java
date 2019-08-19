package com.viewpagertext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.viewpagertext.R;
import com.viewpagertext.constructor.Song;
import java.util.List;

public class LocalSongRecAdapter extends RecyclerView.Adapter<LocalSongRecAdapter.MyViewHolder>implements View.OnClickListener{

    private Context context;
    private List<Song> list;

    public LocalSongRecAdapter(Context context,List<Song> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViewHolder myViewHodler=new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.load_item,parent,false));
        return myViewHodler;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.t_position.setText(position + 1 + "");
        holder.t_song.setText(list.get(position).song);
        holder.t_singer.setText(list.get(position).singer);

        holder.itemView.setTag(position);
        holder.t_position.setTag(position);
        holder.t_song.setTag(position);
        holder.t_singer.setTag(position);
        holder.localmenu.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView t_position,t_song,t_singer;
        private ImageView localmenu;
        public MyViewHolder(View itemView) {
            super(itemView);
            t_position=itemView.findViewById(R.id.t_postion);
            t_song=itemView.findViewById(R.id.t_song);
            t_singer=itemView.findViewById(R.id.t_singer);
            localmenu=itemView.findViewById(R.id.localmenu);

            itemView.setOnClickListener(LocalSongRecAdapter.this);
            localmenu.setOnClickListener(LocalSongRecAdapter.this);
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
                case R.id.mylist:
                    mOnItemClickListener.onItemClick(v, ViewName.PRACTISE, position);
                    break;
                default:
                    mOnItemClickListener.onItemClick(v, ViewName.ITEM, position);
                    break;
            }
        }
    }
}
