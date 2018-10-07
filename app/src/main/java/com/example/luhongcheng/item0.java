package com.example.luhongcheng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.luhongcheng.MBox.MBoxItem;
import com.example.luhongcheng.SWZL.swzlmain;

/**
 * Created by alex233 on 2018/4/21.
 */

public class item0 extends Activity {

    protected  void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item0);
        TopToBottomFinishLayout bottomFinishLayout = (TopToBottomFinishLayout) findViewById(R.id.layout);
        bottomFinishLayout.setOnFinishListener(new TopToBottomFinishLayout.OnFinishListener() {
                    @Override
                    public void onFinish() {
                        finish();
                    }
                });

    }



    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.bottom_silent,R.anim.bottom_out);
    }

    public void close(View view) {
        this.finish();
    }

    public  void wode(View view){
        Intent intent  = new Intent(item0.this,OneSelf.class);
        startActivity(intent);
    }

    public  void question(View view){
        Intent intent = new Intent(item0.this,MoreBox.class);
        startActivity(intent);
    }

    /*
    public  void shequ(View view){
        Intent intent = new Intent(item0.this,MoreBox.class);
        startActivity(intent);
    }
    */

    public  void swzl(View view){
        Intent intent = new Intent(item0.this,swzlmain.class);
        startActivity(intent);
    }
}
