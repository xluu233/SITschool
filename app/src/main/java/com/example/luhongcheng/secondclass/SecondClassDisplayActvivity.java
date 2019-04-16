package com.example.luhongcheng.secondclass;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.luhongcheng.R;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SecondClassDisplayActvivity extends AppCompatActivity {

    private String newsUrl;
    private String str;
    private String T0;
    private String T1;
    private String T2;
    private String shenqingURL;
    TextView title0;
    TextView title1;
    TextView title2;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondclass_display);
        newsUrl = getIntent().getStringExtra("news_url");

        title0= (TextView) findViewById(R.id.title0);
        title1 = (TextView) findViewById(R.id.title1) ;
        title2 = (TextView) findViewById(R.id.title2) ;
        Button bt = (Button) findViewById(R.id.shenqing);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences spCount = getSharedPreferences("SecondCookie", 0);
        str = spCount.getString("cookie", "");
        getmessage();

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shenqing();
                Toast.makeText(SecondClassDisplayActvivity.this,"已帮你申请（请在首页刷新查看结果，若没有则是申请失败，可能原因是人数已满）",Toast.LENGTH_LONG).show();
            }
        });

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
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

                    T0 = link.select("h1").text();
                    //System.out.println("T0"+T0.toString());

                    T1 = link.select("div").get(1).text();
                    //System.out.println("T2"+T1.toString());

                    T2 = "       "+link.select("div").get(2).text();


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
