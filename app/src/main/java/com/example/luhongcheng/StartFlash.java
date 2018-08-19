package com.example.luhongcheng;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;




import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class StartFlash extends Activity {

    private Intent intent;
    private static Bitmap bitmap;
    private static ImageView mImageView;
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mImageView.setImageBitmap(bitmap);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.startflash);
        mImageView = (ImageView) findViewById(R.id.kaiji);
        Bmob.initialize(this, "69d2a14bfc1139c1e9af3a9678b0f1ed");
        loaddate();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(TransitionActivity.this, "First log", Toast.LENGTH_SHORT).show();
                intent = new Intent(StartFlash.this, LoginActivity.class);
                StartFlash.this.startActivity(intent);
                StartFlash.this.finish();
            }
        }, 3000);



        final String url = "http://bmob-cdn-20204.b0.upaiyun.com/2018/08/18/98d8d89e408e59b380bb65250a22d11f.jpg";
        mImageView = (ImageView) findViewById(R.id.kaiji);
        new Thread() {
            public void run() {
                bitmap = getHttpBitmap(url);
                Message msg = handler.obtainMessage();
                msg.obj = bitmap;
                msg.what = 1;
                handler.sendMessage(msg);
            };
        }.start();


    }

    private void loaddate() {
        BmobQuery<LOGO> bmobQuery = new BmobQuery<LOGO>();
        bmobQuery.getObject("6b6c11c537", new >QueryListener<Person>() {
            @Override
            public void done(Person object,BmobException e) {
                if(e==null){
                    toast("查询成功");
                }else{
                    toast("查询失败：" + e.getMessage());
                }
            }
        });
    }





    public static Bitmap getHttpBitmap(String url) {
        URL myFileURL;
        try {
            myFileURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myFileURL
                    .openConnection();
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
