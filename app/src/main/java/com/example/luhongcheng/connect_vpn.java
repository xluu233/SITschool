package com.example.luhongcheng;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.luhongcheng.Bmob.update;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class connect_vpn extends Activity implements View.OnClickListener {
    String URL = "http://bmob-cdn-20204.b0.upaiyun.com/2018/11/07/a4efe9e5401ecdc8809403c5349ffdbf.apk";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vpn);
        Bmob.initialize(this, "69d2a14bfc1139c1e9af3a9678b0f1ed");
        Button down = (Button)findViewById(R.id.down_vpn);
        down.setOnClickListener(this);
        querySingleData();
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
                    //Log.i("vpn-apk", "失败：" + e.getMessage() + "," + e.getErrorCode());
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
        }
    }
}
