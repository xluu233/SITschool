package com.example.luhongcheng.SQ;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luhongcheng.Bmob.SS;
import com.example.luhongcheng.Bmob.UserInfo;
import com.example.luhongcheng.R;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class SS_ADD2 extends AppCompatActivity {
    String ID;
    String label;//标签
    ImageView img;
    EditText content;
    FloatingActionButton send;
    public static final int CHOOSE_PHOTO = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ss_add2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.bugray_400));//设置状态栏背景色
        }

        img = (ImageView)findViewById(R.id.img);
        content =(EditText)findViewById(R.id.content);
        send = (FloatingActionButton) findViewById(R.id.send);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent, CHOOSE_PHOTO);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        label = getIntent().getStringExtra("flag");
        System.out.println("flag是什么？："+label);
        TextView tv_label = (TextView)findViewById(R.id.tv_flag);
        if (label != null){
            if (label.equals("A1")){
               tv_label.setText("#今日最佳#");
            }else if (label.equals("A2")){
               tv_label.setText("#一日三餐#");
            }else if (label.equals("A3")){
                tv_label.setText("#表白墙#");
            } else if (label.equals("A4")){
               tv_label.setText("#众话说#");
            } else if (label.equals("A5")){
               tv_label.setText("#工具推荐#");
            } else if (label.equals("A6")){
               tv_label.setText("#学习交流#");
            } else if (label.equals("A7")){
               tv_label.setText("#安利#");
            } else if (label.equals("A8")){
              tv_label.setText("#需求池#");
            } else if (label.equals("A9")){
               tv_label.setText("#考研党#");
            } else if (label.equals("A10")){
                tv_label.setText("#周边推荐#");
            } else if (label.equals("A11")){
                tv_label.setText("#每日一听#");
            }else if (label.equals("A12")){
                tv_label.setText("#晨读打卡#");
            } else if (label.equals("A13")){
               tv_label.setText("#谈天说地#");
            }

        }else {
            label = "A13";//标签
        }


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp=getSharedPreferences("personID",0);
                ID = sp.getString("ID","");

                if (ID.length() == 0){
                    Toast.makeText(getApplicationContext(),"获取personID失败,请转到个人中心查看",Toast.LENGTH_LONG).show();
                }else {
                    final String neirong,biaoqian,personID,icon_path;
                    neirong = content.getText().toString();
                    biaoqian = label;
                    personID = ID;
                    icon_path = imagePath;
                    if (icon_path == null){
                        Toast.makeText(SS_ADD2.this,"请选择图片",Toast.LENGTH_SHORT).show();
                    }else {
                        final BmobFile bmobfile = new BmobFile(new File(icon_path));
                        bmobfile.upload(new UploadFileListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    SS object = new  SS();
                                    object.setContent(neirong);
                                    object.setID(personID);
                                    object.setImg(bmobfile);
                                    object.setLabel(biaoqian);
                                    object.setZan("0");
                                    object.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String s, BmobException e) {
                                            if(e==null){
                                                // Log.i("bmob","更新成功");
                                                Toast.makeText(SS_ADD2.this, "恭喜你，发布成功", Toast.LENGTH_SHORT).show();
                                                SS_ADD2.this.finish();
                                            }else{
                                                Toast.makeText(SS_ADD2.this, "失败", Toast.LENGTH_SHORT).show();
                                                Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                                            }
                                        }
                                    });


                                } else {
                                    Toast.makeText(SS_ADD2.this, "文件上传失败", Toast.LENGTH_SHORT).show();
                                    System.out.println("文件上传失败");
                                }
                            }
                        });

                    }


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
        Uri uri = data.getData();
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
            Toast.makeText(SS_ADD2.this, "未得到图片", Toast.LENGTH_SHORT).show();
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
