package com.example.luhongcheng;

import android.app.Application;


import cn.jpush.android.api.JPushInterface;

/**
 * Created by alex233 on 2018/6/15.
 */

public class MyApplication extends Application {

    private static final String APP_ID = "2882303761517774950";
    private static final String APP_KEY = "fake_app_key";
    private static final String APP_TOKEN = "fake_app_token";

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

    }
}
