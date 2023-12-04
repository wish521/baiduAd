package com.baidu.baiduapplication;

import android.app.Application;

import org.xutils.BuildConfig;
import org.xutils.x;

public class MyApplication extends Application {
    static public MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}
