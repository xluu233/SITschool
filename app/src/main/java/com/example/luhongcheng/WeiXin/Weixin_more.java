package com.example.luhongcheng.WeiXin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.luhongcheng.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.view.KeyEvent.KEYCODE_BACK;

public class Weixin_more extends Activity {
    WebView wb;
    String weixin_Url = "http://weixin.sogou.com/weixinwap?ie=utf8&s_from=input&type=1&t=1537445736414&pg=webSearchList&_sug_=y&_sug_type_=&query=%E4%B8%8A%E6%B5%B7%E5%BA%94%E7%94%A8%E6%8A%80%E6%9C%AF%E5%A4%A7%E5%AD%A6";
    String xuexiao_url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        wb = (WebView) findViewById(R.id.webview);

        WebSettings webSettings= wb.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持javascript
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//自动适配
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLoadsImagesAutomatically(true);
        wb.getSettings().setBlockNetworkImage(false);
        wb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wb.getSettings().setSupportMultipleWindows(true);
        wb.setWebViewClient(new WebViewClient());
        wb.setWebChromeClient(new WebChromeClient());


        postdatax();

       // wb.loadUrl(weixin_Url);

        final FloatingActionMenu fab = (FloatingActionMenu) findViewById(R.id.fab);
        fab.setClosedOnTouchOutside(true);
        FloatingActionButton refresh = (FloatingActionButton)findViewById(R.id.fab_preview);
        FloatingActionButton zhuan = (FloatingActionButton)findViewById(R.id.zhuan);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wb.reload();
            }
        });

        zhuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nowURL = wb.getUrl();
                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(nowURL));
                startActivity(intent);

            }
        });
    }

    public void postdatax() {
        Thread thread_weixin = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    Request request = new Request.Builder()
                            .url(weixin_Url)
                            .build();
                    Response response = client.newCall(request).execute();
                    Document doc = Jsoup.parse(response.body().string());
                    Elements url = doc.getElementsByClass("gzh-box");
                    xuexiao_url = url.get(0).select("a").attr("href");

                    if (xuexiao_url.length() != 0 || xuexiao_url !=null){
                        load_weixin(xuexiao_url);
                    }else {
                        load_weixin(weixin_Url);
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread_weixin.start();
    }

    private void load_weixin(final String url) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                wb.loadUrl(url);
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && wb.canGoBack()) {
            wb.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
