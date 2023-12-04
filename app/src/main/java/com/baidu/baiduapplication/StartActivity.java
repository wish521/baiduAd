package com.baidu.baiduapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.baidu.baiduapplication.databinding.ActivityStartBinding;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class StartActivity extends AppCompatActivity {
    ActivityStartBinding binding;
    CustomAdapter mAdapter;
    Item[] items;


    public static ExoPlayer player;
    public static PlayerView playerView;
    public static MediaItem mediaItem;

    public static void initPlayer(Context context, String url) {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
        player = new SimpleExoPlayer.Builder(context).build();
//        playerView.setPlayer(player);
//            mediaItem = MediaItem.fromUri(Uri.parse("http://mirror.aarnet.edu.au/pub/TED-talks/911Mothers_2010W-480p.mp4"));
        mediaItem = MediaItem.fromUri(Uri.parse(url));
        player.setMediaItem(mediaItem);
        player.setVolume(0);
        player.prepare();

    }

    HashMap<String, List> mapArea;
    HashMap<String, List> mapBrand;


    public static String result ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapArea = new HashMap<>();
        mapBrand = new HashMap<>();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start);
        RequestParams requestParams = new RequestParams("http://du.wanqiche.cn/items_list_api/");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ResponseData responseData = gson.fromJson(result, ResponseData.class);
                items = responseData.getData();


                for (Item item : items) {
                    String area = item.getArea();
                    if (mapArea.get(area) == null) {
                        mapArea.put(area, new ArrayList());
                    }
                    mapArea.get(area).add(item);
                }
                for (Item item : items) {
                    String site = item.getSite();
                    if (mapBrand.get(item.getSite()) == null) {
                        mapBrand.put(site, new ArrayList());
                    }
                    mapBrand.get(site).add(item);
                }


                mAdapter = new CustomAdapter(StartActivity.this, Arrays.asList(items));
                binding.listview.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

        });
        binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                intent.putExtra("id", mAdapter.getDataList().get(position).getItemId());
                result = null;
                if ( mAdapter.getDataList().get(position).getPreload_url().endsWith(".mp4")) {
                    StartActivity.initPlayer(StartActivity.this, mAdapter.getDataList().get(position).getPreload_url());
                }

//                StartActivity.initPlayer(StartActivity.this,"http://mirror.aarnet.edu.au/pub/TED-talks/911Mothers_2010W-480p.mp4");

                WaitingDialog waitingDialog = new WaitingDialog(StartActivity.this, "waiting....");
                waitingDialog.show();
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RequestParams requestParams = new RequestParams("http://du.wanqiche.cn/baidu_data_api/?id=" + mAdapter.getDataList().get(position).getItemId());
                        x.http().get(requestParams, new Callback.CommonCallback<String>(){

                            @Override
                            public void onSuccess(String res) {
                                result =null;
                                result = res;
                                StartActivity.this.startActivity(intent);
                                waitingDialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {

                            }

                            @Override
                            public void onCancelled(CancelledException cex) {

                            }

                            @Override
                            public void onFinished() {

                            }
                        });
                    }
                }, 200);



            }



        });


        binding.btnArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAreaDialog();
            }
        });

        binding.brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBrandDialog();
            }
        });


    }

    void showAreaDialog() {
        ArrayList<String> dataList = new ArrayList<>(mapArea.keySet());
        String[] data = dataList.toArray(new String[dataList.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
        builder.setTitle("请选择一个选项");

        builder.setSingleChoiceItems(dataList.toArray(new String[dataList.size()]), -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String key = data[which];
                mAdapter.getDataList().clear();
                mAdapter.getDataList().addAll(mapArea.get(key));
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("全部", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mAdapter.getDataList().clear();
                mAdapter.getDataList().addAll(Arrays.asList(items));
                mAdapter.notifyDataSetChanged();
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void showBrandDialog() {
        ArrayList<String> dataList = new ArrayList<>(mapBrand.keySet());
        String[] data = dataList.toArray(new String[dataList.size()]);
        int selectedIndex;
        AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
        builder.setTitle("请选择一个选项");

        builder.setSingleChoiceItems(dataList.toArray(new String[dataList.size()]), -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String key = data[which];
                mAdapter.getDataList().clear();
                mAdapter.getDataList().addAll(mapBrand.get(key));
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("全部", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mAdapter.getDataList().clear();
                mAdapter.getDataList().addAll(Arrays.asList(items));
                mAdapter.notifyDataSetChanged();
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}