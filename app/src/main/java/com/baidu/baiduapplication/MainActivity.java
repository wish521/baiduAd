package com.baidu.baiduapplication;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.baiduapplication.util.ScreenUtils;
import com.baidu.baiduapplication.viewholder.MyViewHolder;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;

import org.w3c.dom.Text;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.time.Instant;

public class MainActivity extends AppCompatActivity {

    MyAdapter adapter;
    ApiResponse jsonObject;

    RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int id = getIntent().getExtras().getInt("id");
        setContentView(R.layout.activity_main);
        ImmersionBar.with(this).navigationBarColor(R.color.transparent).navigationBarAlpha(0)
                .statusBarDarkFont(true)
                .navigationBarColor(R.color.transparent).init();
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);


        RequestParams requestParams = new RequestParams("http://du.wanqiche.cn/baidu_data_api/?id=" + id);

        if(TextUtils.isEmpty(StartActivity.result)){
            x.http().get(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    processResult(result);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    ex.printStackTrace();
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    cex.printStackTrace();
                }

                @Override
                public void onFinished() {

                }
            });
        }else{
            processResult(StartActivity.result);
        }

    }

    void processResult(String result) {

        Log.i("result:", result);
        Gson gson = new Gson();
        jsonObject = gson.fromJson(result, ApiResponse.class);

        TextView textView = findViewById(R.id.tv_area_top);
        textView.setText(jsonObject.area);
        TextView tvArea = findViewById(R.id.tv_area);
        tvArea.setText(jsonObject.area);
        TextView tv_key_word = findViewById(R.id.tv_key_word);
        tv_key_word.setText(jsonObject.hotWord);
        TextView temperature = findViewById(R.id.temperature);
        temperature.setText(jsonObject.temperature);
        TextView tv_area_top = findViewById(R.id.tv_area_top);
        tv_area_top.setText(jsonObject.area);
        TextView temp_weather = findViewById(R.id.temp_weather);
        temp_weather.setText(jsonObject.temp_desc);


        adapter = new MyAdapter(jsonObject.data, MainActivity.this);
        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener(new MyViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (jsonObject.data.get(position).layout.equals("autovideo")) {
                    Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                    intent.putExtra("url", "http://yun.it7090.com/video/XHLaunchAd/video01.mp4");
                    startActivity(intent);
                    stopPlaying();
                }
                if (jsonObject.data.get(position).url != null) {
                    Intent intent = new Intent(MainActivity.this, MyWebViewActivity.class);
                    intent.putExtra("url", jsonObject.data.get(position).url);
                    startActivity(intent);
                }
            }
        });

        relativeLayout = findViewById(R.id.rl_tab);
        int startMargin = getResources().getDimensionPixelSize(R.dimen.show_margin); // 初始的marginTop值，单位为像素
        int endMargin = getResources().getDimensionPixelSize(R.dimen.hide_margin); // 最终的marginTop值，单位为像素

        ValueAnimator hideAnimator = ValueAnimator.ofInt(startMargin, endMargin);
        hideAnimator.setDuration(500); // 设置动画的持续时间，单位为毫秒

        hideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) relativeLayout.getLayoutParams();
                layoutParams.topMargin = animatedValue;
                relativeLayout.setLayoutParams(layoutParams);
            }
        });

        ValueAnimator showAnimator = ValueAnimator.ofInt(endMargin, startMargin);
        showAnimator.setDuration(500); // 设置动画的持续时间，单位为毫秒
        showAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) relativeLayout.getLayoutParams();
                layoutParams.topMargin = animatedValue;
                relativeLayout.setLayoutParams(layoutParams);
            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int scrollDirection = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.i("scroll----:", dy + "");
                if (dy > 0) {
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) relativeLayout.getLayoutParams();
                    if (layoutParams.topMargin == ScreenUtils.dp2px(recyclerView.getContext(), 0)) {
                        return;
                    }
                    layoutParams.topMargin -= dy;
                    if (layoutParams.topMargin <= ScreenUtils.dp2px(recyclerView.getContext(), 0)) {
                        layoutParams.topMargin = ScreenUtils.dp2px(recyclerView.getContext(), 0);
                    }
                    relativeLayout.setLayoutParams(layoutParams);
                    Log.i("scroll----:", "up:" + layoutParams.topMargin);
                } else if (dy < 0) {
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) relativeLayout.getLayoutParams();
                    if (layoutParams.topMargin > ScreenUtils.dp2px(recyclerView.getContext(), 40)) {
                        return;
                    }
                    layoutParams.topMargin -= dy;
                    if (layoutParams.topMargin >= ScreenUtils.dp2px(recyclerView.getContext(), 40)) {
                        layoutParams.topMargin = ScreenUtils.dp2px(recyclerView.getContext(), 40);
                    }
                    Log.i("scroll----:", "down" + layoutParams.topMargin);
                    relativeLayout.setLayoutParams(layoutParams);
                } else {
                    // 停止滑动
                    scrollDirection = 0;
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.stopPlaying();
    }

    public void stopPlaying() {
        for (ApiResponse.DataItem dataItem : jsonObject.data) {
            if (dataItem.player != null) {
                if (dataItem.player.isPlaying()) {
                    dataItem.player.setPlayWhenReady(false);
                }
            }
        }
    }
}