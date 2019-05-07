package com.example.luhongcheng.MainFragment_three;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;
import com.example.luhongcheng.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


@SuppressLint("ValidFragment")
public class TwoFragment extends Fragment implements View.OnClickListener {

    public TwoFragment(){
        Context mContext = getActivity();
    }

    public static TwoFragment newInstance(Context context) {
        return new TwoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.b_fragment,container,false);
    }

    WebView wb;
    List<String> cookies = new ArrayList<>();
    String xuehao;
    String mima;
    String A;//第A周
    String str;
    Button zong,bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8,bt9,bt10,bt11,bt12,bt13,bt14,bt15,bt16,bt17,bt18,bt19,bt20;
    SwipeRefreshLayout refreshLayout;

    @SuppressLint("ShowToast")
    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

        SharedPreferences spCount = Objects.requireNonNull(getActivity()).getSharedPreferences("userid", 0);
        xuehao= spCount.getString("username", "");
        mima= spCount.getString("password", "");

        if (xuehao.length() == 10){
            getCookie();
        }else {
            Toast.makeText(getContext(),"你还没有登录哦",Toast.LENGTH_SHORT);
        }

        View statusBar = Objects.requireNonNull(getView()).findViewById(R.id.statusBarView);
        ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
        layoutParams.height = getStatusBarHeight(getActivity());

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        wb = Objects.requireNonNull(getActivity()).findViewById(R.id.wb);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wb.getSettings().setSupportMultipleWindows(true);
        wb.setWebViewClient(new WebViewClient());
        wb.setWebChromeClient(new WebChromeClient());
        wb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        zong = (Button) getActivity().findViewById(R.id.zong);
        bt1 = (Button) getActivity().findViewById(R.id.zhou1);
        bt2 = (Button) getActivity().findViewById(R.id.zhou2);
        bt3 = (Button) getActivity().findViewById(R.id.zhou3);
        bt4 = (Button) getActivity().findViewById(R.id.zhou4);
        bt5 = (Button) getActivity().findViewById(R.id.zhou5);
        bt6 = (Button) getActivity().findViewById(R.id.zhou6);
        bt7 = (Button) getActivity().findViewById(R.id.zhou7);
        bt8 = (Button) getActivity().findViewById(R.id.zhou8);
        bt9 = (Button) getActivity().findViewById(R.id.zhou9);
        bt10 = (Button) getActivity().findViewById(R.id.zhou10);
        bt11 = (Button) getActivity().findViewById(R.id.zhou11);
        bt12 = (Button) getActivity().findViewById(R.id.zhou12);
        bt13 = (Button) getActivity().findViewById(R.id.zhou13);
        bt14 = (Button) getActivity().findViewById(R.id.zhou14);
        bt15 = (Button) getActivity().findViewById(R.id.zhou15);
        bt16 = (Button) getActivity().findViewById(R.id.zhou16);
        bt17 = (Button) getActivity().findViewById(R.id.zhou17);
        bt18 = (Button) getActivity().findViewById(R.id.zhou18);
        bt19 = (Button) getActivity().findViewById(R.id.zhou19);
        bt20= (Button) getActivity().findViewById(R.id.zhou20);


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


        refreshLayout = getActivity().findViewById(R.id.table_refresh);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        postdata();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshLayout.setRefreshing(false);
                            }
                        });

                    }
                }).start();

            }
        });
    }


    /**
     * 利用反射获取状态栏高度
     */
    public int getStatusBarHeight(Activity activity) {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
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

                    String str1 = null;
                    String str2 = null;
                    for (int i = 0; i < cookies.size(); ++i) {
                        str1 = cookies.get(0);
                        str2 = cookies.get(1);
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

                    String str3 = null;
                    String str4 = null;
                    for (int i = 0; i < cookies.size(); ++i) {
                        str3 = cookies.get(0);
                        str4 = cookies.get(1);
                    }
                    str = str1+";"+str2+";"+str3+";"+str4;

                    Log.e("cookies",str);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View whichbtn) {
        // TODO Auto-generated method stub
        switch (whichbtn.getId()) {
            case R.id.zong:
                postdata0();
                setBack();
                zong.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou1:
                A = "1";
                postdata();
                setBack();
                bt1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou2:
                A= "2";
                postdata();
                setBack();
                bt2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou3:
                A= "3";
                postdata();
                setBack();
                bt3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou4:
                A= "4";
                postdata();
                setBack();
                bt4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou5:
                A= "5";
                postdata();
                setBack();
                bt5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou6:
                A= "6";
                postdata();
                setBack();
                bt6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou7:
                A= "7";
                postdata();
                setBack();
                bt7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou8:
                A= "8";
                postdata();
                setBack();
                bt8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou9:
                A= "9";
                postdata();
                setBack();
                bt9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou10:
                A= "10";
                postdata();
                setBack();
                bt10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou11:
                A= "11";
                postdata();
                setBack();
                bt11.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou12:
                A= "12";
                postdata();
                setBack();
                bt12.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou13:
                A= "13";
                postdata();
                setBack();
                bt13.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou14:
                A= "14";
                postdata();
                setBack();
                bt14.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou15:
                A= "15";
                postdata();
                setBack();
                bt15.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou16:
                A= "16";
                postdata();
                setBack();
                bt16.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou17:
                A= "17";
                postdata();
                setBack();
                bt17.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou18:
                A= "18";
                postdata();
                setBack();
                bt18.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou19:
                A= "19";
                postdata();
                setBack();
                bt19.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou20:
                A= "20";
                setBack();
                bt20.setBackgroundColor(Color.parseColor("#FFFFFF"));
                postdata();
                break;



        }
    }


    @SuppressLint("ResourceAsColor")
    private void setBack() {
        zong.setBackgroundColor(Color.parseColor("#ebebeb"));
        bt1.setBackgroundColor(Color.parseColor("#ebebeb"));
        bt2.setBackgroundColor(Color.parseColor("#ebebeb"));
        bt3.setBackgroundColor(Color.parseColor("#ebebeb"));
        bt4.setBackgroundColor(Color.parseColor("#ebebeb"));
        bt5.setBackgroundColor(Color.parseColor("#ebebeb"));
        bt6.setBackgroundColor(Color.parseColor("#ebebeb"));
        bt7.setBackgroundColor(Color.parseColor("#ebebeb"));
        bt8.setBackgroundColor(Color.parseColor("#ebebeb"));
        bt9.setBackgroundColor(Color.parseColor("#ebebeb"));
        bt10.setBackgroundColor(Color.parseColor("#ebebeb"));
        bt11.setBackgroundColor(Color.parseColor("#ebebeb"));
        bt12.setBackgroundColor(Color.parseColor("#ebebeb"));
        bt13.setBackgroundColor(Color.parseColor("#ebebeb"));
        bt14.setBackgroundColor(Color.parseColor("#ebebeb"));
        bt15.setBackgroundColor(Color.parseColor("#ebebeb"));
        bt16.setBackgroundColor(Color.parseColor("#ebebeb"));
        bt17.setBackgroundColor(Color.parseColor("#ebebeb"));
        bt18.setBackgroundColor(Color.parseColor("#ebebeb"));
        bt19.setBackgroundColor(Color.parseColor("#ebebeb"));
        bt20.setBackgroundColor(Color.parseColor("#ebebeb"));
    }


    public void postdata() {
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
                            .url("http://ems.sit.edu.cn:85/student/selCourse/syllabuslist2.jsp")
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
                    showResponse(Objects.requireNonNull(response3.body()).string());



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    public void postdata0() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final OkHttpClient client = new OkHttpClient().newBuilder()
                            .followRedirects(false)//禁止重定向
                            .followSslRedirects(false)//哈哈哈哈哈哈哈好开心啊
                            .build();

                    Request request3 = new Request.Builder()
                            .url("http://ems1.sit.edu.cn:85/student/selCourse/syllabuslist.jsp")
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
                    showResponse(Objects.requireNonNull(response3.body()).string());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    private void showResponse(final String response) {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                wb.loadData(response, "text/html; charset=UTF-8", null);
            }
        });
    }



}
