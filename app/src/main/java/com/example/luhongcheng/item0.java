package com.example.luhongcheng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.miui.zeus.mimo.sdk.ad.AdWorkerFactory;
import com.miui.zeus.mimo.sdk.ad.IVideoAdWorker;
import com.miui.zeus.mimo.sdk.listener.MimoVideoListener;
import com.xiaomi.ad.common.pojo.AdType;

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
        Intent intent = new Intent(item0.this,MoreQuestion.class);
        startActivity(intent);
    }

    public  void shequ(View view){
        Toast.makeText(getApplicationContext(),"上应社区暂未开放，敬请期待",Toast.LENGTH_SHORT).show();
    }

    public  void swzl(View view){
        Intent intent = new Intent(item0.this,SWZL.class);
        startActivity(intent);
    }
}
