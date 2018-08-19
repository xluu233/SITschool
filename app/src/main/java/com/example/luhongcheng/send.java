package com.example.luhongcheng;

import android.annotation.TargetApi;
import android.app.Activity;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.luhongcheng.Bmob.SWZL;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class send extends Activity {

    EditText title,content,time,adress;

    Button send;
    public static final int CHOOSE_PHOTO = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send);
        Bmob.initialize(this, "69d2a14bfc1139c1e9af3a9678b0f1ed");
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
        time = (EditText) findViewById(R.id.time);
        adress = (EditText) findViewById(R.id.adress);

        send = (Button) findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mtitle  = title.getText().toString();
                String mcontent = content.getText().toString();
                String mtime = time.getText().toString();
                String madress = adress.getText().toString();
                SWZL p2= new SWZL();
                p2.setTitle(mtitle);
                p2.setContent(mcontent);
                p2.setTime(mtime);
                p2.setAdress(madress);
                p2.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if(e==null){
                            Toast.makeText(send.this,"添加数据成功，返回objectId为："+objectId,Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(send.this,"创建数据失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }


    /*


    private void initListener() {
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String mtitle  = title.getText().toString();
                final String mcontent = content.getText().toString();
                final String mtime = time.getText().toString();
                final String madress = adress.getText().toString();
                if(mtitle == null ||  mcontent ==null || mtime ==null || madress ==null ){
                    Toast.makeText(send.this,"请填写完整信息", Toast.LENGTH_SHORT).show();
                }
                else {
                    final BmobFile bmobfile = new BmobFile(new File(icon_path));
                    bmobfile.upload(new UploadFileListener() {

                        @Override
                        public void done(BmobException e) {
                            System.out.println("图片上传成功");
                            SWZL p2= new SWZL();
                            p2.setTitle(mtitle);
                            p2.setContent(mcontent);
                            p2.setTime(mtime);
                            p2.setAdress(madress);
                            p2.setIcon(bmobfile);
                            p2.save(new SaveListener<String>() {
                                @Override
                                public void done(String objectId, BmobException e) {
                                    if(e==null){
                                        Toast.makeText(send.this,"上传成功", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(send.this,"上传失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }

            }

        });
    }

*/

    public void post (View view){
        String mtitle  = title.getText().toString();
        String mcontent = content.getText().toString();
        String mtime = time.getText().toString();
        String madress = adress.getText().toString();
        SWZL p2= new SWZL();
        p2.setTitle(mtitle);
        p2.setContent(mcontent);
        p2.setTime(mtime);
        p2.setAdress(madress);
        p2.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    Toast.makeText(send.this,"添加数据成功，返回objectId为："+objectId,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(send.this,"创建数据失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}
