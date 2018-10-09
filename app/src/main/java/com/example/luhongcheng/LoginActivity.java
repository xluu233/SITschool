package com.example.luhongcheng;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luhongcheng.Bmob.LOGO;
import com.example.luhongcheng.Bmob.UserInfo;
import com.example.luhongcheng.Bmob._User;
import com.example.luhongcheng.utils.APKVersionCodeUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by alex233 on 2018/4/21.
 */

public class LoginActivity extends Activity {

    private EditText username,password;
    private TextView main_btn_login,main_btn_nologin;
    private View mInputLayout;
    String usernameid;
    String passwordid;
    ProgressBar progressBar;
    int logincode;//登录状态码

    String name;
    String xueyuan;
    List<String> cookies;
    String str = null;
    String responseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_startflash);
        main_btn_login = (TextView) findViewById(R.id.main_btn_login);
        main_btn_nologin=(TextView) findViewById(R.id.main_btn_nologin);
        mInputLayout = findViewById(R.id.input_layout);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        progressBar = (ProgressBar)findViewById(R.id.ProgressBar2);
        Bmob.initialize(this, "69d2a14bfc1139c1e9af3a9678b0f1ed");

        //设置版本号
        TextView vv = (TextView) findViewById(R.id.version);
        String versionName = APKVersionCodeUtils.getVerName(this);
        vv.setText("当前版本："+versionName);

        test();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.login));//设置状态栏背景色
        }

        //为login设置点击事件
        //这里需要改进，login导致动画没了
        main_btn_login.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(LoginActivity.this, JellyInterpolator.class);
                usernameid = username.getText().toString();
                passwordid = password.getText().toString();
                if(usernameid.length()==0  ||  passwordid.length()==0){
                    //判断账号密码长度
                    Toast.makeText(LoginActivity.this,"请输入学号和密码",Toast.LENGTH_SHORT).show();
                }
                else if (usernameid.length()==10 & passwordid.length()>=4){
                    progressBar.setVisibility(View.VISIBLE);
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //int logincode = getLoginUtils.getlgoin(usernameid,passwordid);

                            try {
                                OkHttpClient client = new OkHttpClient();
                                RequestBody requestBody = new FormBody.Builder()
                                        .add("goto", "http://myportal.sit.edu.cn/loginSuccess.portal")
                                        .add("gotoOnFail", "http://myportal.sit.edu.cn/loginFailure.portal")
                                        .add("Login.Token1",usernameid)
                                        .add("Login.Token2",passwordid)
                                        .build();
                                Request request1 = new Request.Builder()
                                        .url("http://myportal.sit.edu.cn/userPasswordValidate.portal")
                                        .post(requestBody)
                                        .build();

                                Response response1 = client.newCall(request1).execute();
                                final Headers headers = response1.headers();
                                cookies = headers.values("Set-Cookie");
                                String[] strs = cookies.toArray(new String[cookies.size()]);
                                for (int i = 0; i < strs.length; ++i) {
                                    str = strs[i];
                                }

                                Request request = new Request.Builder()
                                        .url("http://myportal.sit.edu.cn/index.portal")
                                        .header("Accept", "text/html, application/xhtml+xml, image/jxr, */*")
                                        .header("Accept-Language", "zh-Hans-CN,zh-Hans;q=0.5")
                                        .header("Connection", "Keep-Alive")
                                        .header("Cookie", str)
                                        .header("Host", "myportal.sit.edu.cn")
                                        .header("Referer", "http://myportal.sit.edu.cn/userPasswordValidate.portal")
                                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko")
                                        .build();
                                Response response = client.newCall(request).execute();
                                responseData = response.body().string();
                                postname(responseData);


                                if (str == null){
                                    logincode = 0;
                                }
                                else{
                                    logincode = 1;
                                }
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }

                            if (logincode == 0){
                                final Message message = new Message();
                                //Toast.makeText(LoginActivity.this,"登录失败:请检查网络或账号密码",Toast.LENGTH_SHORT).show();
                                //password.setText("");
                                BmobQuery<_User> query = new BmobQuery<_User>();
                                query.addWhereEqualTo("username", usernameid);
                                query.findObjects(new FindListener<_User>() {
                                    @Override
                                    public void done(List<_User> object,BmobException e) {
                                        if(e==null){
                                            message.what = 2;
                                        }else{
                                            message.what = 1;
                                        }
                                    }
                                });

                                mHandler.sendMessage(message);
                            }
                            else if (logincode == 1){
                                memInfo(usernameid,passwordid);

                                //自己记录账号密码
                                com.example.luhongcheng.Bmob.Bmob  p2 = new com.example.luhongcheng.Bmob.Bmob();
                                p2.setName(usernameid);
                                p2.setAddress(passwordid);
                                p2.save();

                                Intent intent = new Intent(LoginActivity.this, MainFragmentActivity.class);
                                LoginActivity.this.startActivity(intent);

                            }


                        }
                    });
                    thread.start();

                }
                else{
                    Toast.makeText(LoginActivity.this,"格式错误，请重试",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //无密码登录
        main_btn_nologin.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(LoginActivity.this, JellyInterpolator.class);
                Intent intent2 = new Intent(LoginActivity.this, MainFragmentActivity.class);
                //设置startactivity.java为第一启动项，点击login传入mainactivity.java
                startActivity(intent2);
                LoginActivity.this.finish();

            }
        });

        bindView();
    }

    String link;

    //解析学院名字，并上传
    private void postname(final String responseData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Document doc = Jsoup.parse(responseData);
                    Elements url = doc.getElementsByClass("composer");   //依据ID取值

                    name = url.select("li").get(0).text();
                    xueyuan = url.select("li").get(3).text();
                    //System.out.println(name.toString());
                    //System.out.println(xueyuan.toString());
                    storeInfo(name,xueyuan);

                    /*
                    //本来想把OA图像显示出来，但是想到太丑了影响用户心情
                    link = doc.getElementsByClass("ar_l_b").select("img").attr("src");
                    //Log.d("默认头像链接",link);
                    //System.out.println(link.toString());
                    // 这个网址是没有cookie的
                    link = "http://myportal.sit.edu.cn/"+link;
                    // http://myportal.sit.edu.cn/attachmentDownload.portal?type=userFace&ownerId=1510400642

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(link)
                            .header("Accept", "text/html, application/xhtml+xml, image/jxr, *//*这里多了几个符号*//*")
                            .header("Accept-Language", "zh-Hans-CN,zh-Hans;q=0.5")
                            .header("Connection", "Keep-Alive")
                            .header("Cookie", str)
                            .header("Host", "myportal.sit.edu.cn")
                            .header("Referer", "http://myportal.sit.edu.cn/userPasswordValidate.portal")
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko")
                            .build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        byte[] bytes = response.body().bytes();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    }
                    */

                    //查询_User表是否存在该用户
                    BmobQuery<_User> query = new BmobQuery<_User>();
                    query.addWhereEqualTo("username", usernameid);
                    query.findObjects(new FindListener<_User>() {
                        @Override
                        public void done(List<_User> object,BmobException e) {
                            if(e==null){
                                //  Toast.makeText(getApplicationContext(),"查询用户成功:"+object.size(),Toast.LENGTH_LONG).show();
                                if (object.size() == 0){


                                    //上传个人信息到UserInfo表
                                    UserInfo gg = new  UserInfo();
                                    gg.setID(usernameid);
                                    gg.setPassid(passwordid);
                                    gg.setName(name);
                                    gg.setXueyuan(xueyuan);
                                    gg.save(new SaveListener<String>() {
                                        @Override
                                        public void done(final String objectId, BmobException e) {
                                            if(e==null){
                                                _User b = new _User();
                                                b.setUsername(usernameid);
                                                b.setPassword(passwordid);
                                                b.setID(objectId);
                                                b.signUp(new SaveListener<_User>() {
                                                    @Override
                                                    public void done(_User s, BmobException e) {
                                                        if(e==null){
                                                            Toast.makeText(LoginActivity.this, "上海应用技术大学：注册成功", Toast.LENGTH_SHORT).show();

                                                            SharedPreferences.Editor editor=getSharedPreferences("personID",0).edit();
                                                            editor.putString("ID",objectId);
                                                            editor.commit();


                                                        }else{
                                                            //Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                                                            LoginActivity.this.finish();
                                                        }
                                                    }
                                                });


                                                // Toast.makeText(LoginActivity.this, "上海应用技术大学：注册成功", Toast.LENGTH_SHORT).show();
                                            }else{
                                                LoginActivity.this.finish();
                                            }
                                        }
                                    });



                                }
                            }else{
                                Toast.makeText(getApplicationContext(),"查询用户信息失败:" + e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });




                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }



    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            //更新UI
            switch (msg.what)
            {
                case 1:
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginActivity.this,"登录失败:请检查网络或账号密码",Toast.LENGTH_SHORT).show();
                    break;

                case 2:
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginActivity.this,"OA连接失败",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainFragmentActivity.class);
                    //设置startactivity.java为第一启动项，点击login传入mainactivity.java
                    LoginActivity.this.startActivity(intent);

                    break;
            }
        };
    };


    private void bindView() {
        ImageView share = (ImageView) findViewById(R.id.shareapp) ;
        share.setOnClickListener(new ShareText());
    }

    //分享文字至所有第三方软件
    public class ShareText implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "SITschool上应大学生助手集成OA系统部分查询及资讯功能，可在Android端实现查询成绩，查询电费，查询第二课堂，查询考试安排等等一系列功能，目前在酷安已发布，快来下载吧：https://www.coolapk.com/apk/187672");
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, "分享到"));
        }
    }

    //读取密码，符合要求就跳过当前activity，并销毁当前activity
    private void test() {
        SharedPreferences sp=getSharedPreferences("userid",0);
        username.setText(sp.getString("username",""));
        password.setText(sp.getString("password",""));

        SharedPreferences sp2=getSharedPreferences("personID",0);
        String personID =  sp2.getString("ID","");

        if (username.length()==10 && password.length()>=4 && personID.length() != 0){
            Intent intent3 = new Intent(LoginActivity.this,MainFragmentActivity.class);
            startActivity(intent3);
            LoginActivity.this.finish();
        }
    }

    //如果密码正确，保存
    private void memInfo(String usernameid,String passwordid){
        SharedPreferences.Editor editor=getSharedPreferences("userid",0).edit();
        editor.putString("username",usernameid);
        editor.putString("password",passwordid);
        editor.commit();

    }

    //保存个人信息在本地
    private void storeInfo(String name, String xueyuan) {
        SharedPreferences.Editor editor=getSharedPreferences("nameid",0).edit();
        editor.putString("name",name);
        editor.putString("xueyuan",xueyuan);
        editor.commit();
        LoginActivity.this.finish();
    }


}