package com.example.luhongcheng.about;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.luhongcheng.R;

/**
 * Created by alex233 on 2018/5/9.
 */

public class about0 extends AppCompatActivity {
    @Override
    protected  void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.green_300));//设置状态栏背景色
        }
    }
    public void back(View view){
        this.finish();
    }
}
