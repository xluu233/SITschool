package com.example.luhongcheng.zixun;


import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.luhongcheng.R;

import okhttp3.OkHttpClient;

import static android.view.KeyEvent.KEYCODE_BACK;

public class zhuyeDisplayActvivity extends AppCompatActivity {

    private String newsUrl;
    private OkHttpClient okHttpClient;
    private OkHttpClient.Builder builder;
    private Handler handler;
    WebView news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        news = (WebView) findViewById(R.id.webview);
        newsUrl = getIntent().getStringExtra("news_url");



        WebSettings webSettings= news.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setJavaScriptEnabled(true);//支持javascript

        webSettings.setSupportZoom(true);//支持缩放
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);//不显示缩放按钮
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//自动适配
        webSettings.setLoadWithOverviewMode(true);

        news.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        news.getSettings().setSupportMultipleWindows(true);
        news.setWebViewClient(new WebViewClient());
        news.setWebChromeClient(new WebChromeClient());

        news.loadUrl(newsUrl);


    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && news.canGoBack()) {
            news.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }




}
