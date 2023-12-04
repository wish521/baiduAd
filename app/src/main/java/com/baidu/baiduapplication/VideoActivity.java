package com.baidu.baiduapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.gyf.immersionbar.ImmersionBar;

import org.xutils.image.ImageOptions;
import org.xutils.x;

public class VideoActivity extends AppCompatActivity {
    ExoPlayer player;
    PlayerView playerView;
    MediaItem mediaItem ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        ImmersionBar.with(this).navigationBarColor(R.color.transparent).navigationBarAlpha(0)
                .statusBarDarkFont(false)
                .navigationBarColor(R.color.transparent).init();

        String url = getIntent().getStringExtra("url");

        String videoFileName = "tv.mp4";
        Uri videoUri = Uri.parse("asset:///" + videoFileName);
        MediaItem mediaItem = MediaItem.fromUri(videoUri);

        playerView = findViewById(R.id.playerView);
        if(player!=null){
            return;
        }
        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
//        mediaItem = MediaItem.fromUri(Uri.parse(url));
        player.setMediaItem(mediaItem);
//            player.setVolume(0);
        player.prepare();
        player.setPlayWhenReady(true);
        ImageOptions.Builder builder = new ImageOptions.Builder();
        builder.setCircular(true);// 设置圆形属性
        ImageOptions options = builder.build();

        x.image().bind(findViewById(R.id.avatar),"https://upload.jianshu.io/users/upload_avatars/1883001/eca57c4e-6f3b-4979-8872-65e72f01d215.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96/format/webp",options);

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player.isPlaying()) {
                    player.stop();
                    player.release();
                }
                finish();
            }
        });

        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_READY && player.getPlayWhenReady()) {
                    // ExoPlayer 已准备好并开始播放
                    // 在这里处理播放开始事件
                    Format videoFormat = player.getVideoFormat();
                    int width = videoFormat.width;
                    int height = videoFormat.height;
                    if(height>width){
                        findViewById(R.id.iv_full_screen).setVisibility(View.GONE);
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
                        layoutParams.setMargins(0,0,0,0);
                        layoutParams.height = ActionBar.LayoutParams.MATCH_PARENT;
                        playerView.setLayoutParams(layoutParams);
                    }
                }
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        player.stop();
    }
}