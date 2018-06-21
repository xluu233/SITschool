package com.example.luhongcheng;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

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

/**
 * Created by Administrator on 2018/4/8.
 */

@SuppressLint("ValidFragment")
public class TwoFragment extends Fragment implements View.OnClickListener {
    private String context;
    public TwoFragment(String context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.b_fragment,container,false);
        return view;
    }


    WebView wb;
    private static HttpClient client = new DefaultHttpClient();
    private OkHttpClient okHttpClient;
    private OkHttpClient.Builder builder;
    List<String> cookies = null;

    String URL = "http://ems1.sit.edu.cn:85/student/selCourse/syllabuslist2.jsp";
    String xuehao;
    String mima;
    String A;//第A周
    int B;
    private  List<String> table = new ArrayList<String>();

    String str;





    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        wb = (WebView) getActivity().findViewById(R.id.wb);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wb.getSettings().setSupportMultipleWindows(true);
        wb.setWebViewClient(new WebViewClient());
        wb.setWebChromeClient(new WebChromeClient());
        wb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        Button zong = (Button) getActivity().findViewById(R.id.zong);
        Button bt1 = (Button) getActivity().findViewById(R.id.zhou1);
        Button bt2 = (Button) getActivity().findViewById(R.id.zhou2);
        Button bt3 = (Button) getActivity().findViewById(R.id.zhou3);
        Button bt4 = (Button) getActivity().findViewById(R.id.zhou4);
        Button bt5 = (Button) getActivity().findViewById(R.id.zhou5);
        Button bt6 = (Button) getActivity().findViewById(R.id.zhou6);
        Button bt7 = (Button) getActivity().findViewById(R.id.zhou7);
        Button bt8 = (Button) getActivity().findViewById(R.id.zhou8);
        Button bt9 = (Button) getActivity().findViewById(R.id.zhou9);
        Button bt10 = (Button) getActivity().findViewById(R.id.zhou10);
        Button bt11 = (Button) getActivity().findViewById(R.id.zhou11);
        Button bt12 = (Button) getActivity().findViewById(R.id.zhou12);
        Button bt13 = (Button) getActivity().findViewById(R.id.zhou13);
        Button bt14 = (Button) getActivity().findViewById(R.id.zhou14);
        Button bt15 = (Button) getActivity().findViewById(R.id.zhou15);
        Button bt16 = (Button) getActivity().findViewById(R.id.zhou16);
        Button bt17 = (Button) getActivity().findViewById(R.id.zhou17);
        Button bt18 = (Button) getActivity().findViewById(R.id.zhou18);
        Button bt19 = (Button) getActivity().findViewById(R.id.zhou19);
        Button bt20= (Button) getActivity().findViewById(R.id.zhou20);


        zong.setOnClickListener(this);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        bt6.setOnClickListener(this);
        bt7.setOnClickListener(this);
        bt8.setOnClickListener(this);
        bt9.setOnClickListener(this);
        bt10.setOnClickListener(this);
        bt11.setOnClickListener(this);
        bt12.setOnClickListener(this);
        bt13.setOnClickListener(this);
        bt14.setOnClickListener(this);
        bt15.setOnClickListener(this);
        bt16.setOnClickListener(this);
        bt17.setOnClickListener(this);
        bt18.setOnClickListener(this);
        bt19.setOnClickListener(this);
        bt20.setOnClickListener(this);

        builder = new OkHttpClient.Builder();
        okHttpClient = builder.build();

        getID();//学号密码
        getCookie();//获取cookie

    }

    private void getID() {
        SharedPreferences spCount = getActivity().getSharedPreferences("userid", 0);
        xuehao= spCount.getString("username", "");
        mima= spCount.getString("password", "");

    }

    private void getCookie() {
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
                    final Headers headers1 = response1.headers();
                    cookies = headers1.values("Set-Cookie"); //这是另一种获取cookie的方法

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
                    str = str1+";"+str2+";"+str3+";"+str4;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    @Override
    public void onClick(View whichbtn) {
        // TODO Auto-generated method stub

        switch (whichbtn.getId()) {
            case R.id.zong:
                URL = "http://ems1.sit.edu.cn:85/student/selCourse/syllabuslist.jsp";
                postdata0();
                break;
            /*
            1.webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            2.context.deleteDatabase(“WebView.db”);
            3.context.deleteDatabase(“WebViewCache.db”);
            4.webView.clearCache(true);
            6.webView.clearFormData();
            7.getCacheDir().delete();
            */

            case R.id.zhou1:
                A = "1";
                postdata();
                break;

            case R.id.zhou2:
                A= "2";
                postdata();
                break;

            case R.id.zhou3:
                A= "3";
                postdata();
                break;

            case R.id.zhou4:
                A= "4";
                postdata();
                break;

            case R.id.zhou5:
                A= "5";
                postdata();
                break;

            case R.id.zhou6:
                A= "6";
                postdata();
                break;

            case R.id.zhou7:
                A= "7";
                postdata();
                break;

            case R.id.zhou8:
                A= "8";
                postdata();
                break;

            case R.id.zhou9:
                A= "9";
                postdata();
                break;

            case R.id.zhou10:
                A= "10";
                postdata();
                break;

            case R.id.zhou11:
                A= "11";
                postdata();
                break;

            case R.id.zhou12:
                A= "12";
                postdata();
                break;

            case R.id.zhou13:
                A= "13";
                postdata();
                break;

            case R.id.zhou14:
                A= "14";
                postdata();
                break;

            case R.id.zhou15:
                A= "15";
                postdata();
                break;

            case R.id.zhou16:
                A= "16";
                postdata();
                break;

            case R.id.zhou17:
                A= "17";
                postdata();
                break;

            case R.id.zhou18:
                A= "18";
                postdata();
                break;

            case R.id.zhou19:
                A= "19";
                postdata();
                break;

            case R.id.zhou20:
                A= "20";
                postdata();
                break;



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

                    RequestBody requestBody2 = new FormBody.Builder()
                            .add("oldWeekly", A)
                            .add("Weeklys", A)
                            .add("yearTerm","")
                            .add("lyearTerm2","")
                            .add("cType","1")
                            .build();
                    Request request3 = new Request.Builder()
                            .url("http://ems1.sit.edu.cn:85/student/selCourse/syllabuslist2.jsp")
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
                            .post(requestBody2)
                            .build();
                    Response response3 = client.newCall(request3).execute();
                    String responseData3 = response3.body().string();
                    showResponse(responseData3);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    public void postdata0() {
        // 开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final OkHttpClient client = new OkHttpClient().newBuilder()
                            .followRedirects(false)//禁止重定向
                            .followSslRedirects(false)//哈哈哈哈哈哈哈好开心啊
                            .build();

                    Request request3 = new Request.Builder()
                            .url(URL)
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

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    private void showResponse(final String response) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行UI操作，将结果显示到界面上
                wb.loadData(response, "text/html; charset=UTF-8", null);
            }
        });
    }



}
