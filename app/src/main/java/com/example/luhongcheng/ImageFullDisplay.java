package com.example.luhongcheng;

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
import android.widget.TextView;


import com.example.luhongcheng.utils.ImageSaveUtil;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ImageFullDisplay extends AppCompatActivity {
    TextView nums;
    Button save_image;
    String url;
    Bitmap bitmap;
    ImageView iv;
    int n,num;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagefull);
        iv = (ImageView)findViewById(R.id.iv);
        nums= (TextView)findViewById(R.id.nums);
        save_image = (Button)findViewById(R.id.save_image);


        n = 1;
        num = 1;
        n = getIntent().getIntExtra("n",0)+1;
        num = getIntent().getIntExtra("nums",0);
        url = getIntent().getStringExtra("url2");


        postUrl(url);
        save_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap !=null){
                    ImageSaveUtil.saveBitmap2file(bitmap, getApplicationContext());
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black));//设置状态栏背景色
        }

        String text = n+"/"+num;
        nums.setText(text);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });

    }

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


}
