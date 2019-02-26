package com.example.luhongcheng.SQ;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.luhongcheng.Bmob_bean.news;
import com.example.luhongcheng.R;
import java.io.File;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class Article_Add extends AppCompatActivity {

    ImageView img;
    EditText title,url;
    FloatingActionButton send;
    public static final int CHOOSE_PHOTO = 1;
    Toolbar toolbar;
    int select = 0;//发送-点击次数；
    Uri uri;//图片地址
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_article);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.bugray_400));//设置状态栏背景色
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        img = (ImageView)findViewById(R.id.img);
        title =(EditText)findViewById(R.id.title);
        url = (EditText)findViewById(R.id.url);
        send = (FloatingActionButton) findViewById(R.id.send);

        onClick();

        /*
        *
        *         // 获取启动这个activity的Intent
        Intent intent = getIntent();
        Uri data = intent.getData();

        url.setText((CharSequence) data);

        // 根据intent的类型决定做什么
        if (intent.getType().indexOf("image/") != -1) {
           uri = data;
        } else if (intent.getType().equals("text/plain")) {
           url.setText((CharSequence) data);
        }
        * */


        Intent intent = getIntent();
        if(intent == null)
            return;
        Bundle extras = intent.getExtras();

        if(extras == null)
            return;

        switch (intent.getType()) {
            case "text/plain"://分享的内容类型，如果png图片：image/png 
                title.setText((CharSequence) extras.get(Intent.EXTRA_TITLE));
                url.setText((CharSequence) extras.get(Intent.EXTRA_TEXT));
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

                if (select == 0){
                    final String news_title,news_url,icon_path;
                    news_title = title.getText().toString();
                    news_url = url.getText().toString();
                    icon_path = imagePath;
                    if (icon_path == null){
                        Toast.makeText(Article_Add.this,"请选择图片",Toast.LENGTH_SHORT).show();
                    }else if (news_title == null  || news_url ==null){
                        Toast.makeText(Article_Add.this,"请输入完整信息",Toast.LENGTH_SHORT).show();
                    }else if (!news_url.contains("http")){
                        Toast.makeText(Article_Add.this,"请输入正确链接地址",Toast.LENGTH_SHORT).show();
                    }else {
                        final BmobFile bmobfile = new BmobFile(new File(icon_path));
                        bmobfile.upload(new UploadFileListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    news object = new  news();
                                    object.setTitle(news_title);
                                    object.setUrl(news_url);
                                    object.setImage(bmobfile);

                                    object.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String s, BmobException e) {
                                            if(e==null){
                                                Toast.makeText(Article_Add.this, "恭喜你，发布成功", Toast.LENGTH_SHORT).show();
                                                Article_Add.this.finish();
                                            }else{
                                                Toast.makeText(Article_Add.this, "失败", Toast.LENGTH_SHORT).show();
                                                // Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                                            }
                                        }
                                    });


                                } else {
                                    Toast.makeText(Article_Add.this, "文件上传失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                    select ++;
                }else {
                    Toast.makeText(getApplicationContext(),"正在发布，请不要重复点击",Toast.LENGTH_SHORT).show();
                }



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
            Toast.makeText(Article_Add.this, "未得到图片", Toast.LENGTH_SHORT).show();
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
