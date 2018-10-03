package com.example.luhongcheng;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import static android.view.KeyEvent.KEYCODE_BACK;


public class item9 extends AppCompatActivity {
    private WebView webview;
    String URL1= "http://weixin.sogou.com/weixinwap?ie=utf8&s_from=input&type=1&t=1537445736414&pg=webSearchList&_sug_=y&_sug_type_=&query=%E4%B8%8A%E6%B5%B7%E5%BA%94%E7%94%A8%E6%8A%80%E6%9C%AF%E5%A4%A7%E5%AD%A6";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weixin);
        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setAllowFileAccessFromFileURLs(true);
        webview.loadUrl(URL1);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String javascript =  "javascript:function hideOther() {" +
                        // 第一个页面
                        "document.getElementsByClassName('tab-top')[0].remove();" +
                        "document.getElementsByClassName('header')[0].remove();+" +
                        "document.getElementsByClassName('account-txt')[0].remove();" +
                        "document.getElementsByClassName('footer')[0].remove();" +
                        "document.getElementsByClassName('back-top')[0].remove(); }";

                view.loadUrl(javascript);
                view.loadUrl("javascript:hideOther();");
            }

        });

        WebSettings webSettings=webview.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final FloatingActionMenu fab = (FloatingActionMenu) findViewById(R.id.fab);
        fab.setClosedOnTouchOutside(true);
        FloatingActionButton refresh = (FloatingActionButton)findViewById(R.id.fab_preview);
        FloatingActionButton zhuan = (FloatingActionButton)findViewById(R.id.zhuan);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview.reload();
            }
        });
        zhuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nowURL = webview.getUrl();

                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(nowURL));
                startActivity(intent);

            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}
