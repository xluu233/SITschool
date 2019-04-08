package com.example.luhongcheng;

import android.annotation.SuppressLint;
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


public class SouHuNews extends AppCompatActivity {
    private WebView webview;
    FloatingActionButton refresh,zhuan,share,finish;
    String URL2= "http://m.sohu.com/media/694346?spm=smwp.content.author-info.1.1537437344995hk1YAuY";
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.souhu_news);

        String url = getIntent().getStringExtra("url");
        if (url != null){
            URL2 = url;
        }

        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setAllowFileAccessFromFileURLs(true);
        webview.loadUrl(URL2);
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
                        "document.getElementsByClassName('fixed_menu')[0].remove();" +
                        "document.getElementsByClassName('sub-header noFixed')[0].remove();+" +

                        //第二个界面
                        "document.getElementsByClassName('plate life_service article')[0].remove();" +
                        "document.getElementsByClassName('art-rec-news')[0].remove();" +
                        "document.getElementsByClassName('plate nav_maps article')[0].remove();" +
                        "document.getElementsByClassName('apk-banner-m-wrapper')[0].remove();"+
                        "document.getElementsByClassName('comment-reply__box comment-reply__bar topbox')[0].remove();"+
                        "document.getElementsByClassName('top-bill-wrapper bill-placeholder')[0].remove();" +
                        "document.getElementsByClassName('text-wrapper')[0].remove();" +
                        "document.getElementsByClassName('share-sns-wrapper')[0].remove();" +
                        "document.getElementsByClassName('nice-container')[0].remove(); }";

                view.loadUrl(javascript);
                view.loadUrl("javascript:hideOther();");
            }

        });

        WebSettings webSettings=webview.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        final FloatingActionMenu fab = (FloatingActionMenu) findViewById(R.id.fab);
        fab.setClosedOnTouchOutside(true);
        refresh = (FloatingActionButton)findViewById(R.id.refresh);
        zhuan = (FloatingActionButton)findViewById(R.id.zhuan);
        share = findViewById(R.id.share);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        onClick();

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void onClick() {
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

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, webview.getUrl());
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "分享到"));

            }
        });


    }
}
