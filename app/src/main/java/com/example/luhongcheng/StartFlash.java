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
import com.example.luhongcheng.Bmob.LOGO;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;


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
    public String ImageUrl ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.startflash);
        mImageView = (ImageView) findViewById(R.id.kaiji);
        //bmob sdk
        Bmob.initialize(this, "69d2a14bfc1139c1e9af3a9678b0f1ed");





        //查找LOGO表里面id为Z2GB666E的数据
        BmobQuery<LOGO> bmobQuery = new BmobQuery<LOGO>();
        bmobQuery.getObject("BQpAHHHX", new  QueryListener<LOGO>() {
            @Override
            public void done(LOGO object,BmobException e) {
                if(e==null){
                    //获得数据的objectId信息
                    //object.getObjectId();
                    //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
                    //object.getCreatedAt();
                    ImageUrl = object.getimageUrl();
                    System.out.println("开机图片地址"+ImageUrl);
                    postUrl(ImageUrl);
                }else{
                    Log.i("bmob图片","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(TransitionActivity.this, "First log", Toast.LENGTH_SHORT).show();
                intent = new Intent(StartFlash.this, LoginActivity.class);
                StartFlash.this.startActivity(intent);
                StartFlash.this.finish();
            }
        }, 3000);



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
