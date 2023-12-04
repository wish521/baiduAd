package com.baidu.baiduapplication.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.baiduapplication.R;

public class ThreePicViewHolder extends RecyclerView.ViewHolder {
    public TextView titleTextView;
    public ImageView imageView;
    public ImageView imageView1;
    public ImageView imageView2;
    public TextView authorTextView;
    public TextView commentTextView;
    public TextView publishTimeTextView;
    public ImageView closeButton;

    public ThreePicViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.tv_title);
        imageView = itemView.findViewById(R.id.iv_pic);
        imageView1 = itemView.findViewById(R.id.iv_pic1);
        imageView2 = itemView.findViewById(R.id.iv_pic2);
        authorTextView = itemView.findViewById(R.id.tv_author);
        commentTextView = itemView.findViewById(R.id.tv_comment);
        publishTimeTextView = itemView.findViewById(R.id.tv_publish_time);
        closeButton = itemView.findViewById(R.id.iv_close);
    }
}