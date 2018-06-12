package com.example.luhongcheng.OA;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.luhongcheng.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
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
    String T2;
    List<String> cookies;
    String xuehao;
    String mima;

    private OkHttpClient okHttpClient;
    private OkHttpClient.Builder builder;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oa_display);

        newsUrl = getIntent().getStringExtra("news_url");

        final TextView title0 = (TextView) findViewById(R.id.title0);
        final TextView title1 = (TextView) findViewById(R.id.title1) ;
        final TextView title2 = (TextView) findViewById(R.id.title2) ;


        getmessage();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    title0.setText(getT0(T0));
                    title1.setText(getT1(T1));
                    title2.setText(getT2(T2));

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

        getID();

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
                    Log.d("cookie信息", "onResponse-size: " + cookies);

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
                        System.out.println("T0"+T0.toString());
                    }

                    Document doc1 = Jsoup.parse(responseData);
                    //Element url = doc.getElementById("containerFrame");   //依据ID取值
                    Elements link1 =  doc1.getElementsByClass("bulletin-info");

                    for(int j = 0;j < link1.size();j++){
                        T1 = link1.get(j).text();
                        System.out.println("T1"+T1.toString());
                    }

                    Document doc2 = Jsoup.parse(responseData);
                    //Element url = doc.getElementById("containerFrame");   //依据ID取值
                    Elements link2 =  doc2.getElementsByClass("bulletin-content");

                    for(int j = 0;j < link2.size();j++){
                        T2 = link2.get(j).text();
                        System.out.println("T2"+T2.toString());
                    }



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
