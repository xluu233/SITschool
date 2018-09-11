package com.example.luhongcheng;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by alex233 on 2018/6/15.
 */

public class TuisongActivity extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tuisong);
        TextView title = (TextView) findViewById(R.id.title) ;
        TextView content = (TextView) findViewById(R.id.content);

        Intent intent1 =getIntent();
        /*取出Intent中附加的数据*/
        String a = intent1.getStringExtra("title");
        String b = intent1.getStringExtra("content");

        title.setText(a);
        content.setText(b);




    }
}
