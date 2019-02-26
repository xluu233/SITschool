package com.example.luhongcheng.SWZL;

import android.annotation.SuppressLint;
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
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.luhongcheng.Bmob_bean.diudiu;
import com.example.luhongcheng.R;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class send2 extends Activity {
    EditText title,content,QQ;
    ImageButton addimage;
    public static final int CHOOSE_PHOTO = 3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send2);
        Bmob.initialize(this, "69d2a14bfc1139c1e9af3a9678b0f1ed");
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
        QQ = (EditText) findViewById(R.id.adress);
        addimage = (ImageButton)findViewById(R.id.image);
        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent, CHOOSE_PHOTO);
            }
        });
    }

    public void post (View view){
        final String mtitle  = title.getText().toString();
        final String mcontent = content.getText().toString();
        final String lianxi = QQ.getText().toString();
        final String icon_path = imagePath;
        if (mtitle == null){
            Toast.makeText(send2.this,"请填写完整信息", Toast.LENGTH_SHORT).show();
        } else if (icon_path == null){
            Toast.makeText(send2.this, "选择图片", Toast.LENGTH_SHORT).show();
        } else {
            final BmobFile bmobfile = new BmobFile(new File(icon_path));
            bmobfile.upload(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        diudiu p2 = new diudiu();
                        p2.setTitle(mtitle);
                        p2.setContent(mcontent);
                        p2.setQQ(lianxi);
                        p2.setImage(bmobfile);
                        p2.save(new SaveListener<String>() {
                            @Override
                            public void done(String objectId, BmobException e) {
                                if(e==null){
                                    Toast.makeText(send2.this,"添加数据成功，返回objectId为："+objectId,Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(send2.this,"创建数据失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } else {
                        Toast.makeText(send2.this, "文件上传失败", Toast.LENGTH_SHORT).show();
                        System.out.println("文件上传失败");
                    }
                }
            });


        }
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
            addimage.setImageBitmap(bitmap);
        }else {
            Toast.makeText(send2.this, "未得到图片", Toast.LENGTH_SHORT).show();
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
