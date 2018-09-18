package com.example.luhongcheng;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
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
public class TwoFragment extends Fragment {
    private String context;
    public TwoFragment(String context){
        this.context = context;
    }
    WebView wb;
    Spinner spinner;
    private static final String[] m={"第一周","第二周","第三周","第四周","第五周","第六周","第七周","第八周","第九周","第十周","十一周","十二周","十三周"
            ,"十四周","十五周","十六周","十七周","十八周","十九周","二十周","总课表"};
    private ArrayAdapter<String> adapter;
    int WeekNum;
    String xuehao;
    String mima;
    List<String> cookies = null;
    String str;
    String A;

    private MyDatabaseHelper dbHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.b_fragment,container,false);
        return view;
    }


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


        WebSettings webSettings= wb.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setSupportZoom(true);//支持缩放
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//自动适配
        webSettings.setLoadWithOverviewMode(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.red_300));//设置状态栏背景色
        }

        spinner= (Spinner) getActivity().findViewById(R.id.spinner);
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,m);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        spinner.setAdapter(adapter);
        //添加事件Spinner事件监听
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
        //设置默认值
        spinner.setVisibility(View.VISIBLE);


        ImageButton jiazai = (ImageButton) getActivity().findViewById(R.id.jiazai);
        jiazai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setMessage("重新加载将会清空课程信息并重新缓存")//设置对话框的内容
                        //设置对话框的按钮
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                db.execSQL("delete from Book");
                                Toast.makeText(getActivity(),"表信息已清除", Toast.LENGTH_SHORT).show();
                                getID();
                                getCookie();
                            }
                        }).create();
                dialog.show();

            }
        });


        ImageButton qiehuan = (ImageButton) getActivity().findViewById(R.id.qiehuan);
        qiehuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(getActivity(),KB.class);
                startActivity(intent);
            }
        });



        dbHelper = new MyDatabaseHelper(getActivity(), "BookStore.db", null, 2);
        dbHelper.getWritableDatabase();
        firstpanduan();
    }

    private void firstpanduan() {
        String xixi = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Book", new String[]{"id"}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                // 遍历Cursor对象，取出数据并打印
                xixi = cursor.getString(cursor.getColumnIndex("id"));
                //Log.d("MainActivity", "book author is " + xixi);
            } while (cursor.moveToNext());
        }
        cursor.close();
        if (xixi == null){
            getID();
            getCookie();
        }else{

        }
    }

    private void getID() {
        SharedPreferences spCount = getActivity().getSharedPreferences("userid", 0);
        xuehao= spCount.getString("username", "");
        mima= spCount.getString("password", "");
        if(xuehao.length()==0){
            Toast.makeText(getActivity(),"你还没有输入账号", Toast.LENGTH_SHORT).show();
        }

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
                    for (int i = 0; i < bb.length; ++i) {
                        str3 = bb[i=0];
                        str4 = bb[i=1];
                    }
                    str = str1+";"+str2+";"+str3+";"+str4;
                    //System.out.println(str);
                    if (str != null){
                        getKB();
                    }
                    else {
                        Toast.makeText(getActivity(),"获取Cookies失败",Toast.LENGTH_SHORT);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void getKB() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int i;
                    for (i=1; i<=20;i++){
                        A = String.valueOf(i);
                        //Log.d("A到底是多少",A);


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
                        //Log.d(A+"周课表源码",responseData3);

                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        // 开始组装第一条数据
                        values.put("id", A);
                        values.put("content", responseData3);
                        db.insert("Book", null, values); // 插入第一条数据
                        values.clear();


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    private class SpinnerSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Spinner spinner = (Spinner) parent;
            //Toast.makeText(getActivity(), "" + spinner.getItemIdAtPosition(position), Toast.LENGTH_SHORT).show();
            switch ((int) spinner.getItemIdAtPosition(position)){
                case 0:
                    WeekNum =1;
                    showResponse(WeekNum);
                   // Toast.makeText(getActivity(), "第1周", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    WeekNum =2;
                    showResponse(WeekNum);
                    Toast.makeText(getActivity(), "第2周", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    WeekNum =3;
                    showResponse(WeekNum);
                    Toast.makeText(getActivity(), "第3周", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    WeekNum =4;
                    showResponse(WeekNum);
                    Toast.makeText(getActivity(), "第4周", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    WeekNum =5;
                    showResponse(WeekNum);
                    Toast.makeText(getActivity(), "第5周", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    WeekNum =6;
                    showResponse(WeekNum);
                    Toast.makeText(getActivity(), "第6周", Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    WeekNum =7;
                    showResponse(WeekNum);
                    Toast.makeText(getActivity(), "第7周", Toast.LENGTH_SHORT).show();
                    break;
                case 7:
                    WeekNum =8;
                    showResponse(WeekNum);
                    Toast.makeText(getActivity(), "第8周", Toast.LENGTH_SHORT).show();
                    break;
                case 8:
                    WeekNum =9;
                    showResponse(WeekNum);
                    Toast.makeText(getActivity(), "第9周", Toast.LENGTH_SHORT).show();
                    break;
                case 9:
                    WeekNum =10;
                    showResponse(WeekNum);
                    Toast.makeText(getActivity(), "第10周", Toast.LENGTH_SHORT).show();
                    break;
                case 10:
                    WeekNum =11;
                    showResponse(WeekNum);
                    Toast.makeText(getActivity(), "第11周", Toast.LENGTH_SHORT).show();
                    break;
                case 11:
                    WeekNum =12;
                    showResponse(WeekNum);
                    Toast.makeText(getActivity(), "第12周", Toast.LENGTH_SHORT).show();
                    break;
                case 12:
                    WeekNum =13;
                    showResponse(WeekNum);
                    Toast.makeText(getActivity(), "第13周", Toast.LENGTH_SHORT).show();
                    break;
                case 13:
                    WeekNum =14;
                    showResponse(WeekNum);
                    Toast.makeText(getActivity(), "第14周", Toast.LENGTH_SHORT).show();
                    break;
                case 14:
                    WeekNum =15;
                    showResponse(WeekNum);
                    Toast.makeText(getActivity(), "第15周", Toast.LENGTH_SHORT).show();
                    break;
                case 15:
                    WeekNum =16;
                    showResponse(WeekNum);
                    Toast.makeText(getActivity(), "第16周", Toast.LENGTH_SHORT).show();
                    break;
                case 16:
                    WeekNum =17;
                    showResponse(WeekNum);
                    Toast.makeText(getActivity(), "第17周", Toast.LENGTH_SHORT).show();
                    break;
                case 17:
                    WeekNum =18;
                    showResponse(WeekNum);
                    Toast.makeText(getActivity(), "第18周", Toast.LENGTH_SHORT).show();
                    break;
                case 18:
                    WeekNum =19;
                    showResponse(WeekNum);
                    Toast.makeText(getActivity(), "第19周", Toast.LENGTH_SHORT).show();
                    break;
                case 19:
                    WeekNum =20;
                    showResponse(WeekNum);
                    Toast.makeText(getActivity(), "第20周", Toast.LENGTH_SHORT).show();
                    break;
                case 20:
                    WeekNum =0;
                    showResponse(WeekNum);
                    Toast.makeText(getActivity(), "总课表", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }


    private void showResponse(final int weekNum) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String author = null;
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("Book", new String[]{"content"}, "id like ?", new String[]{String.valueOf(weekNum)}, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        // 遍历Cursor对象，取出数据并打印
                        author = cursor.getString(cursor.getColumnIndex("content"));
                       // Log.d("MainActivity", "book author is " + author);
                    } while (cursor.moveToNext());
                }
                cursor.close();

               // String wbxinxi;
               wb.loadData(author, "text/html; charset=UTF-8", null);
            }
        });
    }
}
