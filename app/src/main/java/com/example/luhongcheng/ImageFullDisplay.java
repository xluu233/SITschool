package com.example.luhongcheng;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.luhongcheng.utils.BaseStatusBarActivity;
import com.example.luhongcheng.utils.ImageSaveUtil;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ImageFullDisplay extends BaseStatusBarActivity {
    Button save_image;
    String url;
    Bitmap bitmap;
    ImageView iv;
    LinearLayout layout;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagefull);
        iv = (ImageView)findViewById(R.id.iv);
        save_image = (Button)findViewById(R.id.save_image);
        url = getIntent().getStringExtra("url2");
        layout = findViewById(R.id.close_image);

        postUrl(url);
        save_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap !=null){
                    ImageSaveUtil.saveBitmap2file(bitmap, getApplicationContext());
                }
            }
        });


        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    iv.setBackgroundResource(0);
                    iv.setImageBitmap(bitmap);
                    break;
            }
        }
    };

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

    public Bitmap getHttpBitmap(String url) {
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


    private void close() {
        this.finish();
    }

    @Override
    protected int getStatusBarColor() {
        return getResources().getColor(R.color.black);
    }

}
