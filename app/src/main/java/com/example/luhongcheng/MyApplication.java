package com.example.luhongcheng;

import android.app.Application;

import com.miui.zeus.mimo.sdk.MimoSdk;

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

        //xiaomiSDK
        MimoSdk.setEnableUpdate(true);
        //MimoSdk.setDebugOn();
        // 正式上线时候务必关闭stage
        //MimoSdk.setStageOn();
        // 如需预置插件请在assets目录下添加mimo_assets.apk
        MimoSdk.init(this, APP_ID, APP_KEY, APP_TOKEN);
    }
}
