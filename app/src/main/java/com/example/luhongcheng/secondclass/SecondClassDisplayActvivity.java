package com.example.luhongcheng.secondclass;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luhongcheng.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SecondClassDisplayActvivity extends AppCompatActivity {

    private String newsUrl;
    String str;
    String T1;
    String T2;
    String shenqingURL;

    private OkHttpClient okHttpClient;
    private OkHttpClient.Builder builder;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondclass_display);

        newsUrl = getIntent().getStringExtra("news_url");

        final TextView title1 = (TextView) findViewById(R.id.title1) ;
        final TextView title2 = (TextView) findViewById(R.id.title2) ;
        Button bt = (Button) findViewById(R.id.shenqing);
        getCookies();
        getmessage();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    title1.setText(getT1(T1));
                    title2.setText(getT2(T2));

                }
            }
            private String getT1(String T1) {
                return T1;
            }
            private String getT2(String T2) {
                return T2;
            }

        };

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shenqing();
                Toast.makeText(SecondClassDisplayActvivity.this,"已帮你申请（请在首页刷新查看结果，若没有则是申请失败，可能原因是人数已满）",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void shenqing() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final OkHttpClient client = new OkHttpClient().newBuilder()
                            .followRedirects(false)//禁止重定向
                            .followSslRedirects(false)//哈哈哈哈哈哈哈好开心啊
                            .build();

                    shenqingURL = newsUrl.substring(newsUrl.length()-7);
                    shenqingURL = "http://sc.sit.edu.cn/public/pcenter/applyActivity.action?activityId="+shenqingURL;

                    Request request = new Request.Builder()
                            .url(shenqingURL)
                            .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                            .header("Accept-Language","zh-CN,zh;q=0.9")
                            .header("Connection", "Keep-Alive")
                            .header("Cookie", str)
                            .header("Host", "sc.sit.edu.cn")
                            .header("Upgrade-Insecure-Requests","1")
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.7 Safari/537.36")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }


    //extraHeaders.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
    //extraHeaders.put("Accept-Encoding","gzip, deflate");
    //extraHeaders.put("Accept-Language","zh-CN,zh;q=0.9");
    //extraHeaders.put("Connection","keep-alive");
    //extraHeaders.put("Cookie",str);
    //extraHeaders.put("Host","sc.sit.edu.cn");
    //extraHeaders.put("Upgrade-Insecure-Requests","1");
    //extraHeaders.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.7 Safari/537.36");
    //webView.loadUrl(newsUrl,extraHeaders);


    private void getCookies() {
        SharedPreferences spCount = getSharedPreferences("SecondCookie", 0);
        //在fragment中用share方法要getActivity（）
        str = spCount.getString("cookie", "");
    }

    private void getmessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final OkHttpClient client = new OkHttpClient().newBuilder()
                            .followRedirects(false)//禁止重定向
                            .followSslRedirects(false)//哈哈哈哈哈哈哈好开心啊
                            .build();


                    Request request4 = new Request.Builder()
                            .url(newsUrl)
                            .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                            .header("Accept-Language","zh-CN,zh;q=0.9")
                            .header("Connection", "Keep-Alive")
                            .header("Cookie", str)
                            .header("Host", "sc.sit.edu.cn")
                            .header("Upgrade-Insecure-Requests","1")
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.7 Safari/537.36")
                            .build();
                    Response response4 = client.newCall(request4).execute();
                    String responseData4 = response4.body().string();
                    getNews(responseData4);




                    okHttpClient.newCall(request4).enqueue(new Callback() {
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


    private void getNews(final String responseData4){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Document doc = Jsoup.parse(responseData4);
                    //Element url = doc.getElementById("pf8271");   //依据ID取值
                    Elements link =  doc.getElementsByClass("box-1");

                    for(int j = 0;j < link.size();j++){
                        String A1 = link.get(j).select("class.title_8").text();
                        System.out.println("A1"+A1.toString());

                        T1 = link.get(j).select("div").get(1).text();
                        System.out.println("T2"+T1.toString());

                        T2 = link.get(j).select("div").get(2).text();
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
