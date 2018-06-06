package com.example.luhongcheng.NEWS;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.luhongcheng.R;

import java.util.HashMap;
import java.util.Map;

public class NewsDisplayActvivity extends AppCompatActivity {

    private String newsUrl;
    String str;
    Map<String,String> extraHeaders = new HashMap<String, String>();
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_display);

        newsUrl = getIntent().getStringExtra("news_url");
        //System.out.println(newsUrl.toString());

        //str = getIntent().getStringExtra("COOKIE");
        //System.out.println(str.toString());
        //接受COOKIE后会打不开网页

        getcookie();
        System.out.println(str.toString());

        newsUrl = getIntent().getStringExtra("news_url");
        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());



        extraHeaders.put("Accept", "text/html, application/xhtml+xml, image/jxr, */*");
        extraHeaders.put("Accept-Language", "zh-Hans-CN,zh-Hans;q=0.5");
        extraHeaders.put("Connection", "Keep-Alive");
        extraHeaders.put("Cookie", str);
        extraHeaders.put("Host", "myportal.sit.edu.cn");
        extraHeaders.put("Upgrade-Insecure-Requests","1");
        extraHeaders.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3423.2 Safari/537.36");
        webView.loadUrl(newsUrl,extraHeaders);

    }


    private void getcookie() {
        SharedPreferences spCount = getSharedPreferences("cookies", 0);
        str= spCount.getString("iPlanetDirectoryPro", "");
    }


}
