package com.example.luhongcheng.MainFragment_three;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.example.luhongcheng.DataBase.Class_Schedule;
import com.example.luhongcheng.R;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

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
    //private static HttpClient client = new DefaultHttpClient();
    private OkHttpClient okHttpClient;
    private OkHttpClient.Builder builder;
    List<String> cookies = null;

  //  String URL;
    String xuehao;
    String mima;
    String A;//第A周
    int B;
    private  List<String> table = new ArrayList<String>();

    String str;
    Button zong,bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8,bt9,bt10,bt11,bt12,bt13,bt14,bt15,bt16,bt17,bt18,bt19,bt20;


   // ProgressDialog waitingDialog;

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

        builder = new OkHttpClient.Builder();
        okHttpClient = builder.build();

        SharedPreferences spCount = getActivity().getSharedPreferences("userid", 0);
        xuehao= spCount.getString("username", "");
        mima= spCount.getString("password", "");

        if (xuehao.length() == 10){
            getCookie();
        }else {
            Toast.makeText(getContext(),"你还没有登录哦",Toast.LENGTH_SHORT);
        }

        View statusBar = getView().findViewById(R.id.statusBarView);
        ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
        layoutParams.height = getStatusBarHeight(getActivity());

    }


    /**
     * 利用反射获取状态栏高度
     * @return
     * @param activity
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
                //getDataShowWeb0();
                postdata0();
                setBack();
                zong.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou1:
                A = "1";
                //getDataShowWeb();
                postdata();
                setBack();
                bt1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou2:
                A= "2";
              //  getDataShowWeb();
                postdata();
                setBack();
                bt2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou3:
                A= "3";
             //   getDataShowWeb();
                postdata();
                setBack();
                bt3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou4:
                A= "4";
            //    getDataShowWeb();
                postdata();
                setBack();
                bt4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou5:
                A= "5";
            //    getDataShowWeb();
                postdata();
                setBack();
                bt5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou6:
                A= "6";
               // getDataShowWeb();
                postdata();
                setBack();
                bt6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou7:
                A= "7";
              //  getDataShowWeb();
                postdata();
                setBack();
                bt7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou8:
                A= "8";
               // getDataShowWeb();
                postdata();
                setBack();
                bt8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou9:
                A= "9";
               // getDataShowWeb();
                postdata();
                setBack();
                bt9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou10:
                A= "10";
               // getDataShowWeb();
                postdata();
                setBack();
                bt10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou11:
                A= "11";
              //  getDataShowWeb();
                postdata();
                setBack();
                bt11.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou12:
                A= "12";
               // getDataShowWeb();
                postdata();
                setBack();
                bt12.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou13:
                A= "13";
              //  getDataShowWeb();
                postdata();
                setBack();
                bt13.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou14:
                A= "14";
               // getDataShowWeb();
                postdata();
                setBack();
                bt14.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou15:
                A= "15";
               // getDataShowWeb();
                postdata();
                setBack();
                bt15.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou16:
                A= "16";
                //getDataShowWeb();
                postdata();
                setBack();
                bt16.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou17:
                A= "17";
               // getDataShowWeb();
                postdata();
                setBack();
                bt17.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou18:
                A= "18";
                postdata();
                //getDataShowWeb();
                setBack();
                bt18.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou19:
                A= "19";
                postdata();
               // getDataShowWeb();
                setBack();
                bt19.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.zhou20:
                A= "20";
                //getDataShowWeb();
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

    private void getDataShowWeb0() {
        List<Class_Schedule> list = LitePal.where("name = ?","0").find(Class_Schedule.class);
        final String data = list.get(0).getContent();
        if (data != null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    wb.loadData(data, "text/html; charset=UTF-8", null);
                }
            });
        }else {
            postdata0();
        }

    }

    private void getDataShowWeb() {
        List<Class_Schedule> list = LitePal.where("name = ?",A).find(Class_Schedule.class);
        final String data = list.get(0).getContent();
        if (data != null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    wb.loadData(data, "text/html; charset=UTF-8", null);
                }
            });
        }else {
            postdata();
        }

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
                    String responseData3 = response3.body().string();
                    showResponse(responseData3);

                    Class_Schedule schedule = new Class_Schedule();
                    schedule.setName(Integer.parseInt(A));
                    schedule.setContent(responseData3);
                    schedule.save();


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
                    String responseData3 = response3.body().string();
                    showResponse(responseData3);

                    Class_Schedule schedule2 = new Class_Schedule();
                    schedule2.setName(0);
                    schedule2.setContent(responseData3);
                    schedule2.save();



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
                wb.loadData(response, "text/html; charset=UTF-8", null);
            }
        });
    }



}
