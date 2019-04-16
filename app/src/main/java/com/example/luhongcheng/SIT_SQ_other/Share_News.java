package com.example.luhongcheng.SIT_SQ_other;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luhongcheng.R;
import com.example.luhongcheng.WebDisplay;
import com.example.luhongcheng.bean.HotNews;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

import static org.litepal.LitePalApplication.getContext;

public class Share_News extends AppCompatActivity {

    ImageView img;
    EditText title,url;
    TextView send;
    public static final int CHOOSE_PHOTO = 1;
    Toolbar toolbar;
    Uri uri;//图片地址
    LinearLayout status,progress;

    int send_time=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_article);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        img = (ImageView)findViewById(R.id.img);
        title =(EditText)findViewById(R.id.title);
        url = (EditText)findViewById(R.id.url);
        send = findViewById(R.id.send);
        status = findViewById(R.id.share_news_status_layout);
        progress = findViewById(R.id.status_progress);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));//设置状态栏背景色
        }

        onClick();


        Intent intent = getIntent();
        if(intent == null)
            return;
        Bundle extras = intent.getExtras();

        if(extras == null)
            return;

        switch (intent.getType()) {
            case "text/plain"://分享的内容类型，如果png图片：image/png 
                title.setText((CharSequence) extras.get(Intent.EXTRA_TITLE));
                String urls = (String) extras.get(Intent.EXTRA_TEXT);
                Log.d("murl",urls);
                final Matcher m = Pattern.compile("(?i)http://[^\u4e00-\u9fa5]+").matcher(urls);
                while(m.find()){
                    url.setText(m.group());
                    Log.d("murl",m.group());
                }

                final Matcher ms = Pattern.compile("(?i)https://[^\u4e00-\u9fa5]+").matcher(urls);
                while(ms.find()){
                    url.setText(ms.group());
                    Log.d("murl--",ms.group());
                }

                break;
            case "image/png":
                Toast.makeText(getApplicationContext(),"分享的是图片",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void onClick() {

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent, CHOOSE_PHOTO);
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (send_time>=1){
                    Snackbar.make(v,"请15s后重试",Toast.LENGTH_SHORT).show();
                }else {
                    send_time++;

                    final String news_title,news_url,icon_path;
                    news_title = title.getText().toString();
                    news_url = url.getText().toString();
                    icon_path = imagePath;
                    if (icon_path == null){
                        Toast.makeText(Share_News.this,"请选择图片",Toast.LENGTH_SHORT).show();
                    }else if (news_title.length()==0 || news_url.length() ==0){
                        Toast.makeText(Share_News.this,"请输入完整信息",Toast.LENGTH_SHORT).show();
                    }else if (!news_url.contains("http")){
                        Toast.makeText(Share_News.this,"请输入正确链接地址",Toast.LENGTH_SHORT).show();
                    }else {
                        status.setVisibility(View.INVISIBLE);
                        progress.setVisibility(View.VISIBLE);

                        final BmobFile bmobfile = new BmobFile(new File(icon_path));
                        bmobfile.upload(new UploadFileListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    com.example.luhongcheng.Bmob_bean.Share_News object = new com.example.luhongcheng.Bmob_bean.Share_News();
                                    object.setTitle(news_title);
                                    object.setUrl(news_url);
                                    object.setImage(bmobfile);

                                    object.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String s, BmobException e) {
                                            if(e==null){
                                                status.setVisibility(View.VISIBLE);
                                                progress.setVisibility(View.INVISIBLE);
                                                Toast.makeText(getApplicationContext(), "发布成功,积分+10", Toast.LENGTH_SHORT).show();
                                                Share_News.this.finish();
                                            }else{
                                                status.setVisibility(View.VISIBLE);
                                                progress.setVisibility(View.INVISIBLE);
                                                Toast.makeText(getApplicationContext(), "失败", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });


                                } else {
                                    status.setVisibility(View.VISIBLE);
                                    progress.setVisibility(View.INVISIBLE);
                                    Toast.makeText(Share_News.this, "文件上传失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }

                }

                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        send_time = 0;
                    }
                }, 15000);




            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK){
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT>=19){
                        //4.4以上使用这个方法处理图片
                        handleIMageOnKitKat(data);
                    }else{
                        handleIMageBeforKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    String imagePath = null;
    @SuppressLint("NewApi")
    private void handleIMageOnKitKat(Intent data) {
        uri = data.getData();
        if (DocumentsContract.isDocumentUri(this , uri)){
            //如果是document类型的URI，则使用document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID +"=" +id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI , selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath = getImagePath(contentUri ,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            //如果不是document类型的URI，则使用普通方式处理
            imagePath = getImagePath(uri , null);
        }
        displayImage(imagePath);
    }

    private void displayImage(String imagePath) {
        if (imagePath!=null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            img.setImageBitmap(bitmap);
        }else {
            Toast.makeText(Share_News.this, "未得到图片", Toast.LENGTH_SHORT).show();
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri , null , selection , null , null);
        if (cursor!=null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void handleIMageBeforKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri , null);
        displayImage(imagePath);
    }


}
