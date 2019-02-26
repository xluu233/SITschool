package com.example.luhongcheng;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.luhongcheng.Bmob_bean.LOGO;
import com.example.luhongcheng.Login.LoginActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class StartFlash extends Activity {


    private Intent intent;
    private Thread flash;
    private static Bitmap bitmap;
    private static ImageView mImageView;
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mImageView.setBackgroundResource(0);
                    mImageView.setImageBitmap(bitmap);
                    break;
            }
        }
    };

    private Boolean hasCode;
    
    public String ImageUrl ;
    Button skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.startflash);
        mImageView = (ImageView) findViewById(R.id.kaiji);
        skip = (Button)findViewById(R.id.skip);
        Bmob.initialize(this, "69d2a14bfc1139c1e9af3a9678b0f1ed");

        test();//检查密码

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

        BmobQuery<LOGO> bmobQuery = new BmobQuery<LOGO>();
        bmobQuery.findObjects(new FindListener<LOGO>(){
            @Override
            public void done(List<LOGO> list, BmobException e) {
                List<LOGO> lists = new ArrayList<>();
                if (list != null) {
                    final String[] tip  =  new String[list.size()];
                    final String[] url = new String[list.size()];
                    for(int i = 0;i<list.size();i++){
                        tip[i] = list.get(i).getimageUrl();
                        url[i] = list.get(i).getUrl();

                    }
                   // click_url = url[list.size() - 1];
                    ImageUrl = tip[list.size() - 1];
                    postUrl(ImageUrl);
                }else{
                    //Log.i("bmob图片","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
        /*
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(TransitionActivity.this, "First log", Toast.LENGTH_SHORT).show();
                intent = new Intent(StartFlash.this, LoginActivity.class);
                StartFlash.this.startActivity(intent);
                StartFlash.this.finish();
            }
        }, 10000);
        */

        StartNewThread();


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasCode){
                    Intent intent2 = new Intent(StartFlash.this, MainFragmentActivity.class);
                    startActivity(intent2);
                    finish();
                }else {
                    Intent intent2 = new Intent(StartFlash.this, LoginActivity.class);
                    startActivity(intent2);
                    finish();
                }

            }
        });

    }

    private void test() {
        SharedPreferences sp= getSharedPreferences("userid",0);
        String username = sp.getString("username","");
        String password = sp.getString("password","");


        if (username.length()>=10 && password.length()>=4 ){
            hasCode = true;
        }else {
            hasCode = false;
        }
    }

    private void StartNewThread() {
        flash = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (hasCode){
                    Intent intent2 = new Intent(StartFlash.this, MainFragmentActivity.class);
                    startActivity(intent2);
                    finish();
                }else {
                    Intent intent2 = new Intent(StartFlash.this, LoginActivity.class);
                    startActivity(intent2);
                    finish();
                }

                StartFlash.this.finish();
            }
        });
        flash.start();
    }



    public void  postUrl(final String imageUrl){
        new Thread() {
            public void run() {
                bitmap = getHttpBitmap(imageUrl);
                Message msg = handler.obtainMessage();
                msg.obj = bitmap;
                msg.what = 1;
                handler.sendMessage(msg);
            };
        }.start();

    }

    public static Bitmap getHttpBitmap(String url) {
        URL myFileURL;
        try {
            myFileURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
            conn.setConnectTimeout(6000);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


}
