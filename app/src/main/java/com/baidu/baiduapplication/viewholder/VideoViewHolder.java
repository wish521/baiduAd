package com.baidu.baiduapplication.viewholder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.baiduapplication.R;
import com.google.android.exoplayer2.ui.PlayerView;

public class VideoViewHolder extends RecyclerView.ViewHolder {
    public TextView titleTextView;
    public ImageView imageView;
    public TextView timeTextView;
    public  TextView authorTextView;
    public TextView commentTextView;
    public ImageView closeButton;
    public PlayerView playerView;

    public VideoViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.tv_title);
        imageView = itemView.findViewById(R.id.my_image_view);
        timeTextView = itemView.findViewById(R.id.tv_time);
        authorTextView = itemView.findViewById(R.id.tv_author);
        commentTextView = itemView.findViewById(R.id.tv_comment);
        closeButton = itemView.findViewById(R.id.iv_close);
        playerView = itemView.findViewById(R.id.playerView);
    }
}
