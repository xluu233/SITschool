package com.example.luhongcheng.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.luhongcheng.R;

/**
 * Created by alex233 on 2018/5/9.
 */

public class about2  extends AppCompatActivity {
    ImageButton xiazai;
    @Override
    protected  void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about2);
        xiazai = (ImageButton) findViewById(R.id.kuan);
        xiazai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setData(Uri.parse("https://www.coolapk.com/apk/187672"));//Url 就是你要打开的网址
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent); //启动浏览器
            }
        });
    }
}
