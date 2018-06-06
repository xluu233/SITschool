package com.example.luhongcheng;

/**
 * Created by alex233 on 2018/5/19.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luhongcheng.about.about2;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class GetHtml extends AppCompatActivity implements View.OnClickListener{

    TextView responseText;
    private EditText username = null;
    private EditText password = null;
    private TextView sendpostdata;

    private static HttpClient client = new DefaultHttpClient();
    private OkHttpClient okHttpClient;
    private OkHttpClient.Builder builder;
    List<String> cookies = null;

    String LOGINURL1 = "http://myportal.sit.edu.cn/userPasswordValidate.portal";
    String LOGINURL2 = "http://myportal.sit.edu.cn/index.portal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_startflash);
        TextView sendpostdata = (TextView) findViewById(R.id.main_btn_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

       // responseText = (TextView) findViewById(R.id.response_text);
        sendpostdata.setOnClickListener(this);
        builder = new OkHttpClient.Builder();
        okHttpClient = builder.build();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_request) {
            postdata();
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
                            .add("Login.Token1",username.getText().toString())
                            .add("Login.Token2",password.getText().toString())
                            .build();
                    Request request1 = new Request.Builder()
                            .url(LOGINURL1)
                            .post(requestBody)
                            .build();

                    Response response1 = client.newCall(request1).execute();
                    final Headers headers = response1.headers();
                    //   Log.d("头信息", "header " + headers);
                    HttpUrl loginUrl = request1.url();
                    // List<Cookie> cookies = Cookie.parseAll(loginUrl, headers);  这是另一种获取cookie的方法
                    cookies = headers.values("Set-Cookie");
                    Log.d("cookie信息", "onResponse-size: " + cookies);

                    if (cookies != null) {
                        save(cookies);
                        //保存cookies，不知道可不可行
                    }

                    //List<String>是String数组集合，先转换成String[],在转换成String
                    String[] strs = cookies.toArray(new String[cookies.size()]);
                    String str = null;
                    for (int i = 0; i < strs.length; ++i) {
                        str = strs[i];
                    }

                    Request request = new Request.Builder()
                            .url(LOGINURL2)
                            .header("Accept", "text/html, application/xhtml+xml, image/jxr, */*")
                            // .header("Accept-Encoding", "gzip, deflate")
                            //这样可以注释掉，不注释的话返回数据乱码
                            .header("Accept-Language", "zh-Hans-CN,zh-Hans;q=0.5")
                            .header("Connection", "Keep-Alive")
                            .header("Cookie", str)
                            .header("Host", "myportal.sit.edu.cn")
                            .header("Referer", "http://myportal.sit.edu.cn/userPasswordValidate.portal")
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Jsoup(responseData);

                    
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(okhttp3.Call call, IOException e) {
                            //Intent intent=new Intent(GetHtml.this, MainActivity.class);
                          //  startActivity(intent);
                           // Toast.makeText(GetHtml.this,"登录失败，请重试", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onResponse(okhttp3.Call call, Response response) throws IOException {
                           //  Log.d("源代码", "onResponse: " + response.body().string().toString());
                        //    Intent intent=new Intent(GetHtml.this, MainActivity.class);
                         //   startActivity(intent);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void save(List<String> cookie) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try{
            out = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(String.valueOf(cookie));
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if (writer != null){
                    writer.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    private void Jsoup(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行UI操作，将结果显示到界面上
              //  responseText.setText(response);
            }
        });
    }

}
