package com.example.luhongcheng.OAitem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.luhongcheng.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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


public class item5 extends Activity implements View.OnClickListener {


    private Handler handler;
    private EditText chaxun;
    private OkHttpClient okHttpClient;
    private OkHttpClient.Builder builder;
    List<String> cookies;
    String classroomid;
    String xuehao;
    String mima;
    String LOGINURL1 = "http://myportal.sit.edu.cn/userPasswordValidate.portal";
    String dianfei = "http://card.sit.edu.cn/dk_xxmh.jsp";
    private ProgressBar progressBar;

    String name;
    String a1;
    String a2;
    String a3;
    String a4;



    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item5);


        Button sendpostdata = (Button) findViewById(R.id.send_request);
        chaxun = (EditText) findViewById(R.id.chaxun);
        sendpostdata.setOnClickListener(this);
        builder = new OkHttpClient.Builder();
        okHttpClient = builder.build();
        progressBar = (ProgressBar) findViewById(R.id.progressBarNormal) ;

        final TextView title0 = (TextView) findViewById(R.id.room1);
        final TextView title1 = (TextView) findViewById(R.id.A1) ;
        final TextView title2 = (TextView) findViewById(R.id.B1) ;
        final TextView title3 = (TextView) findViewById(R.id.C1);
        final TextView title4 = (TextView) findViewById(R.id.D1) ;

        restoreInfo();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    title0.setText(getT(name));
                    title1.setText(getT0(a1));
                    title2.setText(getT1(a2));
                    title3.setText(getT2(a3));
                    title4.setText(getT3(a4));
                }
            }
            private String getT(String name) {
                return name;
            }
            private String getT0(String a1) {
                return a1;
            }
            private String getT1(String a2) {
                return a2;
            }
            private String getT2(String a3) {
                return a3;
            }
            private String getT3(String a4) {
                return a4;
            }
        };

        getID();
    }



    private void getID() {
        SharedPreferences spCount = getSharedPreferences("userid", 0);
        xuehao= spCount.getString("username", "");
        mima= spCount.getString("password", "");

    }

    public void onClick(View v) {
        if (v.getId() == R.id.send_request) {
            if(xuehao.length()==10&&mima.length()>=4){
                postdata();
                classroomid = chaxun.getText().toString();
                if (classroomid.length()==6){
                    memInfo(classroomid);
                }
                else{
                    Toast.makeText(item5.this,"格式错误",Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(item5.this,"你还没有输入账号，请重启登入学号", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*保存密码-嘻嘻*/
    private void memInfo(String classroomid){
        SharedPreferences.Editor editor = getSharedPreferences("data1", 0).edit();
        editor.putString("classroomid",classroomid);
        editor.apply();
    }

    private void restoreInfo(){
        SharedPreferences sp=getSharedPreferences("data1",0);
        chaxun.setText(sp.getString("classroomid",""));

    }
    /**保存密码就到这里了*/

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
                            .add("Login.Token1", xuehao)
                            .add("Login.Token2", mima)
                            // .add("authtype","2")
                            // .add("Login.Token1","")
                            // .add("Login.Token2","")
                            // .add("loginName","1510400642")
                            // .add("loginYzm","")
                            // .add("password","199709")
                            .build();
                    Request request1 = new Request.Builder()
                            .url(LOGINURL1)
                            .post(requestBody)
                            .build();

                    Response response1 = client.newCall(request1).execute();
                    final Headers headers = response1.headers();
                    //Log.d("头信息", "header " + headers);
                    HttpUrl loginUrl = request1.url();
                    //List<Cookie> cookies = Cookie.parseAll(loginUrl, headers);  //这是另一种获取cookie的方法
                    cookies = headers.values("Set-Cookie");
                    //Log.d("cookie信息", "onResponse-size: " + cookies);

                    //List<String>是String数组集合，先转换成String[],在转换成String
                    String[] strs = cookies.toArray(new String[cookies.size()]);
                    String str = null;
                    for (int i = 0; i < strs.length; ++i) {
                        str = strs[i];
                    }

                    RequestBody requestBody1 = new FormBody.Builder()
                            .add("actionType","init")
                            .add("fjh",chaxun.getText().toString())
                            .add("selectstate","on")
                            .build();
                    Request request = new Request.Builder()
                            .url(dianfei)
                            .header("Accept","text/html, application/xhtml+xml, image/jxr, */*")
                            .header("Accept-Language","zh-Hans-CN,zh-Hans;q=0.5")
                            .header("Cache-Control","no-cache")
                            .header("Connection","Keep-Alive")
                            .header("Content-Length","41")
                            .header("Content-Type","application/x-www-form-urlencoded")
                            .header("Cookie",str)
                            .header("Host","card.sit.edu.cn")
                            .header("Referer","http://card.sit.edu.cn/dk_xxmh.jsp")
                            .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko")
                            .post(requestBody1)
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
                            //Log.d("源代码", "onResponse: " + response.body().string().toString());
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
                    //System.out.println(link.get(0).toString());

                    for(int j = 1;j < link.size();j++){
                        //为什么J=1呢，因为=0会输出一组空值
                        //List将数值表示为集合，在下面进行分解
                        List<String> name1 = link.get(j).select("td").eachText();
                        String[] names = name1.toArray(new String[name1.size()]);

                        for (int i = 0; i < names.length; ++i) {
                            name = names[i=0];
                            a1 = names[i=1];
                            a2 = names[i=2];
                            a3 = names[i=3];
                            a4 = names[i=4];
                        }
                        //System.out.println("name:"+name.toString());
                        //System.out.println("a1:"+a1.toString());
                        //System.out.println("a2:"+a2.toString());
                        //System.out.println("a3:"+a3.toString());
                        // System.out.println("a4:"+a4.toString());


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




