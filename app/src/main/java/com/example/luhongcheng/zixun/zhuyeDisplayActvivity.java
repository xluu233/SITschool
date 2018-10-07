package com.example.luhongcheng.zixun;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.luhongcheng.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

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
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//自动适配
        webSettings.setLoadWithOverviewMode(true);

        news.getSettings().setBlockNetworkImage(false);

        news.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        news.getSettings().setSupportMultipleWindows(true);
        news.setWebViewClient(new WebViewClient());
        news.setWebChromeClient(new WebChromeClient());

        webSettings.setLoadsImagesAutomatically(true);

        news.loadUrl(newsUrl);

        final FloatingActionMenu fab = (FloatingActionMenu) findViewById(R.id.fab);
        fab.setClosedOnTouchOutside(true);
        FloatingActionButton refresh = (FloatingActionButton)findViewById(R.id.fab_preview);
        FloatingActionButton zhuan = (FloatingActionButton)findViewById(R.id.zhuan);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                news.reload();
            }
        });
        zhuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nowURL = news.getUrl();

                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(nowURL));
                startActivity(intent);

            }
        });



    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && news.canGoBack()) {
            news.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }




}
