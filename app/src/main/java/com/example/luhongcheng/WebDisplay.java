package com.example.luhongcheng;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.luhongcheng.utils.BaseStatusBarActivity;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import okhttp3.OkHttpClient;

import static android.view.KeyEvent.KEYCODE_BACK;

public class WebDisplay extends BaseStatusBarActivity {

    private String newsUrl;
    String title = null;
    private OkHttpClient okHttpClient;
    private OkHttpClient.Builder builder;
    private Handler handler;
    FloatingActionButton refresh,zhuan,share,finish;
    WebView news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        news = (WebView) findViewById(R.id.webview);
        newsUrl = getIntent().getStringExtra("news_url");
        title = getIntent().getStringExtra("title");
        FloatingActionMenu fab = (FloatingActionMenu) findViewById(R.id.fab);
        fab.setClosedOnTouchOutside(true);
        refresh = (FloatingActionButton)findViewById(R.id.refresh);
        zhuan = (FloatingActionButton)findViewById(R.id.zhuan);
        share = findViewById(R.id.share);
        finish = findViewById(R.id.finish);

        onClick();

        Toolbar toolbar = (Toolbar) findViewById(R.id.web_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (title.length() !=0){
            toolbar.setTitle(title);
        }
        toolbar.setSubtitle(newsUrl);


        Log.d("web",newsUrl);
        if (newsUrl.equals("http://m.5read.com/")){
            news.setWebViewClient(new WebViewClient(){
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
        } else if (newsUrl.contains("sohu")){
            news.setWebViewClient(new WebViewClient(){
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
        } else if (newsUrl.contains("static.owspace.com")){
            news.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    String javascript =  "javascript:function hideOther() {" +
                            "document.getElementsByClassName('goapp')[0].remove(); }";

                    view.loadUrl(javascript);
                    view.loadUrl("javascript:hideOther();");
                }

            });
        }else {
            news.setWebViewClient(new WebViewClient());
        }
        news.setWebChromeClient(new WebChromeClient());
        news.getSettings().setBlockNetworkImage(false);
        news.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        news.getSettings().setSupportMultipleWindows(true);
        WebSettings webSettings= news.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setJavaScriptEnabled(true);//支持javascript
        webSettings.setSupportZoom(true);//支持缩放
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//自动适配
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLoadsImagesAutomatically(true);
        news.loadUrl(newsUrl);

    }

    private void onClick() {
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

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "上应助手资讯分享："+news.getTitle()+news.getUrl());
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "分享到："));

            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebDisplay.this.finish();
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

    @Override
    protected int getStatusBarColor() {
        return getResources().getColor(R.color.colorAccent);
    }

}
