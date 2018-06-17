package com.example.luhongcheng;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by alex233 on 2018/6/15.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
