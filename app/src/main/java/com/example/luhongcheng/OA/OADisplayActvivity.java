package com.example.luhongcheng.OA;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luhongcheng.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OADisplayActvivity extends AppCompatActivity {

    private String newsUrl;
    String str;
    String T0;
    String T1;
    String T2 = "";

    String title;
    String titleUrl;
    List<String> cookies;
    String xuehao;
    String mima;

    ListView lv;
    private OkHttpClient okHttpClient;
    private OkHttpClient.Builder builder;
    private Handler handler,handler2;
    private List<xiazai> newsList =  new ArrayList<>();
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oa_display);

        newsUrl = getIntent().getStringExtra("news_url");

        final TextView title0 = (TextView) findViewById(R.id.title0);
        final TextView title1 = (TextView) findViewById(R.id.title1) ;
        final TextView title2 = (TextView) findViewById(R.id.title2) ;


        lv = (ListView)findViewById(R.id.lv);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    title0.setText(getT0(T0));
                    title1.setText(getT1(T1));
                    title2.setText(getT2(T2));

                    AssetManager mgr = getAssets();
                    Typeface tf = Typeface.createFromAsset(mgr, "fonts/fangsong.TTF");//仿宋
                    title0.setTypeface(tf);
                    title1.setTypeface(tf);
                    title2.setTypeface(tf);

                }
            }
            private String getT0(String T0) {
                return T0;
            }
            private String getT1(String T1) {
                return T1;
            }
            private String getT2(String T2) {
                return T2;
            }

        };

        handler2 = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    lv.setAdapter(new Downloadadapter(getApplicationContext(),newsList));
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            xiazai url = newsList.get(position);
                            Intent intent = new Intent();
                            intent.setData(Uri.parse(url.getUrl()));//Url 就是你要打开的网址
                            intent.setAction(Intent.ACTION_VIEW);
                            startActivity(intent); //启动浏览器
                        }
                    });


                }
            }
        };

        getID();
        getmessage();

    }


    private void getID() {
        SharedPreferences spCount = getSharedPreferences("userid", 0);
        xuehao= spCount.getString("username", "");
        mima= spCount.getString("password", "");
    }

    private void getmessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("goto", "http://myportal.sit.edu.cn/loginSuccess.portal")
                            .add("gotoOnFail", "http://myportal.sit.edu.cn/loginFailure.portal")
                            .add("Login.Token1",xuehao)
                            .add("Login.Token2",mima)
                            .build();
                    Request request1 = new Request.Builder()
                            .url("http://myportal.sit.edu.cn/userPasswordValidate.portal")
                            .post(requestBody)
                            .build();

                    Response response1 = client.newCall(request1).execute();
                    final Headers headers = response1.headers();
                    HttpUrl loginUrl = request1.url();

                    cookies = headers.values("Set-Cookie");
                   // Log.d("cookie信息", "onResponse-size: " + cookies);

                    String[] strs = cookies.toArray(new String[cookies.size()]);
                    for (int i = 0; i < strs.length; ++i) {
                        str = strs[i];
                    }

                    Request request = new Request.Builder()
                            .url(newsUrl)
                            .header("Accept", "text/html, application/xhtml+xml, image/jxr, */*")
                            .header("Accept-Language", "zh-Hans-CN,zh-Hans;q=0.5")
                            .header("Connection", "Keep-Alive")
                            .header("Cookie", str)
                            .header("Host", "myportal.sit.edu.cn")
                            .header("Referer", "http://myportal.sit.edu.cn/userPasswordValidate.portal")
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    getNews(responseData);
                    getXiazai(responseData);

                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(okhttp3.Call call, IOException e) {
                        }
                        @Override
                        public void onResponse(okhttp3.Call call, Response response) throws IOException {
                            // Log.d("源代码", "onResponse: " + response.body().string().toString());
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void getXiazai(final String responseData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Document doc = Jsoup.parse(responseData);
                    Elements url = doc.getElementById("mainContentFrame").select("table").get(0).select("tbody").select("tr").select("td");
                    if (url.size() != 0){

                        //System.out.println("妈的数量到底是多少："+url.size());
                        for(int j = 0;j<url.size();j++){
                            titleUrl = url.select("a").attr("href");
                            titleUrl = "http://myportal.sit.edu.cn/"+titleUrl;

                            //http://myportal.sit.edu.cn/attachmentDownload.portal?notUseCache=true&attachmentId=fa965403-a753-11e8-b606-13e308f774a2
                            title = url.select("a").text();
                            //System.out.println("title"+title);
                            //System.out.println("titleurl"+titleUrl);

                            xiazai xixi = new xiazai(title,titleUrl);
                            newsList.add(xixi);
                        }

                    }

                    if (newsList.size() != 0 ){
                        Message msg = new Message();
                        msg.what = 1;
                        handler2.sendMessage(msg);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void getNews(final String responseData){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Document doc = Jsoup.parse(responseData);
                    //Element url = doc.getElementById("containerFrame");   //依据ID取值
                    Elements link =  doc.getElementsByClass("bulletin-title");

                    for(int j = 0;j < link.size();j++){
                        T0 = link.get(j).text();
                       // System.out.println("T0"+T0.toString());
                    }

                    Document doc1 = Jsoup.parse(responseData);
                    //Element url = doc.getElementById("containerFrame");   //依据ID取值
                    Elements link1 =  doc1.getElementsByClass("bulletin-info");

                    for(int j = 0;j < link1.size();j++){
                        T1 = link1.get(j).text();
                      //  System.out.println("T1"+T1.toString());
                    }

                    Document doc2 = Jsoup.parse(responseData);


                    Elements link2 =  doc2.getElementsByClass("MsoNormal");
                    int n = link2.select("span").size();
                    if (link2.size() == 0){
                        Elements link3 =  doc2.getElementsByClass("bulletin-content").select("div");
                        for (int j = 0; j<link3.size();j++){
                            String head = link3.get(j).text();
                            T2 = T2 +head+"     \n       ";
                        }
                    }else {
                        Elements link3 =  doc2.getElementsByClass("bulletin-content").select("span");
                        for (int j = 0; j<link3.size()-n;j++){
                            String head = link3.get(j).text();
                            T2 = T2 +head;
                        }

                        T2 = T2+"   \n";
                        for (int j = 0; j<link2.size();j++){
                            String T = link2.get(j).text();
                            System.out.println("T:"+T);
                            T2 = T2+T+"     \n       ";
                        }

                    }

                    /*
                    for(int j = 0;j < link2.size();j++){
                        T2 = link2.get(j).text();
                        System.out.println("T2"+T2.toString());
                    }*/


                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
