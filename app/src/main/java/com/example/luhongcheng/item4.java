package com.example.luhongcheng;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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


public class item4 extends AppCompatActivity implements View.OnClickListener {

    private List<grade> newsList;
    private gradeAdapter adapter;
    private Handler handler;
    private ListView lv;
    String a1;
    String a2;
    String a3;
    String a4;
    String a5;
    String a6;
    String a7;
    String a8;
    String a9;
    String xuehao;
    String mima;
    private OkHttpClient okHttpClient;
    private OkHttpClient.Builder builder;
    List<String> cookies;
    String b = "http://ems.sit.edu.cn/";
    String c="http://ems.sit.edu.cn:85/student/graduate/scorelist.jsp";//成绩链接
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item4);
        newsList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.news_lv);

        Button sendpostdata = (Button) findViewById(R.id.send_request);
        sendpostdata.setOnClickListener(this);
        builder = new OkHttpClient.Builder();
        okHttpClient = builder.build();
        progressBar = (ProgressBar) findViewById(R.id.progressBarNormal) ;


        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    progressBar.setVisibility(View.GONE);
                    adapter = new gradeAdapter(item4.this,newsList);
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            grade news = newsList.get(position);

                           //Intent intent2 = new Intent(MainActivity.this,NewsDisplayActvivity.class);
                            //intent2.putExtra("COOKIE",str);
                            //startActivity(intent2);
                            //此处不能传递COOKIE，可能会混淆
                        }
                    });
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

    }


    public void onClick(View v) {
        if(xuehao.length()==10&&mima.length()>=4){
            postdata();
        }
        else {
            Toast.makeText(item4.this,"你还没有输入账号", Toast.LENGTH_SHORT).show();
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
                            .url(c)
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
                            // Log.d("源代码", "onResponse: " + response.body().string().toString());
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
                        //Elements url = doc.select("table-100%");    //依据ID取值
                        Elements link =  doc.getElementsByTag("tr");



                        for(int j = 4; j < link.size(); j++){

                            a1 = link.get(j).select("td").get(0).text();
                            System.out.println("a1"+a1.toString());

                            a2 = link.get(j).select("td").get(1).text();
                            System.out.println("a2"+a1.toString());

                            a3 = link.get(j).select("td").get(2).text();
                            System.out.println("a3"+a1.toString());

                            a4 = link.get(j).select("td").get(3).text();
                            System.out.println("a4"+a1.toString());

                            a5 = link.get(j).select("td").get(4).text();
                            System.out.println("a5"+a1.toString());

                            a6 = link.get(j).select("td").get(5).text();
                            System.out.println("a6"+a1.toString());

                            a7 = link.get(j).select("td").get(6).text();
                            System.out.println("a7"+a1.toString());

                            a8 = link.get(j).select("td").get(7).text();
                            System.out.println("a8"+a1.toString());

                            a9 = link.get(j).select("td").get(8).text();
                            System.out.println("a9"+a1.toString());


                            grade news = new grade(a1,a2,a3,a4,a5,a6,a7,a8,a9);
                            newsList.add(news);
                            memInfo(a1,a2,a3,a4,a5,a6,a7,a8,a9);
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

    /*保存密码-嘻嘻*/
    private void memInfo(String a1, String a2, String a3, String a4, String a5, String a6, String a7, String a8, String a9){
        SharedPreferences.Editor editor=getSharedPreferences("chengji",0).edit();
        editor.putString("a1",a1);
        editor.putString("a2",a2);
        editor.putString("a3",a3);
        editor.putString("a4",a4);
        editor.putString("a5",a5);
        editor.putString("a6",a6);
        editor.putString("a7",a7);
        editor.putString("a8",a8);
        editor.putString("a9",a9);
        editor.commit();
    }

    private void restoreInfo(String a1, String a2, String a3, String a4, String a5, String a6, String a7, String a8, String a9){
        SharedPreferences sp=getSharedPreferences("chengji",0);
    }


    }




