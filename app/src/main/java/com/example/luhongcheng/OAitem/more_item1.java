package com.example.luhongcheng.OAitem;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.luhongcheng.R;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.List;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//教学计划
public class more_item1 extends Activity {
    private OkHttpClient okHttpClient;
    private OkHttpClient.Builder builder;
    List<String> cookies = null;
    WebView webView;
    String xuehao;
    String mima;
    String str;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_item1);
        webView = (WebView)findViewById(R.id.jiaoxue);

        webView.getSettings().setJavaScriptEnabled(true);

        WebSettings webSettings=webView.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);

        builder = new OkHttpClient.Builder();
        okHttpClient = builder.build();

        getID();
        postdata();


        FloatingActionButton refresh = (FloatingActionButton)findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postdata();
            }
        });

    }

    private void getID() {
        SharedPreferences spCount = getSharedPreferences("userid", 0);
        xuehao= spCount.getString("username", "");
        mima= spCount.getString("password", "");

        if(xuehao.length()==0){
            Toast.makeText(more_item1.this,"你还没有输入账号", Toast.LENGTH_SHORT).show();
        }

    }

    public void postdata() {
        // 开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final OkHttpClient client = new OkHttpClient().newBuilder()
                            .followRedirects(false)//禁止重定向
                            .followSslRedirects(false)//哈哈哈哈哈哈哈好开心啊
                            .build();

                    Request request1 = new Request.Builder()
                            .url("http://ems.sit.edu.cn/")
                            .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                            .addHeader("Accept-Language","zh-CN,zh;q=0.9")
                            .addHeader("Connection","Keep-Alive")
                            .addHeader("Host","ems.sit.edu.cn:85")
                            .addHeader("Upgrade-Insecure-Requests","1")
                            .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko")
                            .build();
                    Response response1 = client.newCall(request1).execute();
                    String responseData1 = response1.body().string();
                    final Headers headers1 = response1.headers();
                    cookies = headers1.values("Set-Cookie");

                    String[] aa = cookies.toArray(new String[cookies.size()]);
                    String str1 = null;
                    String str2 = null;
                    for (int i = 0; i < aa.length; ++i) {
                        str1 = aa[i=0];
                        str2 = aa[i=1];
                    }

                    RequestBody requestBody1 = new FormBody.Builder()
                            .add("loginName",xuehao)
                            .add("password",mima)
                            .add("authtype","2")
                            .add("loginYzm","")
                            .add("Login.Token1","")
                            .add("Login.Token2","")
                            .build();
                    Request request2 = new Request.Builder()
                            .url("http://ems.sit.edu.cn:85/login.jsp")
                            .post(requestBody1)
                            .header("Accept","text/html, application/xhtml+xml, image/jxr, */*")
                            .header("Accept-Language","zh-CN,zh;q=0.9")
                            .header("Cache-Control","max-age=0")
                            .header("Connection","Keep-Alive")
                            .header("Content-Length","85")
                            .header("Content-Type","application/x-www-form-urlencoded")
                            .header("Cookie",str1)
                            .header("Host","ems.sit.edu.cn:85")
                            .header("Origin","http://ems.sit.edu.cn:85")
                            .header("Referer","http://ems.sit.edu.cn:85/")
                            .addHeader("Upgrade-Insecure-Requests","1")
                            .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko")
                            .build();

                    Response response2 = client.newCall(request2).execute();
                    String responseData2 = response2.body().string();
                    final Headers headers2 = response2.headers();
                    cookies = headers2.values("Set-Cookie");

                    String[] bb = cookies.toArray(new String[cookies.size()]);
                    String str3 = null;
                    String str4 = null;
                    String str5 = null;
                    for (int i = 0; i < bb.length; ++i) {
                        str3 = bb[i=0];
                        str4 = bb[i=1];
                        str5 = bb[i=2];
                    }

                    String str = str1+";"+str2+";"+str3+";"+str4;

                    Request request3 = new Request.Builder()
                            .url("http://ems.sit.edu.cn:85/student/graduate/viewcreditdetail.jsp")
                            .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                            // .header("Accept-Encoding", "gzip, deflate")
                            .header("Accept-Language", "zh-CN,zh;q=0.9")
                            .header("Cache-Control","max-age=0")
                            .header("Connection", "Keep-Alive")
                            .header("Cookie",str)
                            .header("Host", "ems.sit.edu.cn:85")
                            .header("Referer", "http://ems.sit.edu.cn:85/")
                            .header("Upgrade-Insecure-Requests","1")
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko")
                            .build();
                    Response response3 = client.newCall(request3).execute();
                    String responseData3 = response3.body().string();
                    showResponse(responseData3);

                    okHttpClient.newCall(request3).enqueue(new Callback() {
                        @Override
                        public void onFailure(okhttp3.Call call, IOException e) {
                        }
                        @Override
                        public void onResponse(okhttp3.Call call, Response response) throws IOException {
                            //Log.d("源代码", "onResponse: " + response.body().string().toString());
                        }
                    });



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void showResponse(final String responseData3) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webView.loadData(responseData3, "text/html; charset=UTF-8", null);
            }
        });

    }


}
