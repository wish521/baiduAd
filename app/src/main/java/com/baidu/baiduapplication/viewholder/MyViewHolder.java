package com.baidu.baiduapplication.viewholder;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.baiduapplication.R;
import com.google.android.exoplayer2.ui.PlayerView;

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView titleTextView;
    public ImageView imageView;
    public TextView authorTextView;
    public TextView tv_comment;
    public TextView tv_publish_time;
    public TextView adTextView;
    public ImageView closeButton;
    public RelativeLayout rl_player_container;

    public PlayerView playerView;
    public OnItemClickListener onItemClickListener;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.tv_title);
        imageView = itemView.findViewById(R.id.my_image_view);
        authorTextView = itemView.findViewById(R.id.tv_author);
        adTextView = itemView.findViewById(R.id.tv_ad);
        closeButton = itemView.findViewById(R.id.iv_close);
        playerView = itemView.findViewById(R.id.playerView);
        tv_comment = itemView.findViewById(R.id.tv_comment);
        tv_publish_time = itemView.findViewById(R.id.tv_publish_time);
        rl_player_container=itemView.findViewById(R.id.rl_player_container);;
        if (playerView != null) {
            playerView.setOnClickListener(this);
            playerView.setUseController(false);
        }

    }

    @Override
    public void onClick(View view) {
        int pos = this.getLayoutPosition();
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(pos);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
