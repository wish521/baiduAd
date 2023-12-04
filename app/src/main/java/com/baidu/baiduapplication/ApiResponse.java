package com.baidu.baiduapplication;

import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.List;

public class ApiResponse {
    public long timestamp;
    public String errno;
    public String errmsg;
    public String hotWord;
    public String area;
    public String temperature;
    public String temp_desc;
    public List<DataItem> data;


    // Getters and Setters

    public class DataItem {
        ExoPlayer player;
        PlayerView playerView;
        MediaItem mediaItem;

        public void init(PlayerView playerView, Context context) {
            if (player != null) {
                return;
            }
            player = StartActivity.player;
            playerView.setPlayer(player);
//            player = new SimpleExoPlayer.Builder(context).build();
//            playerView.setPlayer(player);
////            mediaItem = MediaItem.fromUri(Uri.parse("http://mirror.aarnet.edu.au/pub/TED-talks/911Mothers_2010W-480p.mp4"));
//            mediaItem = MediaItem.fromUri(Uri.parse(imageurls.get(0)));
//            player.setMediaItem(mediaItem);
//            player.setVolume(0);
//            player.prepare();
        }

        public String layout;
        public String url;
        public String title;
        public String type;
        public String site;
        public String tag;
        public String comment_num;
        public long publish_time;
        public String duration;
        public int img_cnt;
        public List<String> imageurls;
        public String media_type;
        // Getters and Setters
    }
}