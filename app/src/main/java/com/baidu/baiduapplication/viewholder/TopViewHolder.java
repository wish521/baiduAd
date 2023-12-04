package com.baidu.baiduapplication.viewholder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.baiduapplication.R;

public class TopViewHolder extends RecyclerView.ViewHolder {
    public TextView titleTextView;
    public TextView topMarkTextView;
    public TextView authorTextView;
    public LinearLayout secondTitleLayout;

    public TopViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.tv_title);
        topMarkTextView = itemView.findViewById(R.id.tv_top_mark);
        authorTextView = itemView.findViewById(R.id.tv_author);
        secondTitleLayout = itemView.findViewById(R.id.ll_second_title);
    }
}