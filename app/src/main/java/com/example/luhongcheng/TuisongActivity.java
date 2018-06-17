package com.example.luhongcheng;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by alex233 on 2018/6/15.
 */

public class TuisongActivity extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tuisong);
        WebView xiazai = (WebView) findViewById(R.id.kuan);
        xiazai.loadUrl("https://www.coolapk.com/apk/187672");
    }
}
