package com.example.luhongcheng.OAitem;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luhongcheng.R;
import com.example.luhongcheng.secondclass.SecondClass;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//绩点
public class more_item0  extends Activity {
    Spinner start_spinner,stop_spinner;
    TextView result,result2;
    Button get;
    String A,B;

    private OkHttpClient okHttpClient;
    private OkHttpClient.Builder builder;
    List<String> cookies = null;
    String xuehao;
    String mima;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_item0);
        start_spinner = (Spinner)findViewById(R.id.spinner1);
        stop_spinner = (Spinner)findViewById(R.id.spinner2);
        get = (Button)findViewById(R.id.get_jidian);
        result = (TextView)findViewById(R.id.result);
        result2 =(TextView)findViewById(R.id.result2);

        /*
        * 2015%C7%EF  2015-2016学年第1学期
        * 2016%B4%BA
        *
        * 2016%C7%EF  2016-2017学年第1学期
        * 2017%B4%BA  2016-2017学年第2学期
        *
        * 2017%C7%EF  2017-2018学年第1学期
        * 2018%B4%BA  2017-2018学年第2学期
        *
        * 2018%C7%EF  2018-2019学年第1学期
        * 2019%B4%BA  2018-2019学年第2学期
        *
        * 2019%C7%EF  2019-2020学年第1学期
        * 2020%B4%BA  2019-2020学年第2学期
        *
        * 2020%C7%EF  2020-2021学年第1学期
        * 2021%B4%BA  2020-2021学年第2学期
        *
        * 2021%C7%EF  2021-2022学年第1学期
        * 2022%B4%BA  2021-2022学年第2学期
        * */
        final String xiix[] =new String[]{"2015%C7%EF","2016%B4%BA","2016%C7%EF","2017%B4%BA","2017%C7%EF","2018%B4%BA",
                "2018%C7%EF","2019%B4%BA","2019%C7%EF","2020%B4%BA","2020%C7%EF","2021%B4%BA"};

        final String xiix2[] =new String[]{"2015%C7%EF","2016%B4%BA","2016%C7%EF","2017%B4%BA","2017%C7%EF","2018%B4%BA",
                "2018%C7%EF","2019%B4%BA","2019%C7%EF","2020%B4%BA","2020%C7%EF","2021%B4%BA"};

        final String start[]  = new String[]{"2015-2016学年第1学期","2015-2016学年第2学期","2016-2017学年第1学期","2016-2017学年第2学期","2017-2018学年第1学期","2017-2018学年第2学期",
                "2018-2019学年第1学期","2018-2019学年第2学期","2019-2020学年第1学期","2019-2020学年第2学期","2020-2021学年第1学期","2020-2021学年第2学期"};

        final String stop[]  = new String[]{"2015-2016学年第1学期","2015-2016学年第2学期","2016-2017学年第1学期","2016-2017学年第2学期","2017-2018学年第1学期","2017-2018学年第2学期",
                "2018-2019学年第1学期","2018-2019学年第2学期","2019-2020学年第1学期","2019-2020学年第2学期","2020-2021学年第1学期","2020-2021学年第2学期"};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,start);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,stop);

        start_spinner.setAdapter(arrayAdapter);
        stop_spinner.setAdapter(arrayAdapter2);

        start_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner)parent;
                A = xiix[position];
                //Toast.makeText(getApplicationContext(), "start"+spinner.getItemAtPosition(position),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        stop_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner2 = (Spinner)parent;
                B = xiix2[position];
               // Toast.makeText(getApplicationContext(), "stop"+spinner2.getItemAtPosition(position),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder = new OkHttpClient.Builder();
        okHttpClient = builder.build();
        getID();

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("A:"+A,"B:"+B);
                result.setText(null);
                result2.setText(null);
                postdata();
            }
        });

    }

    private void getID() {
        SharedPreferences spCount = getSharedPreferences("userid", 0);
        xuehao= spCount.getString("username", "");
        mima= spCount.getString("password", "");

        if(xuehao.length()==0){
            Toast.makeText(more_item0.this,"你还没有输入账号", Toast.LENGTH_SHORT).show();
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
                            .url("http://ems.sit.edu.cn/")
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
                    cookies = headers1.values("Set-Cookie");

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
                    String responseData2 = response2.body().string();
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

                    String str = str1+";"+str2+";"+str3+";"+str4;

                    RequestBody requestBody2 = new FormBody.Builder()
                            .add("op","list")
                            .add("srTerm",A)
                            .add("srTerm2",B)
                            .build();
                    Request request3 = new Request.Builder()
                            .url("http://ems.sit.edu.cn:85/student/graduate/scorepoint.jsp")
                            .post(requestBody2)
                            .header("Accept", "image/gif, image/jpeg, image/pjpeg, application/x-ms-application, application/xaml+xml, application/x-ms-xbap, */*")
                            .header("Accept-Language", "zh-CN")
                            .header("Cache-Control","no-cache")
                            .header("Connection", "Keep-Alive")
                            .header("Content-Length","44")
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .header("Cookie",str)
                            .header("Host", "ems.sit.edu.cn:85")
                            .header("Referer", "http://ems.sit.edu.cn:85/")
                            .header("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 10.0; WOW64; Trident/7.0; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.30729; .NET CLR 3.5.30729)")
                            .build();
                    Response response3 = client.newCall(request3).execute();
                    String responseData3 = response3.body().string();
                    showResponse(responseData3);

                    okHttpClient.newCall(request3).enqueue(new Callback() {
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


    private void showResponse(final String responseData3) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Document doc = Jsoup.parse(responseData3);
                Elements link =  doc.getElementsByClass("list");
                System.out.println("list的数量："+link.size());

                if (link.size() != 0){
                    String A1= null;

                    A1= link.get(0).select("tbody").select("tr").get(0).text();
                    A1 = A1.replaceAll("，学期","  \n学期");

                    String A2 = null;
                    A2= link.get(0).select("tbody").select("tr").get(2).select("td").get(2).text()+":"+
                            link.get(0).select("tbody").select("tr").get(2).select("td").get(3).text();


                    result2.setText(A1);
                    result.setText(A2);
                }else if (link.size() == 0){
                    result.setText("查询失败");
                }



            }
        });

    }


}
