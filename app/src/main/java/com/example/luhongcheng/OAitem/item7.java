package com.example.luhongcheng.OAitem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.luhongcheng.R;
import com.example.luhongcheng.bean.Test;
import com.example.luhongcheng.Adapter.TestAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//考试安排
public class  item7 extends Activity {

    private List<Test> newsList;
    private TestAdapter adapter;
    private Handler handler;
    private ListView lv;

    private OkHttpClient okHttpClient;
    private OkHttpClient.Builder builder;
    List<String> cookies;
    String b = "http://ems.sit.edu.cn/";
    String a2;


    String xuehao;
    String mima;

    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;


    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item7);
        newsList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.news_lv);


        builder = new OkHttpClient.Builder();
        okHttpClient = builder.build();
        progressBar = (ProgressBar) findViewById(R.id.progressBarNormal) ;


        refreshLayout = findViewById(R.id.bk_refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if(xuehao.length()==10&&mima.length()>=4){
                            newsList.clear();
                            postdata();
                        }

                        try {
                            Thread.sleep(2000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    refreshLayout.setRefreshing(false);
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    }
                }).start();
            }
        });


        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            //解决原生SwipeRefreshLayout与原生listview的滑动冲突
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) { }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                boolean enable = false;
                if (lv != null && lv.getChildCount() > 0) {
                    boolean firstItemVisible = lv.getFirstVisiblePosition() == 0;
                    boolean topOfFirstItemVisible = lv.getChildAt(0).getTop() == 0;
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                refreshLayout.setEnabled(enable);
            }
        });

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    progressBar.setVisibility(View.GONE);
                    adapter = new TestAdapter(item7.this,newsList);
                    lv.setAdapter(adapter);
                }
            }
        };
        getID();
        postdata();

    }

    private void getID() {
        SharedPreferences spCount = getSharedPreferences("userid", 0);
        xuehao= spCount.getString("username", "");
        mima= spCount.getString("password", "");

        if(xuehao.length()==0){
            Toast.makeText(item7.this,"你还没有输入账号", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
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
                            .url(b)
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
                    //Log.d("头信息11", "header " + headers1);
                    cookies = headers1.values("Set-Cookie"); //这是另一种获取cookie的方法
                    //Log.d("JSESSIONID11", "onResponse-size: " + cookies);

                    String[] aa = cookies.toArray(new String[cookies.size()]);
                    String str1 = null;
                    String str2 = null;
                    for (int i = 0; i < aa.length; ++i) {
                        str1 = aa[i=0];
                        str2 = aa[i=1];
                    }
                    //System.out.println("1:"+str1.toString());
                    //System.out.println("2:"+str2.toString());

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
                    //Log.d("头信息22", "header " + headers2);
                    cookies = headers2.values("Set-Cookie"); //这是另一种获取cookie的方法
                    //Log.d("cookie22", "onResponse-size: " + cookies);

                    String[] bb = cookies.toArray(new String[cookies.size()]);
                    String str3 = null;
                    String str4 = null;
                    String str5 = null;
                    for (int i = 0; i < bb.length; ++i) {
                        str3 = bb[i=0];
                        str4 = bb[i=1];
                        str5 = bb[i=2];
                    }
                    //System.out.println("3:"+str3.toString());
                    //System.out.println("4:"+str4.toString());
                    //System.out.println("5:"+str5.toString());

                    String str = str1+";"+str2+";"+str3+";"+str4;
                    //System.out.println("总共的cookies:"+str.toString());

                    Request request3 = new Request.Builder()
                            .url("http://ems.sit.edu.cn:85/student/main.jsp")
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
                    getNews(responseData3);


                    okHttpClient.newCall(request3).enqueue(new Callback() {
                        @Override
                        public void onFailure(okhttp3.Call call, IOException e) {
                        }
                        @Override
                        public void onResponse(okhttp3.Call call, Response response) throws IOException {
                           // Log.e("源代码", "onResponse: " + response.body().string().toString());
                        }
                    });



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getNews(final String responseData3){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Document doc = Jsoup.parse(responseData3);
                    Elements link =  doc.getElementsByTag("tbody").get(5).select("tr");
                    for(int j = 2;j < link.size();j++){
                        int a1 = j-1;

                        a2 = link.get(j).select("td").text();
                        //System.out.println("考试安排："+a2.toString());
                        a2 = a2.replaceAll("第","\n 第");

                        Test ne  = new Test(a1,a2);
                        newsList.add(ne);
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






