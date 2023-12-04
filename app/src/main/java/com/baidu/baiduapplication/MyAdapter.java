package com.baidu.baiduapplication;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.baiduapplication.util.ScreenUtils;
import com.baidu.baiduapplication.viewholder.MyViewHolder;
import com.baidu.baiduapplication.viewholder.RoundedCornerDrawable;
import com.baidu.baiduapplication.viewholder.ThreePicViewHolder;
import com.baidu.baiduapplication.viewholder.TopViewHolder;
import com.baidu.baiduapplication.viewholder.VideoViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.xutils.image.ImageOptions;
import org.xutils.x;

public class MyAdapter extends RecyclerView.Adapter {
    private List<ApiResponse.DataItem> data;
    private Context context;
    private int currentPlayingPosition = -1;


    private MyViewHolder.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(MyViewHolder.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public MyAdapter(List<ApiResponse.DataItem> data, Context context) {
        this.data = data;
        this.context = context;
    }

    static final int TYPE_VIDEO = 2;
    static final int TYPE_ADS = 1;

    @Override
    public int getItemViewType(int position) {
        ApiResponse.DataItem item = data.get(position);
        if (item.layout.equals("titleonly_top")) {
            if (position == 0) {
                return 0;
            }
            if (position == 4) {
                return 7;
            }
            return 8;
        } else if (item.layout.equals("ads")) {

            return TYPE_ADS;
        } else if (item.layout.equals("autovideo")) {
            return TYPE_VIDEO;
        } else if (item.layout.equals("image1") || item.layout.equals("ad_image1")) {
            return 3;
        } else if (item.layout.equals("image3")) {
            return 4;
        } else if (item.layout.equals("bigimage")) {
            return 5;
        } else if (item.layout.equals("ad_bigimage")) {
            return 6;
        }
        Log.i("tag:", item.layout);
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(context).inflate(R.layout.item_on_top, parent, false);
                return new TopViewHolder(view);
            case 7:
                view = LayoutInflater.from(context).inflate(R.layout.item_on_top_5, parent, false);
                return new TopViewHolder(view);
            case 8:
                view = LayoutInflater.from(context).inflate(R.layout.item_on_top_middle, parent, false);
                return new TopViewHolder(view);
            case 4:
                view = LayoutInflater.from(context).inflate(R.layout.item_3_pic, parent, false);
                return new ThreePicViewHolder(view);
            case TYPE_VIDEO:
                view = LayoutInflater.from(context).inflate(R.layout.item_vedio, parent, false);
                return new VideoViewHolder(view);
            case TYPE_ADS:
                view = LayoutInflater.from(context).inflate(R.layout.item_ads, parent, false);
                MyViewHolder myViewHolder = new MyViewHolder(view);
                myViewHolder.onItemClickListener = onItemClickListener;
                return myViewHolder;
            case 3:
            case 5:
            case 6:
                view = LayoutInflater.from(context).inflate(R.layout.item_one_pic, parent, false);
                return new MyViewHolder(view);
            default:
                view = LayoutInflater.from(context).inflate(R.layout.item_on_top, parent, false);
                return new MyViewHolder(view);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ApiResponse.DataItem item = data.get(position);
        int viewType = getItemViewType(position);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    int pos = ((int) view.getTag());
                    if (pos != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(pos);
                    }
                }
            }
        });
        switch (viewType) {
//            case 0:
            case 7:
            case 8:
                TopViewHolder topViewHolder = (TopViewHolder) holder;
                topViewHolder.titleTextView.setText(item.title);
                topViewHolder.authorTextView.setText(item.site);
                if (position > 1) {
                    topViewHolder.topMarkTextView.setVisibility(View.GONE);
                }
                break;
            case TYPE_VIDEO:
                VideoViewHolder videoViewHolder = (VideoViewHolder) holder;
                videoViewHolder.titleTextView.setText(item.title);
                videoViewHolder.imageView.getLayoutParams().width = ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2px(context, 16 * 2);
                videoViewHolder.imageView.getLayoutParams().height = (int) (videoViewHolder.imageView.getLayoutParams().width * 0.5628);

                x.image().bind(videoViewHolder.imageView, item.imageurls.get(0), ImageOptions.DEFAULT);
                videoViewHolder.authorTextView.setText(item.site);
                videoViewHolder.commentTextView.setText(item.comment_num);
                videoViewHolder.timeTextView.setText(item.duration);
//                item.init(videoViewHolder.playerView,videoViewHolder.playerView.getContext());
//                item.player.play();
//                videoViewHolder.imageView.setVisibility(View.GONE);
                break;
            case 4:
                ThreePicViewHolder threePicViewHolder = (ThreePicViewHolder) holder;
                threePicViewHolder.titleTextView.setText(item.title);
                x.image().bind(threePicViewHolder.imageView, item.imageurls.get(0), ImageOptions.DEFAULT);
                x.image().bind(threePicViewHolder.imageView1, item.imageurls.get(1), ImageOptions.DEFAULT);
                x.image().bind(threePicViewHolder.imageView2, item.imageurls.get(2), ImageOptions.DEFAULT);
                threePicViewHolder.authorTextView.setText(item.site);
                threePicViewHolder.commentTextView.setText(item.comment_num);
//                threePicViewHolder.publishTimeTextView.setText(item.publish_time);
                break;
            case TYPE_ADS:
                MyViewHolder myViewHolder = (MyViewHolder) holder;
                myViewHolder.titleTextView.setText(item.title);
                myViewHolder.authorTextView.setText(item.site);
                myViewHolder.imageView.getLayoutParams().width = ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2px(context, 16 * 2);
                myViewHolder.imageView.getLayoutParams().height = (int) (myViewHolder.imageView.getLayoutParams().width * 0.5628);

                myViewHolder.playerView.getLayoutParams().width = ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2px(context, 16 * 2);
                myViewHolder.playerView.getLayoutParams().height = (int) (myViewHolder.imageView.getLayoutParams().width * 0.5628);

                myViewHolder.playerView.setOutlineProvider(new ViewOutlineProvider() {
                    @Override
                    public void getOutline(View view, Outline outline) {
                        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(),
                                ScreenUtils.dp2px(myViewHolder.playerView.getContext(), 8));
                    }
                });

                myViewHolder.playerView.setClipToOutline(true);

                x.image().bind(myViewHolder.imageView, item.imageurls.get(0), ImageOptions.DEFAULT);

                item.init(myViewHolder.playerView, myViewHolder.playerView.getContext());
//                item.player.play();
                myViewHolder.imageView.setVisibility(View.GONE);
                myViewHolder.playerView.setPlayer(item.player);

                if (item.media_type.equals("image")) {
                    myViewHolder.imageView.setVisibility(View.VISIBLE);
                    myViewHolder.playerView.setVisibility(View.GONE);
                } else {
                    myViewHolder.imageView.setVisibility(View.GONE);
                    myViewHolder.playerView.setVisibility(View.VISIBLE);
                }
                break;
            case 3:
            case 5:
            case 6:
                myViewHolder = (MyViewHolder) holder;
                myViewHolder.titleTextView.setText(item.title);
                myViewHolder.authorTextView.setText(item.site);
                myViewHolder.tv_comment.setText(item.comment_num);
                if (item.publish_time > 0) {
                    // 将时间戳转换为毫秒数
                    long milliseconds = (long) item.publish_time * 1000;
                    // 创建日期对象
                    Date date = new Date(milliseconds);
                    // 创建日期格式化器
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    // 格式化日期
                    String formattedDate = sdf.format(date);
                    myViewHolder.tv_publish_time.setText(formattedDate);
                }
                myViewHolder.imageView.getLayoutParams().width = ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2px(context, 16 * 2);
                myViewHolder.imageView.getLayoutParams().height = (int) (myViewHolder.imageView.getLayoutParams().width * 0.5628);
                x.image().bind(myViewHolder.imageView, item.imageurls.get(0), ImageOptions.DEFAULT);
                break;

        }

    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        // 当列表项滑动到屏幕中时，开始播放
        int position = holder.getAdapterPosition();
//        if (position == currentPlayingPosition && holder instanceof MyViewHolder) {
//
//        }
        if (holder instanceof MyViewHolder) {
            if (((MyViewHolder) holder).playerView != null) {
                if( ((MyViewHolder) holder).playerView.getPlayer()!=null){
                    ((MyViewHolder) holder).playerView.getPlayer().setPlayWhenReady(true);
                    ((MyViewHolder) holder).playerView.hideController();
                }
            }
        }

    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
//        // 当列表项离开屏幕时，停止播放
        int position = holder.getAdapterPosition();
//        if (position == currentPlayingPosition && holder instanceof MyViewHolder) {
//            ((MyViewHolder) holder).playerView.getPlayer().setPlayWhenReady(false);
//        }
        if (holder instanceof MyViewHolder) {
            if (((MyViewHolder) holder).playerView != null) {
                if (((MyViewHolder) holder).playerView.getPlayer() != null) {
                    ((MyViewHolder) holder).playerView.getPlayer().setPlayWhenReady(false);
                }
            }
        }
    }

    public void setCurrentPlayingPosition(int position) {
        currentPlayingPosition = position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}