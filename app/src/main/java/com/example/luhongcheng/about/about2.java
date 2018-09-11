package com.example.luhongcheng.about;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.luhongcheng.utils.APKVersionCodeUtils;
import com.example.luhongcheng.Bmob.update;
import com.example.luhongcheng.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by alex233 on 2018/5/9.
 */

public class about2  extends AppCompatActivity implements View.OnClickListener {
    ImageButton xiazai1;
    ImageButton xiazai2;
    private String url;
    private String code1;
    private String text;

    public int code;
    //  上下文
    private Context mContext;
    //  进度条
    private ProgressBar mProgressBar;
    //  对话框
    private Dialog mDownloadDialog;
    //  判断是否停止
    private boolean mIsCancel = false;
    //  进度
    private int mProgress;
    //  文件保存路径
    private String mSavePath;
    //  版本名称
    private String mVersion_name="1.0";
    //  请求链接
    private String url1="http://bmob-cdn-20204.b0.upaiyun.com/2018/08/26/8e306c4c402a1a678094bf58bcadeb3d.apk";
    @Override
    protected  void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about2);
        mContext = about2.this;
        Bmob.initialize(this, "69d2a14bfc1139c1e9af3a9678b0f1ed");
        querySingleData();

        xiazai1 = (ImageButton) findViewById(R.id.kuan);
        xiazai1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setData(Uri.parse("https://www.coolapk.com/apk/187672"));//Url 就是你要打开的网址
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent); //启动浏览器
            }
        });
        xiazai2 = (ImageButton) findViewById(R.id.bmob);
        xiazai2.setOnClickListener(this);


    }

    //查询单条数据
    public void querySingleData() {
        BmobQuery<update> bmobQuery = new BmobQuery<update>();
        bmobQuery.getObject("MF4j000B", new QueryListener<update>() {
            @Override
            public void done(update object, BmobException e) {
                if (e == null) {
                    url = object.getapkUrl();
                    code1 = object.getCode();
                    text = object.getText();
                    //System.out.println("APK更新地址：" + url);
                    //System.out.println("版本号：" + code1);
                    //System.out.println("更新内容" + text);
                    check(code1);
                } else {
                    //Log.i("bmob图片", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    //判断版本大小
    public void check(String code1) {
        code = APKVersionCodeUtils.getVersionCode(this);
        int i = Integer.valueOf(this.code1).intValue();
        if (i > code) {
            showDialog();
        }
        else{
            Toast.makeText(about2.this, "已经是最新版本了哦", Toast.LENGTH_LONG).show();
        }
    }

    private void showDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_launcher)//设置标题的图片
                .setTitle("检查到新版本")//设置对话框的标题
                .setMessage(text)//设置对话框的内容
                //设置对话框的按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(MainActivity.this, "点击了取消按钮", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(MainActivity.this, "点击了确定的按钮", Toast.LENGTH_SHORT).show();
                        //postUrl(url);
                        //mIsCancel=false;
                        //展示对话框
                        //showDownloadDialog();
                        dialog.dismiss();
                        Intent intent = new Intent();
                        intent.setData(Uri.parse(url));//Url 就是你要打开的网址
                        intent.setAction(Intent.ACTION_VIEW);
                        startActivity(intent); //启动浏览器
                    }
                }).create();
        dialog.show();
    }


    /*
     * 显示正在下载对话框
     */
    protected void showDownloadDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("下载中");
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_progress, null);
        mProgressBar = (ProgressBar) view.findViewById(R.id.id_progress);
        builder.setView(view);

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 隐藏当前对话框
                dialog.dismiss();
                // 设置下载状态为取消
                mIsCancel = true;
            }
        });

        mDownloadDialog = builder.create();
        mDownloadDialog.show();

        // 下载文件
        downloadAPK();
    }
    /*
     * 开启新线程下载apk文件
     */
    private void downloadAPK() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                        String sdPath = Environment.getExternalStorageDirectory() + "/";
//                      文件保存路径
                        mSavePath = sdPath + "SITschool";

                        File dir = new File(mSavePath);
                        if (!dir.exists()){
                            dir.mkdir();
                        }
                        // 下载文件
                        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        int length = conn.getContentLength();

                        File apkFile = new File(mSavePath, mVersion_name);
                        FileOutputStream fos = new FileOutputStream(apkFile);

                        int count = 0;
                        byte[] buffer = new byte[1024];
                        while (!mIsCancel){
                            int numread = is.read(buffer);
                            count += numread;
                            // 计算进度条的当前位置
                            mProgress = (int) (((float)count/length) * 100);
                            // 更新进度条
                            mUpdateProgressHandler.sendEmptyMessage(1);

                            // 下载完成
                            if (numread < 0){
                                mUpdateProgressHandler.sendEmptyMessage(2);
                                break;
                            }
                            fos.write(buffer, 0, numread);
                        }
                        fos.close();
                        is.close();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 接收消息
     */
    private Handler mUpdateProgressHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    // 设置进度条
                    mProgressBar.setProgress(mProgress);
                    break;
                case 2:
                    // 隐藏当前下载对话框
                    mDownloadDialog.dismiss();
                    // 安装 APK 文件
                    //在API高于22的版本无法安装，暂时不用
                    //installAPK();
            }
        };
    };

    /*
     * 下载到本地后执行安装
     */
    protected void installAPK() {
        File apkFile = new File(mSavePath, mVersion_name);
        if (!apkFile.exists()){
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
//	    安装完成后，启动app（源码中少了这句话）
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.parse("file://" + apkFile.toString());
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bmob:
                Intent intent = new Intent();
                intent.setData(Uri.parse(url));//Url 就是你要打开的网址
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent); //启动浏览器
                break;
        }
    }
}
