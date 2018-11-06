package com.example.luhongcheng.OAitem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.luhongcheng.R;
import com.example.luhongcheng.SWZL.send;
import com.example.luhongcheng.SWZL.send2;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * Created by alex233 on 2018/6/15.
 */

public class item8 extends AppCompatActivity{
    private WebView webView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item8);
        webView = (WebView) findViewById(R.id.library);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String javascript =  "javascript:function hideOther() {" +
                        "document.getElementsByClassName('appDownload clearfix')[0].remove(); }";

                view.loadUrl(javascript);
                view.loadUrl("javascript:hideOther();");
            }

        });
        webView.loadUrl("http://m.5read.com/");




        WebSettings webSettings=webView.getSettings();
        /**
         * LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
         * LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
         * LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
         * LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
         */
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据.
        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        //不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);


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
                webView.reload();
            }
        });
        zhuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nowURL = webView.getUrl();

                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(nowURL));
                startActivity(intent);

            }
        });

    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
