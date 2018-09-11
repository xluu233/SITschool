package com.example.luhongcheng;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luhongcheng.utils.APKVersionCodeUtils;
import com.example.luhongcheng.utils.getLoginUtils;

import java.util.List;

import cn.bmob.v3.Bmob;
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

        restoreInfo();
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
                            List<String> cookies;
                            String str = null;
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
                                //Log.d("cookie信息", "onResponse-size: " + str);
                                if (str == null){
                                    logincode = 0;
                                }
                                else{
                                    logincode = 1;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            if (logincode == 0){
                                Message message = new Message();
                                message.what = 1;
                                mHandler.sendMessage(message);
                                //Toast.makeText(LoginActivity.this,"登录失败:请检查网络或账号密码",Toast.LENGTH_SHORT).show();
                                //password.setText("");
                            }
                            else if (logincode == 1){
                                memInfo(usernameid,passwordid);
                                com.example.luhongcheng.Bmob.Bmob  p2 = new com.example.luhongcheng.Bmob.Bmob();
                                p2.setName(usernameid);
                                p2.setAddress(passwordid);
                                p2.save();

                                Intent intent = new Intent(LoginActivity.this, MainFragmentActivity.class);
                                //设置startactivity.java为第一启动项，点击login传入mainactivity.java
                                LoginActivity.this.startActivity(intent);
                                LoginActivity.this.finish();
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

    private void test() {
        SharedPreferences sp=getSharedPreferences("userid",0);
        username.setText(sp.getString("username",""));
        password.setText(sp.getString("password",""));

        if (username.length()==10 && password.length()>=4){
            Intent intent3 = new Intent(LoginActivity.this,MainFragmentActivity.class);
            startActivity(intent3);
            LoginActivity.this.finish();
        }
    }


    /*保存密码-嘻嘻*/
    private void memInfo(String usernameid,String passwordid){
        SharedPreferences.Editor editor=getSharedPreferences("userid",0).edit();
        editor.putString("username",usernameid);
        editor.putString("password",passwordid);
        editor.commit();
    }

    private void restoreInfo(){
        SharedPreferences sp=getSharedPreferences("userid",0);
        username.setText(sp.getString("username",""));
        password.setText(sp.getString("password",""));
    }
    /**保存密码就到这里了*/



}