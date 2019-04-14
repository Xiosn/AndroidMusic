package com.viewpagertext.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.viewpagertext.R;

public class MusicGridAdapter extends RecyclerView.Adapter<MusicGridAdapter.ViewHolder> {

    private Context context;
    public MusicGridAdapter(Context context){
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.find_grid_music,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//        Glide.with(context)
//                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554893789477&di=d9418cad53d6e5e2fb0bf5c3f18cdba8&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D2769395767%2C3624585577%26fm%3D214%26gp%3D0.jpg")
//                .into(viewHolder.roundedImageView);
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private RoundedImageView roundedImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            roundedImageView=itemView.findViewById(R.id.iv_Grid_Icon);
        }
    }
}
