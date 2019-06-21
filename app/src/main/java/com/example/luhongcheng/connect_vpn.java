package com.example.luhongcheng;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.luhongcheng.Bmob_bean.update;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class connect_vpn extends Activity implements View.OnClickListener {
    String URL = "http://bmob-cdn-20204.b0.upaiyun.com/2018/11/07/a4efe9e5401ecdc8809403c5349ffdbf.apk";
    Intent intent2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vpn);
        Bmob.initialize(this, "69d2a14bfc1139c1e9af3a9678b0f1ed");
        check();
        Button down = (Button)findViewById(R.id.down_vpn);
        down.setOnClickListener(this);
        Button open = (Button)findViewById(R.id.open_vpn);
        open.setOnClickListener(this);

        Button newvpn = findViewById(R.id.down_new_vpn);
        newvpn.setOnClickListener(this);

    }

    private void check() {
        PackageManager packageManager = getPackageManager();
        intent2 = packageManager.getLaunchIntentForPackage("com.topsec.topsap");
        if(intent2==null){
            Toast.makeText(getApplicationContext(), "未安装！！", Toast.LENGTH_LONG).show();
            querySingleData();
        }else{
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("检测到应用，是否打开？")//设置对话框的标题
                    .setMessage("IP:210.35.64.3     账号密码为学号密码")//设置对话框的内容
                    //设置对话框的按钮
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("打开", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(intent2);
                            dialog.dismiss();
                        }
                    }).create();
            dialog.show();
        }
    }

    //查询单条数据
    public void querySingleData() {
        BmobQuery<update> bmobQuery = new BmobQuery<update>();
        bmobQuery.getObject("Q1Lj666B", new QueryListener<update>() {
            @Override
            public void done(update object, BmobException e) {
                if (e == null) {
                    URL = object.getapkUrl();
                } else {
                    URL = "http://bmob-cdn-20204.b0.upaiyun.com/2018/11/07/a4efe9e5401ecdc8809403c5349ffdbf.apk";
                }
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.down_vpn:
                Intent intent = new Intent();
                intent.setData(Uri.parse(URL));
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent);
                break;
            case R.id.open_vpn:
                PackageManager packageManager = getPackageManager();
                intent2 = packageManager.getLaunchIntentForPackage("com.topsec.topsap");
                if(intent2==null){
                    Toast.makeText(getApplicationContext(), "未安装！！", Toast.LENGTH_LONG).show();
                }else{
                    startActivity(intent2);
                }
                break;
            case R.id.down_new_vpn:
                Intent intent0 = new Intent();
                intent0.setData(Uri.parse("https://vpn1.sit.edu.cn"));
                intent0.setAction(Intent.ACTION_VIEW);
                startActivity(intent0); //启动浏览器
        }
    }
}
