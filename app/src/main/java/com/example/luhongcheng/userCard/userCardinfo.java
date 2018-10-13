package com.example.luhongcheng.userCard;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.luhongcheng.R;
import com.example.luhongcheng.item4;

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


public class userCardinfo extends Activity {

    private List<userCard> newsList;
    private userCardAdapter adapter;
    private Handler handler;
    private ListView lv;

    private Button sendpostdata;
    private OkHttpClient okHttpClient;
    private OkHttpClient.Builder builder;
    List<String> cookies;
    String str;
    String xuehao;
    String mima;

    String LOGINURL1 = "http://myportal.sit.edu.cn/userPasswordValidate.portal";

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usercardinfo);
        newsList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.news_lv);

        builder = new OkHttpClient.Builder();
        okHttpClient = builder.build();
        progressBar = (ProgressBar) findViewById(R.id.progressBarNormal) ;


        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    progressBar.setVisibility(View.GONE);
                    adapter = new userCardAdapter(userCardinfo.this,newsList);
                    lv.setAdapter(adapter);

                }
            }
        };
        check();

    }



    private void check() {
        SharedPreferences spCount = getSharedPreferences("userid", 0);
        xuehao= spCount.getString("username", "");
        mima= spCount.getString("password", "");
        if(xuehao.length()==10&&mima.length()>=4){
            postdata();
        }
        else {
            Toast.makeText(userCardinfo.this,"你还没有输入账号", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);

        }

    }


    public void postdata() {
        // 开启线程来发起网络请求
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
                            .url(LOGINURL1)
                            .post(requestBody)
                            .build();

                    Response response1 = client.newCall(request1).execute();
                    final Headers headers = response1.headers();
                    HttpUrl loginUrl = request1.url();

                    cookies = headers.values("Set-Cookie");
                    //Log.d("cookie信息", "onResponse-size: " + cookies);

                    String[] strs = cookies.toArray(new String[cookies.size()]);
                    for (int i = 0; i < strs.length; ++i) {
                        str = strs[i];
                    }

                    Request request = new Request.Builder()
                            .url("http://card.sit.edu.cn/personalxiaofei.jsp")
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
                          //   Log.d("源代码", "onResponse: " + response.body().string().toString());
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
                        Element url = doc.getElementById("table");   //依据ID取值
                        Elements link =  url.getElementsByTag("tr");

                        for(int j = 1;j < link.size();j++){
                            String a1 = link.get(j).select("td").get(2).text();
                            //System.out.println("a1"+a1.toString());

                            String aa = link.get(j).select("td").get(3).text();

                            a1 = a1 +"  "+ aa;

                            String a2 = link.get(j).select("td").get(4).text();
                           // System.out.println("a2"+a2.toString());

                            String a3 = link.get(j).select("td").get(5).text();
                           // System.out.println("a3"+a3.toString());

                            userCard news = new userCard(a1,a2,a3);
                            newsList.add(news);
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




