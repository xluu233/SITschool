package com.example.luhongcheng;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;


import org.litepal.LitePalApplication;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by alex233 on 2018/6/15.
 */

public class MyApplication extends LitePalApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }


    public static Handler getAppHandler() {
        Looper.prepare();
        return new Handler();
    }


}
